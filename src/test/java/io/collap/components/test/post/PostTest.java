package io.collap.components.test.post;

import io.collap.components.Chain;
import io.collap.components.ReflectionChainBuilder;
import org.junit.Test;

import java.util.function.Supplier;

public class PostTest {

    @Test
    public void test() {
        Supplier<Chain<Integer, String>> chainFactory =
                new ReflectionChainBuilder<Integer, String>()
                .append(GetPost.class)
                .append(ShowPost.class)
                .build();
        System.out.println(chainFactory.get().execute(5));
    }

}
