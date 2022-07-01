package com.sing.design.b01strategy;

import java.util.Comparator;

/**
 * 策略模式
 * 将类中经常改变或者可能改变的部分提取为作为一个抽象策略接口类，然后在类中包含这个对象的实例，这样类实例在运行时就可以随意调用实现了这个接口的类的行为。
 *
 * @author songbo
 * @since 2022-06-28
 */
public class StrategyMain {
    public static void main(String[] args) {
        Cat[] cats = new Cat[3];
        cats[0] = new Cat(1, 3);
        cats[1] = new Cat(2, 2);
        cats[2] = new Cat(3, 1);

        //策略1
        SorterContext<Cat> sorterContext = new SorterContext(new HeightComparatorStrategy());
        sorterContext.sort(cats);
        for (Cat cat : cats) {
            System.out.println(cat);
        }

        //策略2
        sorterContext.setComparatorStrategy(new WeightComparatorStrategy());
        sorterContext.sort(cats);
        for (Cat cat : cats) {
            System.out.println(cat);
        }

    }
}


// 抽象策略接口
interface ComparatorStrategy<T> {
    int compare(T o1, T o2);
}

//环境类（Context)
class SorterContext<T> {
    private ComparatorStrategy<T> comparatorStrategy;

    public SorterContext(ComparatorStrategy<T> comparatorStrategy) {
        this.comparatorStrategy = comparatorStrategy;
    }

    public void sort(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minPos = i;
            for (int j = i + 1; j < arr.length; j++) {
                minPos = comparatorStrategy.compare(arr[j], arr[minPos]) == -1 ? j : minPos;
            }
            swap(arr, i, minPos);
        }
    }

    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public ComparatorStrategy<T> getComparatorStrategy() {
        return comparatorStrategy;
    }

    public void setComparatorStrategy(ComparatorStrategy<T> comparatorStrategy) {
        this.comparatorStrategy = comparatorStrategy;
    }
}

//策略实现1按高度排序
class HeightComparatorStrategy implements ComparatorStrategy<Cat> {

    @Override
    public int compare(Cat o1, Cat o2) {
        if (o1.getHeight() > o2.getHeight()) {
            return 1;
        } else if (o1.getHeight() < o2.getHeight()) {
            return -1;
        } else {
            return 0;
        }
    }
}

//策略实现2按重量排序
class WeightComparatorStrategy implements ComparatorStrategy<Cat> {

    @Override
    public int compare(Cat o1, Cat o2) {
        if (o1.getWeight() > o2.getWeight()) {
            return 1;
        } else if (o1.getWeight() < o2.getWeight()) {
            return -1;
        } else {
            return 0;
        }
    }
}


class Cat {
    private int weight;
    private int height;

    public Cat(int weight, int height) {
        this.weight = weight;
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "weight=" + weight +
                ", height=" + height +
                '}';
    }
}
