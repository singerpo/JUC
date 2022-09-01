package com.sing.herostory.model;

public class User {
    private int userId;
    private String heroAvatar;
    // 血量
    private int hp = 100;
    private MoveState moveState;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHeroAvatar() {
        return heroAvatar;
    }

    public void setHeroAvatar(String heroAvatar) {
        this.heroAvatar = heroAvatar;
    }

    public MoveState getMoveState() {
        return moveState;
    }

    public void setMoveState(MoveState moveState) {
        this.moveState = moveState;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}

class MoveState {
    // 起始位置 X
    float fromPosX = 1;
    // 起始位置 Y
    float fromPosY = 2;
    // 移动到位置 X
    float toPosX = 3;
    // 移动到位置 Y
    float toPosY = 4;
    // 启程时间戳
    long startTime = 5;
}
