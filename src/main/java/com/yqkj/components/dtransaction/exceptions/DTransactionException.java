package com.yqkj.components.dtransaction.exceptions;

public class DTransactionException extends RuntimeException {

    private String code;

    public DTransactionException(String code, String message) {
        super("code: " + code + ", " + message);
        this.code = code;
    }

    public DTransactionException(String code, String message, Throwable throwable) {
        super("code: " + code + ", " + message, throwable);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
