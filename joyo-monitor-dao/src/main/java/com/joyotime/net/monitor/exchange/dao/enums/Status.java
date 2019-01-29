package com.joyotime.net.monitor.exchange.dao.enums;

/**
 * @author rollinkin
 * @date 2017-07-17
 */
public enum Status {

    ENABLE(1),

    DISABLE(0),
    ;

    private int status;

    Status(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
