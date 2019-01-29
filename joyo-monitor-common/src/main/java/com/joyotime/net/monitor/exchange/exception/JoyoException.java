package com.joyotime.net.monitor.exchange.exception;

/**
 * @author rollinkin
 * @date 2017-07-05
 */
public class JoyoException extends RuntimeException {

    private int code;

    private String errorMsg;

    public JoyoException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.errorMsg = errorCode.getMsg();
    }

    public JoyoException(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
