package io.collap.components;

@FunctionalInterface
public interface Component<T, R> {

    /**
     * Applies the component to the value. This method is generally only defined for a single call per object
     * and does not need to be thread-safe. These points are imposed to add flexibility to the usage of fields
     * in the underlying implementation.
     */
    R apply(T value);

}
