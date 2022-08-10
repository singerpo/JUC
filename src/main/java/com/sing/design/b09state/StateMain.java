package com.sing.design.b09state;



/**
 * 状态模式
 * 就是允许对象在内部状态发生改变时改变它的行为，对象看起来就好像修改了它的类，也就是说以状态为原子来改变它的行为，而不是通过行为来改变状态。
 *
 * @author songbo
 * @since 2022-08-09
 */
public class StateMain {
    public static void main(String[] args) {
        Room room = new Room();
        room.bookRoom();
        room.checkInRoom();
        room.bookRoom();
        System.out.println(room.getState());
        room.checkOutRoom();
        room.checkInRoom();
        System.out.println(room.getState());
    }
}

// 状态接口
interface State {
    /**
     * 预订房间
     */
    void bookRoom();

    /**
     * 退订房间
     */
    void unsubscribeRoom();

    /**
     * 入住
     */
    void checkInRoom();

    /**
     * 退房
     */
    void checkOutRoom();
}

class Room implements State {
    /*** 空闲状态 **/
    private State freeState;
    /*** 预订状态 **/
    private State bookedState;
    /*** 入住状态 **/
    private State checkInState;

    private State state;

    public Room() {
        freeState = new FreeState(this);
        bookedState = new BookedState(this);
        checkInState = new CheckInState(this);

        state = freeState;
    }

    @Override
    public void bookRoom() {
        state.bookRoom();
    }

    @Override
    public void unsubscribeRoom() {
        state.unsubscribeRoom();

    }

    @Override
    public void checkInRoom() {
        state.checkInRoom();

    }

    @Override
    public void checkOutRoom() {
        state.checkOutRoom();

    }


    public State getFreeState() {
        return freeState;
    }

    public void setFreeState(State freeState) {
        this.freeState = freeState;
    }

    public State getCheckInState() {
        return checkInState;
    }

    public void setCheckInState(State checkInState) {
        this.checkInState = checkInState;
    }

    public State getBookedState() {
        return bookedState;
    }

    public void setBookedState(State bookedState) {
        this.bookedState = bookedState;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}

// 空闲状态只能预订和入住
class FreeState implements State {
    Room room;

    public FreeState(Room room) {
        this.room = room;
    }

    @Override
    public void bookRoom() {
        System.out.println("预订成功");
        // 状态变成已预订
        room.setState(room.getBookedState());
    }

    @Override
    public void unsubscribeRoom() {
        // 无操作
    }

    @Override
    public void checkInRoom() {
        System.out.println("入住成功");
        // 状态变成已入住
        room.setState(room.getCheckInState());
    }

    @Override
    public void checkOutRoom() {
        // 无操作
    }
}

// 预订状态只能退订和入住
class BookedState implements State {
    Room room;

    public BookedState(Room room) {
        this.room = room;
    }

    @Override
    public void bookRoom() {
        // 无操作
    }

    @Override
    public void unsubscribeRoom() {
        System.out.println("退订成功");
        // 状态变成空闲
        room.setState(room.getFreeState());
    }

    @Override
    public void checkInRoom() {
        System.out.println("入住成功");
        // 状态变成已入住
        room.setState(room.getCheckInState());
    }

    @Override
    public void checkOutRoom() {
        // 无操作
    }
}

// 入住状态只能退房
class CheckInState implements State {
    Room room;

    public CheckInState(Room room) {
        this.room = room;
    }

    @Override
    public void bookRoom() {
        // 无操作
    }

    @Override
    public void unsubscribeRoom() {
        // 无操作
    }

    @Override
    public void checkInRoom() {
        // 无操作
    }

    @Override
    public void checkOutRoom() {
        System.out.println("退房成功");
        // 状态变成空闲
        room.setState(room.getFreeState());
    }
}
