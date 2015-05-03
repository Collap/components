package io.collap.components.test.tuple;

import io.collap.components.Component;
import io.collap.components.Entry;
import io.collap.components.tuple.Triple;

public class ModifyValues implements Component {

    @Entry
    public Triple<Double, Double, Double> modify(Double a, Double b, Double c) {
        return new Triple<>(a * b, b * c, c * a);
    }

}
