package io.collap.components;

import io.collap.components.tuple.Tuple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ReflectionChainBuilder<T, R> implements ChainBuilder<T, R> {

    private static class ComponentDescription {
        Supplier<? extends Component> factory;
        Method entry;

        public ComponentDescription(Supplier<? extends Component> factory, Method entry) {
            this.factory = factory;
            this.entry = entry;
        }
    }

    private static class ReflectionChain<T, R> implements Chain<T, R> {
        private List<ComponentDescription> descriptions;


        public ReflectionChain(List<ComponentDescription> descriptions) {
            this.descriptions = descriptions;
        }

        @Override
        public R execute(T input) {
            // Generics are mostly ignored here, because we assume that the Builder already checked
            // if the types match.
            Object value = input;
            for (ComponentDescription description : descriptions) {
                Component component = description.factory.get();
                Method entry = description.entry;
                Parameter[] parameters = entry.getParameters();

                try {
                    if (parameters.length == 1 && value.getClass().isAssignableFrom(parameters[0].getType())) {
                        // This case also passes the tuple if the entry function accepts a tuple,
                        // which is the case, for example, with chains.
                        value = entry.invoke(component, value);
                    } else {
                        // Check if the value is a tuple and potentially "unpack" the tuple to call the method.
                        if (value instanceof Tuple) {
                            // We assume that the type checking already happened, so the elements of the tuple
                            // should be compatible to the entry method's parameters.
                            value = entry.invoke(component, ((Tuple) value).getValues());
                        }

                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Could not call entry method!", e);
                }
            }

            return (R) value;
        }
    }

    private List<ComponentDescription> descriptions = new ArrayList<>();

    @Override
    public ReflectionChainBuilder<T, R> append(Supplier<? extends Component> factory, Class<? extends Component> type) {
        // Search for the entry method.
        Method[] methods = type.getMethods();
        Method entry = null;
        for (Method method : methods) {
            if (method.getAnnotation(Entry.class) != null) {
                if (entry == null) {
                    entry = method;
                } else {
                    throw new IllegalArgumentException("The class type " + type + " has two or more @Entry methods. " +
                            "There can only be one!");
                }
            }
        }

        if (entry == null) {
            throw new IllegalArgumentException("The class type " + type + " does not specify an @Entry method.");
        }

        // TODO: Check if the input type of the entry method matches with the last output type.
        // This would either require two new arguments, inputType and outputType, or making an instance
        // of the component via factory.get() and checking its class. The latter choice is definitely
        // more convenient for the API user, but a bit ugly, because the component that is created is
        // not used.
        // A third option would entail to create a Factory interface that extends the Supplier interface,
        // and which provides two methods that return the input and output type, respectively.

        descriptions.add(new ComponentDescription(factory, entry));
        return this;
    }

    @Override
    public ReflectionChainBuilder<T, R> append(Class<? extends Component> type) {
        // TODO: Check if the type provides a constructor with no arguments.
        return append(() -> {
            try {
                return type.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Class could not be instantiated!", e);
            }
        }, type);
    }

    @Override
    public Supplier<Chain<T, R>> build() {
        if (descriptions == null) {
            throw new IllegalStateException("The build() method may only be called once!");
        }

        // TODO: Check if the last output type matches with R.

        // The descriptions NEED to be passed via this local variable. Otherwise this object is captured instead
        // of just the factory list.
        final List<ComponentDescription> descriptions = this.descriptions;
        Supplier<Chain<T, R>> factory = () -> new ReflectionChain<>(descriptions);
        this.descriptions = null; // Set this to null to prevent further build() calls.
        return factory;
    }

}
