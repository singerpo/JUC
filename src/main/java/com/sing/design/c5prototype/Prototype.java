package com.sing.design.c5prototype;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 原型模式
 * 使用原型实例指定创建对象的种类，并通过拷贝这些原型创建新的对象
 */
public class Prototype {

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Person person1 = new Person();
        // 浅拷贝
        Person person2 = (Person) person1.clone();

        //通过序列化方法实现深拷贝
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(person2);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        Person person3 = (Person) ois.readObject();
        System.out.println(person1.arr == person3.arr);
        for (String s : person3.arr) {
            System.out.println(s);
        }


    }


}

class Person implements Cloneable, Serializable {
    int age = 8;
    String name = "张三";
    String[] arr = new String[]{"1", "2"};
    Location location = new Location("观湖街道", 188);
    List<String> list = new ArrayList<>();

    {
        list.add("11");
        list.add("22");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Location implements Serializable{
    String street;
    int roomNo;

    public Location(String street, int roomNo) {
        this.street = street;
        this.roomNo = roomNo;
    }
}
