package io.collap.components;

import java.util.function.Supplier;

/**
 * A chain is just a component itself. The fact that it chains together components to get from an
 * input value to a result is the implementation detail of this particular component, it does not
 * affect its status as a Component.
 */
public interface ChainBuilder<T, R> {

    /**
     * Appends a factory to the chain, which is polled to create a new Component
     * each time the chain is executed.
     * @return This object for chaining.
     */
    ChainBuilder<T, R> append(Supplier<? extends Component<?, ?>> factory);

    /**
     * Appends a Component class to the chain, which must have a constructor
     * that takes not arguments. Each time the chain is executed, a new
     * Component of this type is instantiated and applied.
     * @return This object for chaining.
     */
    ChainBuilder<T, R> append(Class<? extends Component<?, ?>> type);

    /**
     * This method can only be called once per instance of a ChainBuilder. Any subsequent
     * calls yield undefined behaviour.
     * @return A supplier which acts as a factory for new instances of the Component.
     */
    Supplier<Component<T, R>> build();

}
