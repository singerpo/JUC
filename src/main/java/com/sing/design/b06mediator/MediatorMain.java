package com.sing.design.b06mediator;

/**
 * 中介者(调停者)模式
 *
 * @author songbo
 * @since 2022-06-24
 */
public class MediatorMain {
    public static void main(String[] args) {
        //一个房主、一个租房者、一个中介机构
        ConcreteMediator mediator = new ConcreteMediator();

        //房主和租房者只需要知道中介机构即可
        HouseOwner houseOwner = new HouseOwner("张三", mediator);
        Tenant tenant = new Tenant("李四", mediator);

        //中介结构要知道房主和租房者
        mediator.setHouseOwner(houseOwner);
        mediator.setTenant(tenant);

        tenant.contact("听说你那里有三室的房主出租.....");
        houseOwner.contact("是的!请问你需要租吗?");
    }
}

abstract class Mediator {
    public abstract void contact(String message, Person person);
}

class ConcreteMediator extends Mediator{
    //首先中介结构必须知道所有房主和租房者的信息
    private HouseOwner houseOwner;
    private Tenant tenant;

    public HouseOwner getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(HouseOwner houseOwner) {
        this.houseOwner = houseOwner;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public void contact(String message, Person person) {
        //如果是房主，则租房者获得信息
        if(person == houseOwner){
            tenant.getMessage(message);
        }
        else{
            //反之则是获得房主信息
            houseOwner.getMessage(message);
        }
    }
}

abstract class Person {
    String name;
    Mediator mediator;

    Person(String name, Mediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }
}

// 房主
class HouseOwner extends Person {

    HouseOwner(String name, Mediator mediator) {
        super(name, mediator);
    }

    /**
     * 与中介者联系
     * @param message 信息
     */
    public void contact(String message) {
        mediator.contact(message, this);
    }

    /**
     * 获取信息
     * @param message
     */
    public void getMessage(String message) {
        System.out.println("房主:" + name + ",获得信息：" + message);
    }
}

class Tenant extends Person {

    Tenant(String name, Mediator mediator) {
        super(name, mediator);
    }

    /**
     * 与中介者联系
     * @param message 信息
     */
    public void contact(String message) {
        mediator.contact(message, this);
    }

    /**
     * 获取信息
     * @param message
     */
    public void getMessage(String message) {
        System.out.println("租房者:" + name + ",获得信息：" + message);
    }
}







