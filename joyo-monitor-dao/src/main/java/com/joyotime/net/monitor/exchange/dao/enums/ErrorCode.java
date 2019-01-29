package com.joyotime.net.monitor.exchange.dao.enums;

/**
 * @author rollinkin
 * @date 2017-07-13
 */
public enum ErrorCode {

    INDEX_ERROR(60001, "生成索引文件时，无可用的数据文件"),

    FTP_ERROR(60002, "FTP上传错误"),

    PARAM_LOST(70001, "参数缺失"),

    PLATFORM_CONFIG_LOST(70002, "平台无对接配置信息"),

    ;

    private int code;

    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
