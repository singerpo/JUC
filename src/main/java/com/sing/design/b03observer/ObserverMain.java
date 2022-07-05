package com.sing.design.b03observer;

import java.util.ArrayList;
import java.util.List;

/**
 * https://blog.csdn.net/a745233700/article/details/83662714
 * 观察者模式
 * 又称为 发布-订阅模式，定义了对象之间一对多依赖关系，当目标对象(被观察者)的状态发生改变时，它的所有依赖者(观察者)都会收到通知。
 *
 * @author songbo
 * @since 2022-07-04
 */
public class ObserverMain {

    public static void main(String[] args) {
        // 创建被观察者
        ChatSubject chatSubject = new ChatSubject();
        // 创建观察者
        User user1 = new User("张三");
        User user2 = new User("李四");
        User user3 = new User("王五");

        // 通知观察者
        chatSubject.addObserver(user1);
        chatSubject.addObserver(user2);
        chatSubject.addObserver(user3);
        chatSubject.notifyObservers("您有新的消息");

    }
}

//观察者
interface IObserver {
    void update(String message);
}

class User implements IObserver {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + "收到消息：" + message);
    }
}

//被观察者
interface Subject {
    void addObserver(IObserver observer);

    void deleteObserver(IObserver observer);

    void notifyObservers(String message);
}

class ChatSubject implements Subject{
    private List<IObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (IObserver observer : observers) {
            observer.update(message);
        }
    }
}

