package com.joyotime.net.monitor.exchange.dao.enums;

/**
 * @author rollinkin
 * @date 2017-07-19
 */
public enum  MessageContentType {

    FILE(1),

    STRING(2),

    ;

    private int type;

    MessageContentType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
