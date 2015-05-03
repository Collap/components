package io.collap.components;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ReflectionChainBuilder<T, R> implements ChainBuilder<T, R> {

    private static class ReflectionChain<T, R> implements Component<T, R> {
        private List<Supplier<? extends Component<?, ?>>> componentFactories;

        public ReflectionChain(List<Supplier<? extends Component<?, ?>>> componentFactories) {
            this.componentFactories = componentFactories;
        }

        @Override
        public R apply(T input) {
            // Generics are mostly ignored here, because we assume that the Builder already checked
            // if the types match, so we can mostly ignore them here.
            Object value = input;
            for (Supplier<? extends Component<?, ?>> factory : componentFactories) {
                Component component = factory.get();
                value = component.apply(value);
            }
            return (R) value;
        }
    }

    private List<Supplier<? extends Component<?, ?>>> componentFactories = new ArrayList<>();

    @Override
    public ReflectionChainBuilder<T, R> append(Supplier<? extends Component<?, ?>> factory) {
        // TODO: Check if the input type matches with the last output type (or T in case of the first).
        // This would either require two new arguments, inputType and outputType, or making an instance
        // of the component via factory.get() and checking its class. The latter choice is definitely
        // more convenient for the API user, but a bit ugly, because the component that is created is
        // not used.
        // A third option would entail to create a Factory interface that extends the Supplier interface,
        // and which provides two methods that return the input and output type, respectively.
        componentFactories.add(factory);
        return this;
    }

    @Override
    public ReflectionChainBuilder<T, R> append(Class<? extends Component<?, ?>> type) {
        // TODO: Check if the type provides a constructor with no arguments.
        // TODO: Check if the input type matches with the last output type (or T in case of the first).
        componentFactories.add(() -> {
            try {
                return type.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Class could not be instantiated!", e);
            }
        });
        return this;
    }

    @Override
    public Supplier<Component<T, R>> build() {
        if (componentFactories == null) {
            throw new IllegalStateException("The build() method may only be called once!");
        }

        // TODO: Check if the last output type matches with R.

        // The factories NEED to be passed via this local variable. Otherwise this object is captured instead
        // of just the factory list.
        final List<Supplier<? extends Component<?, ?>>> factories = componentFactories;
        Supplier<Component<T, R>> factory = () -> new ReflectionChain<>(factories);
        componentFactories = null; // Set this to null to prevent further build() calls.
        return factory;
    }

}
