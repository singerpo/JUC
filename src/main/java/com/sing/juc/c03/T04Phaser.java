package com.sing.juc.c03;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 移相器
 *
 * @author songbo
 * @since 2022-04-24
 */
public class T04Phaser {
    static Random random = new Random();
    static Phaser phaser = new MarriagePhaser();

    static void millSleep(int million) {
        try {
            TimeUnit.MILLISECONDS.sleep(million);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MarriagePhaser extends Phaser {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人到齐了" + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完了" + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("所有人离开了" + registeredParties);
                    System.out.println();
                    return false;
                case 3:
                    System.out.println("婚礼结束了" + registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }

    static class Person implements Runnable {
        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrive() {
            millSleep(random.nextInt(1000));
            System.out.printf("%s 到达现场\n", name);
            // 到达并等待前进
            phaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            millSleep(random.nextInt(1000));
            System.out.printf("%s 吃完\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            millSleep(random.nextInt(1000));
            System.out.printf("%s 离开现场\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        private void hug() {
            if (name.equals("新郎") || name.equals("新娘")) {
                millSleep(random.nextInt(1000));
                System.out.printf("%s 入洞房\n", name);
                phaser.arriveAndAwaitAdvance();
            }else {
                // 到达并注销
               phaser.arriveAndDeregister();
            }
        }

        private void test(){
            System.out.printf("%s test\n", name);
        }

        @Override
        public void run() {
            this.arrive();
            this.eat();
            this.leave();
            this.hug();
            this.test();
        }
    }

    public static void main(String[] args) {
        phaser.bulkRegister(7);

        for (int i = 0; i < 5; i++) {
            new Thread(new Person("p" + i)).start();
        }

        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();
    }
}
