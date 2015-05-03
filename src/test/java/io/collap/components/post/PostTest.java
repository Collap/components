package io.collap.components.post;

import io.collap.components.Component;
import io.collap.components.ReflectionChainBuilder;
import org.junit.Test;

import java.util.function.Supplier;

public class PostTest {

    @Test
    public void test() {
        Supplier<Component<Integer, String>> chain =
                new ReflectionChainBuilder<Integer, String>()
                .append(GetPost.class)
                .append(ShowPost.class)
                .build();
        System.out.println(chain.get().apply(5));
    }

}
