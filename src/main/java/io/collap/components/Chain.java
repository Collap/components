package io.collap.components;

/**
 * A chain is just a component itself. The fact that it chains together components to get from an
 * input value to a result is the implementation detail of this particular component, it does not
 * affect its status as a Component.
 */
public interface Chain<T, R> extends Component {

    @Entry
    R execute(T input);

    // TODO: Add subchain method?

}
