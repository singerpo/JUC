package com.sing.juc.c07.T2ThreadPool;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class T02CompletableFuture {

    public static void main(String[] args) {
        CompletableFuture<Double> future1 = CompletableFuture.supplyAsync(()->getPrice1());
        CompletableFuture<Double> future2 = CompletableFuture.supplyAsync(()->getPrice2());
        CompletableFuture.allOf(future1,future2).join();


        CompletableFuture future3 =  CompletableFuture.supplyAsync(()->getPrice1())
                .thenApply(String::valueOf)
                .thenApply(str->"price " + str)
                .thenAccept(System.out::println);
        System.out.println("--------------");
        future3.join();
    }

    private static double getPrice1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1.00;
    }
    private static double getPrice2() {
        return 2.00;
    }
}
