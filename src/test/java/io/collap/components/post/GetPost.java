package io.collap.components.post;

import io.collap.components.Component;

public class GetPost implements Component<Integer, Post> {

    @Override
    public Post apply(Integer value) {
        return new Post(value, "Test", "This is a test post!");
    }

}
