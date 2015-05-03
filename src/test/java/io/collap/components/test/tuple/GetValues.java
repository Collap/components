package io.collap.components.test.tuple;

import io.collap.components.Component;
import io.collap.components.Entry;
import io.collap.components.tuple.Triple;

public class GetValues implements Component {

    @Entry
    public Triple<Double, Double, Double> get(Double value) {
        return new Triple<>(value * value, Math.sqrt(value), -value);
    }

}
