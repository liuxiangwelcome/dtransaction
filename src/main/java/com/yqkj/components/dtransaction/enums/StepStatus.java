package com.yqkj.components.dtransaction.enums;

public enum StepStatus {

    CREATED(1, "新建"),

    SUCCEED(2, "成功"),

    FAILED(3, "失败"),

    CONFIRMDONE(4, "提交完成"),

    CONFIRMFAILED(5, "提交失败"),

    ROLLBACKDONE(6, "回滚完成"),

    ROLLEBACKFAILED(7, "回滚失败");

    private int code;
    private String msg;

    StepStatus(int code, String msg) {
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

    public static StepStatus fromValue(int code) {
        for (StepStatus s : StepStatus.values()) {
            if (s.getCode() == code) {
                return s;
            }
        }
        return null;
    }
}
