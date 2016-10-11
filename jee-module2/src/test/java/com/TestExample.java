package com;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestExample {

    private boolean privBoolean = true;

    /**
     * hotfix 0.34.1
     * dev 0.54
     * dev 0.57 while rel 0.56
     */
    @Test
    public void test(){

        System.out.println("thread id " + Thread.currentThread().getId());

        Assert.assertTrue(true);

        Object[] arguments = new Object[]{"test", Integer.valueOf(1)};
        String y = Arrays.asList(arguments).stream().reduce("",
                //(sum, p) -> ((p != null ? sum = sum : sum += p.toString())),
                //ok (sum, p) -> sum += p.toString(),
                //(sum, p) -> p != null ? sum += p.toString() : sum += sum,
                //(p != null ? (sum, p) -> sum += p.toString() : (sum, p) -> sum += ""),
                //(sum, p) -> {sum += p.toString()},
                //(sum, p) -> {sum += p.toString();},
                (sum, p) -> sum += (p !=null ?p.toString():""),
                (sum1, sum2) -> sum1 + sum2);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            System.out.println("thread id " + Thread.currentThread().getId());
            System.out.println("access priv variable " + privBoolean);
        }
                ;
        executorService.execute(task);

        System.out.println(y);
    }
}


