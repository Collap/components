package io.collap.components;

import java.util.function.Supplier;

public interface ChainBuilder<T, R> {

    /**
     * Appends a factory to the chain, which is polled to create a new Component
     * each time the chain is executed.
     * @param type The class type of the Component which is produces by 'factory'.
     * @return This object for chaining.
     */
    ChainBuilder<T, R> append(Supplier<? extends Component> factory, Class<? extends Component> type);

    /**
     * Appends a Component class to the chain, which must have a constructor
     * that takes not arguments. Each time the chain is executed, a new
     * Component of this type is instantiated and applied.
     * @return This object for chaining.
     */
    ChainBuilder<T, R> append(Class<? extends Component> type);

    /**
     * This method can only be called once per instance of a ChainBuilder. Any subsequent
     * calls yield undefined behaviour.
     * @return A supplier which acts as a factory for new instances of the Component.
     */
    Supplier<Chain<T, R>> build();

}
