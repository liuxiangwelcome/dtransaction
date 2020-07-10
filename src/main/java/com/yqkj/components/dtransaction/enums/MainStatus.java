package com.yqkj.components.dtransaction.enums;

public enum MainStatus {

    CREATED(1, "新建"),

    SUCCEED(2, "成功"),

    FAILED(3, "失败"),

    CONFIRMDONE(4, "提交完成"),

    CONFIRMFAILED(5, "提交失败"),

    ROLLBACKDONE(6, "回滚完成"),

    ROLLBACKFAILED(7, "回滚失败");

    private int code;
    private String msg;

    MainStatus(int code, String msg) {
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

    public static MainStatus fromValue(int code) {
        for (MainStatus s : MainStatus.values()) {
            if (s.getCode() == code) {
                return s;
            }
        }
        return null;
    }
}
