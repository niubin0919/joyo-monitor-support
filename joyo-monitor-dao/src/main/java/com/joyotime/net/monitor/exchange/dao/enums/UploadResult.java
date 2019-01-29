package com.joyotime.net.monitor.exchange.dao.enums;

/**
 * @author rollinkin
 * @date 2017-07-19
 */
public enum UploadResult {

    UNKOWN(0),

    SUCCESS(1),

    FAIL(2),

    ;

    private int result;

    UploadResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }
}
