package com.sing.tank.net;

/**
 * @author songbo
 * @since 2022-08-19
 */
public enum MsgEnum {
    /***普通字符串**/
    MESSAGE("Str"),
    /***坦克加入**/
    TANK_JOIN("TankJoin"),
    TANK_DIRECTION_CHANGED("TankDirectionChanged"),
    TANK_STOP("TankStop"),
    TANK_START_MOVING("TankStartMoving"),
    BULLET_NEW("BulletNew"),
    TANK_DIE("TankDie"),
    ;
    private String value;

    MsgEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
