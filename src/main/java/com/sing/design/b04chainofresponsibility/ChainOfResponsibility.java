package com.sing.design.b04chainofresponsibility;

/**
 * 责任链模式
 *
 * @author songbo
 * @since 2022-07-05
 */
public class ChainOfResponsibility {
    public static void main(String[] args) {
        Handle handleFirst = new LevelFirstHandle();
        Handle handleSecond = new LevelSecondHandle();

        handleFirst.setNextHandle(handleSecond);

        handleFirst.handleRequest(2);

    }
}

abstract class Handle {
    private Handle nextHandle;

    public abstract void handleRequest(int day);

    public Handle getNextHandle() {
        return nextHandle;
    }

    public void setNextHandle(Handle nextHandle) {
        this.nextHandle = nextHandle;
    }
}

class LevelFirstHandle extends Handle {

    @Override
    public void handleRequest(int day) {
        if(day <= 1){
            System.out.println("组长批准了我"+day+"天假期");
        }else{
            if(getNextHandle() != null){
                getNextHandle().handleRequest(day);
            }else{
                System.out.println("没有找到人批准");
            }
        }
    }
}

class LevelSecondHandle extends Handle {

    @Override
    public void handleRequest(int day) {
        if(day >1 && day <=3){
            System.out.println("经理批准了我"+day+"天假期");
        }else{
            if(getNextHandle() != null){
                getNextHandle().handleRequest(day);
            }else{
                System.out.println("没有找到人批准");
            }
        }
    }
}
