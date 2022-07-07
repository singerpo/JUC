#com.sing.design
23种设计模式
创建型模式：共5种：工厂方法模式、抽象工厂模式、单例模式、建造者模式、原型模式
结构型模式：共7种：适配器模式、装饰器模式、代理模式、桥接模式、外观模式、组合模式、享元模式
行为型模式：共11种：策略模式、模板方法模式、观察者模式、责任链模式、访问者模式、中介者模式、迭代器模式、命令模式、状态模式、备忘录模式、解释器模式
#com.sing.disruptor
Disruptor是一个高性能队列.基于Disruptor开发的系统单线程能支撑每秒600万订单
.引入环形的数组结构：数组元素不会被回收，避免频繁的GC，
.无锁的设计：采用CAS无锁方式，保证线程的安全性
.属性填充：通过添加额外的无用信息，避免伪共享问题
.元素位置的定位：采用跟一致性哈希一样的方式，一个索引，进行自增
#com.sing.jmh
JMH即Java Microbenchmark Harness，是Java用来做基准测试的一个工具，该工具由OpenJDK提供并维护，测试结果可信度高。
#com.sing.juc
#1.com.sing.juc.c01  
T01_WhatIsThread: 线程的概念、线程的5种状态  
T02_HowToCreateThread: 线程启动方式  
T03_Sleep_Yield_Join: 线程常用方法  
T04_Synchronized: 线程同步synchronized  

#2.com.sing.juc.c02  
T01Volatile: volatile保证线程可见性、禁止指令重排序  
T02AtomicInteger: AtomicXX类，通过Unsafe类通过**CAS**来实现（**ABA**问题通过加版本号解决）

### 3.com.sing.juc.c03  
T01ReentrantLock: ReentrantLock  
T02CountDownLatch: CountDownLatch  
T03CyclicBarrier: CyclicBarrier
T04Phaser: Phaser
T05ReentrantReadWriteLock：ReentrantReadWriteLock--StampedLock   
T06Semaphore：Semaphore
T07Exchanger：Exchanger

### 4.com.sing.juc.c04.T1interview  
    实现一个容器，提供两个方法 add size  
    写两个线程线程1提供10个元素到容器中，线程2实现监控元素的个数，当个数为5时，线程2给出提示并结束  
com.sing.juc.c04.T2interview  
    写一个固定容量同步容器，拥有put和get方法，以及getCount方法  
    能够支持2个生产者线程以及10个消费者线程的阻塞调用  
AQS
JDK9及之后：VarHandle（1.普通属性原子操作 2比反射快，直接操作二进制码）

### 5.com.sing.juc.c05
T01ThreadLocal：ThreadLocal（弱）
T02ReferenceType：强软弱虚

### 6.com.sing.juc.c06
容器：Collection(List Set Queue)  Map 

### 7.com.sing.juc.c07.T1InterviewA1B2C3
    两个线程交替打印A1B2C3D4E5F6G7
    


  