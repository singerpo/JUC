package com.sing.herostory.mq;

/**
 * 战斗结果消息
 *
 * @author songbo
 * @since 2022-09-05
 */
public class VictorMsg {
    private Integer winnerId;
    private Integer loserId;

    public Integer getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }

    public Integer getLoserId() {
        return loserId;
    }

    public void setLoserId(Integer loserId) {
        this.loserId = loserId;
    }
}
