package com.sing.design.b02templatemethod;

/**
 * 模板方法
 * 基于继承实现的，在抽象父类中声明一个模板方法，并在模板方法中定义算法的执行步骤（即算法骨架）。
 *
 * @author songbo
 * @since 2022-07-01
 */
public class TemplateMethodMain {

    public static void main(String[] args) {
        AbstractPerson student = new Student();
        student.prepareToSchool();

        AbstractPerson teacher = new Teacher();
        teacher.prepareToSchool();
    }
}

// 抽象父类定义整个流程骨架
abstract class AbstractPerson {
    public final void prepareToSchool() {
        dressUp();
        eatBreakfast();
        takeThings();
    }

    // 打扮
    protected abstract void dressUp();

    // 吃早餐
    protected abstract void eatBreakfast();

    protected abstract void takeThings();
}

class Student extends AbstractPerson {

    @Override
    protected void dressUp() {
        System.out.println("穿校服");
    }

    @Override
    protected void eatBreakfast() {
        System.out.println("吃妈妈做好的早餐");
    }

    @Override
    protected void takeThings() {
        System.out.println("背书包，带上家庭作业和红领巾");
    }
}

class Teacher extends AbstractPerson {

    @Override
    protected void dressUp() {
        System.out.println("穿工作服");
    }

    @Override
    protected void eatBreakfast() {
        System.out.println("买早餐，和孩子一起吃早餐");
    }

    @Override
    protected void takeThings() {
        System.out.println("带上准备的考卷");
    }
}
