package com.yqkj.components.dtransaction.enums;

public enum DTXLogsType {

    Main(1, "主事务"),

    Step(2, "子事务");

    private int code;
    private String msg;

    DTXLogsType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static DTXLogsType fromValue(int code) {
        for (DTXLogsType s : DTXLogsType.values()) {
            if (s.getCode() == code) {
                return s;
            }
        }
        return null;
    }
}
