package com.sing.design.b07iterator;

import org.apache.commons.collections.iterators.ArrayListIterator;
import org.omg.CORBA.Object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 迭代器模式
 * 提供一种访问集合中的各个元素，而不暴露其内部表示的方法。
 *
 * @author songbo
 * @since 2022-07-08
 */
public class IteratorMain {
    /**
     * 数组：随机访问性强，查找速度快；插入和删除效率低
     * 链表：插入删除速度快；查找效率低
     * @param args
     */
    public static void main(String[] args) {
        List list = new ArrayList<>();
        list.add("11");
        list.iterator();

    }
}

interface IteratorM<E> {//Element //Type //Key //Value
    boolean hasNext();

    E next();
}

interface CollectionM<E> {
    int size();

    void add(E object);

    IteratorM<E> iterator();
}

class ArrayListM<E> implements CollectionM<E> {
    E[] objects = (E[]) new Object[10];
    private int size = 0;

    public int size() {
        return size;
    }

    public void add(E object) {
        if (size + 1 > objects.length) {
            objects = Arrays.copyOf(objects, objects.length + objects.length >> 2);
        }
        objects[size++] = object;
    }

    @Override
    public IteratorM<E> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements IteratorM {
        int cursor;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            return objects[cursor++];
        }
    }

}

class LinkListM<E> implements CollectionM<E> {
    Node head;
    Node tail;
    private int size = 0;

    public int size() {
        return size;
    }

    public void add(E object) {
        Node node = new Node(object);
        node.next = null;

        if (head == null) {
            head = node;
            tail = node;
        }
        tail.next = node;
        tail = node;
        size++;
    }

    @Override
    public IteratorM<E> iterator() {
        return new LinkListIterator();
    }

    private class LinkListIterator implements IteratorM {
        int cursor;
        Node node;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public Node next() {
            if (node == null) {
                node = head;
            } else {
                node = node.next;
            }
            return node;
        }
    }

    class Node {
        E object;
        Node next;

        public Node(E object) {
            this.object = object;
        }

    }
}


