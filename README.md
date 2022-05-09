1.com.sing.juc.c01  
T01_WhatIsThread: 线程的概念、线程的5种状态  
T02_HowToCreateThread: 线程启动方式  
T03_Sleep_Yield_Join: 线程常用方法  
T04_Synchronized: 线程同步synchronized  

2.com.sing.juc.c02  
T01Volatile: volatile保证线程可见性、禁止指令重排序  
T02AtomicInteger: AtomicXX类，通过Unsafe类通过**CAS**来实现（**ABA**问题通过加版本号解决）  

3.com.sing.juc.c03  
T01ReentrantLock: ReentrantLock  
T02CountDownLatch: CountDownLatch  
T03CyclicBarrier: CyclicBarrier
T04Phaser: Phaser
T05ReentrantReadWriteLock：ReentrantReadWriteLock--StampedLock   
T06Semaphore：Semaphore
T07Exchanger：Exchanger

4.com.sing.juc.c04.T1interview  
    实现一个容器，提供两个方法 add size  
    写两个线程线程1提供10个元素到容器中，线程2实现监控元素的个数，当个数为5时，线程2给出提示并结束  
com.sing.juc.c04.T2interview  
    写一个固定容量同步容器，拥有put和get方法，以及getCount方法  
    能够支持2个生产者线程以及10个消费者线程的阻塞调用  
AQS
JDK9及之后：VarHandle（1.普通属性原子操作 2比反射快，直接操作二进制码）

5.com.sing.juc.c05
T01ThreadLocal：ThreadLocal（弱）
T02ReferenceType：强软弱虚

6.com.sing.juc.c05
容器：Collection  Map 

  