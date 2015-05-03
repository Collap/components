package io.collap.components.test.tuple;

import io.collap.components.Component;
import io.collap.components.Entry;

public class ShowValues implements Component {

    @Entry
    public String show(Double a, Double b, Double c) {
        return "a: " + a + ", b: " + b + ", c: " + c;
    }

}
