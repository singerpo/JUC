package com.sing.design.b10memento;

/**
 * 行为型：备忘录模式
 * 提供了一种恢复状态的机制，在不破坏封装的前提下，捕获对象的内部状态，并保存在该对象之外，保证该对象能够恢复到某个历史状态；
 * 备忘录模式将保存的细节封装在备忘录中，除了创建它的创建者之外其他对象都不能访问它，并且实现了即使要改变保存的细节也不影响客户端。
 *
 * @author songbo
 * @since 2022-08-09
 */
public class MementoMain {
    public static void main(String[] args) {
        Originator originator = new Originator(100, 100);
        // 存档
        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(originator.saveMemento());
        // 恢复存档
        originator.restoreMemento(caretaker.getMemento());
    }
}

//备忘录
class Memento {
    public int blood;
    public int magic;

    public Memento(Originator originator) {
        this.blood = originator.blood;
        this.magic = originator.magic;
    }

}

class Originator {
    public int blood;
    public int magic;

    public Originator(int blood, int magic) {
        this.blood = blood;
        this.magic = magic;
    }

    /**
     * 存档 保存当前状态
     * @return
     */
    public Memento saveMemento() {
        return new Memento(this);
    }

    /**
     * 恢复存档
     * @param memento
     */
    public void restoreMemento(Memento memento){
        this.blood = memento.blood;
        this.magic = memento.magic;
    }
}

class Caretaker {
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
