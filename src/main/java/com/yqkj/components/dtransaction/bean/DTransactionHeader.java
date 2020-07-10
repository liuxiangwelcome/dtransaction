package com.yqkj.components.dtransaction.bean;

public class DTransactionHeader {

    private Long sessionId;

    private Long stepId;

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getStepId() {
        return stepId;
    }
}
