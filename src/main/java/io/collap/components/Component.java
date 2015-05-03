package io.collap.components;

/**
 * <p>A component provides at least one function, annotated with @Entry.
 * The function takes one or more input values, and produces one or more
 * output vales. Components can be chained together with a ChainBuilder.
 * Each time a chain is executed, a new object of the component is
 * instantiated. Because of this, components do not need to be thread-safe.
 *
 * <p>Components can not directly share values between calls via fields,
 * because allowing that would introduce another layer that needs to
 * be persistent between multiple JVMs.
 *
 * <p>A component must provide a constructor with no parameters.
 */
public interface Component {

}
