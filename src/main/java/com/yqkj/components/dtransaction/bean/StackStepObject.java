package com.yqkj.components.dtransaction.bean;

public class StackStepObject {
    public StackStepObject(Long stepId, int seq) {
        this.stepId = stepId;
        this.seq = seq;
    }

    private Long stepId;
    private int seq = 1;

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
