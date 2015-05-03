package io.collap.components.test.tuple;

import io.collap.components.Chain;
import io.collap.components.ReflectionChainBuilder;
import org.junit.Test;

import java.util.function.Supplier;

public class TupleTest {

    @Test
    public void test() {
        Supplier<Chain<Double, String>> chainFactory =
                new ReflectionChainBuilder<Double, String>()
                        .append(GetValues.class)
                        .append(ModifyValues.class)
                        .append(ShowValues.class)
                        .build();
        System.out.println(chainFactory.get().execute(2.0));
        System.out.println(chainFactory.get().execute(4.0));
    }

}
