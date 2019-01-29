package com.joyotime.net.monitor.exchange.dao.enums;

/**
 * @author rollinkin
 * @date 2017-07-19
 */
public enum MessageType {
    ON_LINE(1),

    OFF_LINE(2),

    NET_LOG(3),

    TERMINAL(4),

    BASIC(5),

    /**
     * 虚拟身份跟踪
     */
    TRACK(6),

    ;


    private int type;

    MessageType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
