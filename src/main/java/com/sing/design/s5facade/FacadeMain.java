package com.sing.design.s5facade;

/**
 * 外观(门面)模式
 * 通过对客户端提供一个统一的接口，用于访问子系统中的一群接口
 *
 * @author songbo
 * @since 2022-06-24
 */
public class FacadeMain {
    public static void main(String[] args) {
        ComputerFacade computerFacade = new ComputerFacade();
        computerFacade.startup();
        computerFacade.shutdown();
    }
}


//Facade类
class ComputerFacade {
    private CPU cpu;
    private Memory memory;
    private Disk disk;

    public ComputerFacade(){
        cpu = new CPU();
        memory = new Memory();
        disk = new Disk();
    }

    public void startup(){
        System.out.println("start the computer!");
        cpu.startup();
        memory.startup();
        disk.startup();
        System.out.println("start computer finished!");
    }

    public void shutdown(){
        System.out.println("begin to close the computer!");
        cpu.shutdown();
        memory.shutdown();
        disk.shutdown();
        System.out.println("computer closed!");
    }
}

class CPU {
    public void startup() {
        System.out.println("cpu startup!");
    }

    public void shutdown() {
        System.out.println("cpu shutdown!");
    }

}

class Memory {

    public void startup(){
        System.out.println("memory startup!");
    }

    public void shutdown(){
        System.out.println("memory shutdown!");
    }
}

class Disk {

    public void startup(){
        System.out.println("disk startup!");
    }

    public void shutdown(){
        System.out.println("disk shutdown!");
    }
}
