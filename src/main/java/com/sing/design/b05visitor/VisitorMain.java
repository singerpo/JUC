package com.sing.design.b05visitor;

/**
 * 访问者模式
 * 访问者模式适用于数据结构相对稳定的系统，将数据结构与基于数据的操作进行分离，
 * 使得添加作用于这些数据结构的新操作变得简单，并且不需要改变各数据结构，为不同类型的数据结构提供多种访问操作方式，这样是访问者模式的设计动机。
 *
 * @author songbo
 * @since 2022-07-07
 */
public class VisitorMain {
    public static void main(String[] args) {
        Computer computer = new Computer();

        PersonVisitor personVisitor = new PersonVisitor();
        computer.accept(personVisitor);
        System.out.println(personVisitor.totalPrice);

        CorpVisitor corpVisitor = new CorpVisitor();
        computer.accept(corpVisitor);
        System.out.println(corpVisitor.totalPrice);
    }
}

//Vistor：抽象访问者
abstract class Visitor {
    public abstract void visit(Cpu cpu);

    public abstract void visit(Memory memory);

    public abstract void visit(Board board);
}

//Element：抽象元素
abstract class ComputerPart {
    public abstract double getPrice();

    public abstract void accept(Visitor visitor);
}

//ConcreteVisitor：具体访问者
class PersonVisitor extends Visitor {
    double totalPrice = 0;

    @Override
    public void visit(Cpu cpu) {
        this.totalPrice += cpu.getPrice()*0.9;
    }

    @Override
    public void visit(Memory memory) {
        this.totalPrice += memory.getPrice()*0.85;
    }

    @Override
    public void visit(Board board) {
        this.totalPrice += board.getPrice()*0.95;
    }
}

class CorpVisitor extends Visitor {
    double totalPrice = 0;

    @Override
    public void visit(Cpu cpu) {
        this.totalPrice += cpu.getPrice()*0.6;
    }

    @Override
    public void visit(Memory memory) {
        this.totalPrice += memory.getPrice()*0.75;
    }

    @Override
    public void visit(Board board) {
        this.totalPrice += board.getPrice()*0.85;
    }
}

//ObjectStructure：对象结构
class Computer {
    ComputerPart cup = new Cpu();
    ComputerPart memory = new Memory();
    ComputerPart board = new Board();
    public void accept(Visitor visitor){
        this.cup.accept(visitor);
        this.memory.accept(visitor);
        this.board.accept(visitor);
    }
}

class Cpu extends ComputerPart {

    @Override
    public double getPrice() {
        return 100;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class Memory extends ComputerPart {

    @Override
    public double getPrice() {
        return 50;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class Board extends ComputerPart {

    @Override
    public double getPrice() {
        return 850;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}