package com.joyotime.net.monitor.exchange.dao.enums;

import com.joyotime.net.monitor.exchange.exception.JoyoException;

/**
 * 发送到多个地方时的条件类型
 */
public enum MutilSendConditionType {
    /**
     * 没有条件
     */
    NO_CONDITION(0),

    /**
     * 门店所在的区县
     */
    STORE_AREA_ZONE(1),
    ;

    private int type;

    MutilSendConditionType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static MutilSendConditionType getConditionType(int conditionType) {
        for (MutilSendConditionType mutilSendConditionType : MutilSendConditionType.values()) {
            if (mutilSendConditionType.type == conditionType) {
                return mutilSendConditionType;
            }
        }

        throw new JoyoException(10, "未知的条件类型:" + conditionType);
    }
}
