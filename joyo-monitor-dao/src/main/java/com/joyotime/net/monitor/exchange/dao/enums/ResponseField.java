package com.joyotime.net.monitor.exchange.dao.enums;

/**
 * @author rollinkin
 * @date 2017-07-06
 */
public enum ResponseField {

    CODE("code"),

    ERROR_MSG("errMsg"),

    ;

    private String field;

    ResponseField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }


}
