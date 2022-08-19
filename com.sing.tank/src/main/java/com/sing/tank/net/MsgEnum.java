package com.sing.tank.net;

/**
 * @author songbo
 * @since 2022-08-19
 */
public enum MsgEnum {
    /***普通字符串**/
    message,
    /***坦克加入**/
    tankJoin,
    tankDirectionChanged,
    tankStop,
    tankStartMoving,
    bulletNew,
    tankDie,
    ;
}
