package io.collap.components.tuple;

public class Tuple {

    protected Object[] values;

    public Tuple(Object... values) {
        this.values = values;
    }

    public int size() {
        return values.length;
    }

    public Object[] getValues() {
        return values;
    }

}
