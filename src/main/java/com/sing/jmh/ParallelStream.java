package com.sing.jmh;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author songbo
 * @since 2022-05-13
 */
public class ParallelStream {
    static List<Integer> list = new ArrayList<>();
    static {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            list.add(1000000 + random.nextInt(1000000));
        }
    }
    static void foreach(){
        list.forEach(v->isPrime(v));
    }
    static void parallel(){
        list.parallelStream().forEach(ParallelStream::isPrime);
    }

    /**
     * 判断是否质数
     * @param num
     * @return
     */
    static boolean isPrime(int num){
        for (int i = 2; i <= num/2; i++) {
            if(num%i == 0){
                return false;
            }
        }
        return true;
    }
}
