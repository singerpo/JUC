package com.sing.tank.net;

/**
 * @author songbo
 * @since 2022-08-19
 */
public enum MsgEnum {
    /***普通字符串**/
    MESSAGE,
    /***坦克加入**/
    TANK_JOIN,
    TANK_DIRECTION_CHANGED,
    TANK_STOP,
    TANK_START_MOVING,
    BULLET_NEW,
    TANK_DIE,
    ;
}
