package com.joyotime.net.monitor.exchange.dao.enums;

/**
 * @author rollinkin
 * @date 2017-08-24
 */
public enum  UploadFailMsg {
    NOT_FOUND_ONLINE("没有上线数据"),

    NOT_FOUND_DEVICE("没有找到设备"),

    NOT_FOUND_STORE("没有获取到门店信息"),

    NOT_FOUND_SAFE_COMCANY("没有找到安全厂商信息"),

    ;

    private String msg;

    UploadFailMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
