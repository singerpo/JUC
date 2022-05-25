package com.sing.jvm.jmm;

/**102：0.21
 * @author songbo
 * @since 2022-05-25
 */
public class TestIPlusPlus {

    public static void main(String[] args) {
        int i = 8;
        i = i++;
        i = i++;
        i = i++;
        System.out.println(i);
    }
}
