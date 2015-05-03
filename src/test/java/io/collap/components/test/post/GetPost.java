package io.collap.components.test.post;

import io.collap.components.Component;
import io.collap.components.Entry;

public class GetPost implements Component {

    @Entry
    public Post get(Integer value) {
        return new Post(value, "Test", "This is a test post!");
    }

}
