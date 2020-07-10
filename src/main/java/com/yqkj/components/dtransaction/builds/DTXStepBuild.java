package com.yqkj.components.dtransaction.builds;

import com.yqkj.components.dtransaction.enums.StepStatus;
import com.yqkj.components.dtransaction.pojo.DTXStep;

import java.util.Date;

public class DTXStepBuild {
    private DTXStep s = new DTXStep();

    public DTXStepBuild() {
    }

    public DTXStepBuild(DTXStep step) {
        this.s = step;
    }

    public DTXStepBuild buildId(Long id) {
        s.setId(id);
        return this;
    }

    public DTXStepBuild buildSessionId(Long sessionId) {
        s.setSessionId(sessionId);
        return this;
    }

    public DTXStepBuild buildParentId(Long parentId) {
        s.setParentId(parentId);
        return this;
    }

    public DTXStepBuild buildStepSeq(Integer stepSeq) {
        s.setStepSeq(stepSeq);
        return this;
    }

    public DTXStepBuild buildStepStatus(StepStatus status) {
        s.setStatus(status);
        return this;
    }

    public DTXStepBuild buildExecuteTimes(Integer executeTimes) {
        s.setExecuteTimes(executeTimes);
        return this;
    }

    public DTXStepBuild buildConfirmTimes(Integer confirmTimes) {
        s.setConfirmTimes(confirmTimes);
        return this;
    }

    public DTXStepBuild buildRollbackTimes(Integer rollbackTimes) {
        s.setRollbackTimes(rollbackTimes);
        return this;
    }

    public DTXStepBuild buildTime(Date createTime, Date updateTime) {
        return this.buildCreateTime(createTime).buildUpdateTime(updateTime);
    }

    public DTXStepBuild buildCreateTime(Date createTime) {
        s.setCreateTime(createTime);
        return this;
    }

    public DTXStepBuild buildUpdateTime(Date updateTime) {
        s.setUpdateTime(updateTime);
        return this;
    }

    public DTXStepBuild buildConfirmTime(Date confirmTime) {
        s.setConfirmTime(confirmTime);
        return this;
    }

    public DTXStepBuild buildRollbackTime(Date rollbackTime) {
        s.setRollbackTime(rollbackTime);
        return this;
    }

    public DTXStepBuild buildRemark(String remark) {
        s.setRemark(remark);
        return this;
    }

    public DTXStepBuild buildServiceName(String serviceName) {
        s.setServiceName(serviceName);
        return this;
    }

    public DTXStepBuild buildRequestContent(String requestContent) {
        s.setRequestContent(requestContent);
        return this;
    }

    public DTXStepBuild buildRequestContentClass(String requestContentClass) {
        s.setRequestContentClass(requestContentClass);
        return this;
    }

    public DTXStepBuild buildResponseContent(String responseContent) {
        s.setResponseContent(responseContent);
        return this;
    }

    public DTXStepBuild buildResponseContentClass(String responseContentClass) {
        s.setResponseContentClass(responseContentClass);
        return this;
    }

    public DTXStep build() {
        return s;
    }
}
