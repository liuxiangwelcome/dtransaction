package com.yqkj.components.dtransaction.builds;

import com.yqkj.components.dtransaction.enums.StepStatus;
import com.yqkj.components.dtransaction.pojo.DTXStep;

import java.util.Date;

public class DTXStepBuild {
    private DTXStep s = new DTXStep();

    private LogsContentBuild logs = LogsContentBuild.getInstance();

    public static DTXStepBuild getInstance() {
        return new DTXStepBuild();
    }

    public static DTXStepBuild getInstance(DTXStep step) {
        return new DTXStepBuild(step);
    }

    private DTXStepBuild() {
    }

    private DTXStepBuild(DTXStep step) {
        this.s = step;
    }

    public DTXStepBuild buildId(Long id) {
        s.setId(id);
        logs.buildId(id);
        return this;
    }

    public DTXStepBuild buildSessionId(Long sessionId) {
        s.setSessionId(sessionId);
        logs.buildSessionId(sessionId);
        return this;
    }

    public DTXStepBuild buildParentId(Long parentId) {
        s.setParentId(parentId);
        logs.buildparentId(parentId);
        return this;
    }

    public DTXStepBuild buildStepSeq(Integer stepSeq) {
        s.setStepSeq(stepSeq);
        logs.buildStepSeq(stepSeq);
        return this;
    }

    public DTXStepBuild buildStepStatus(StepStatus status) {
        s.setStatus(status);
        logs.buildStatus(status.name());
        return this;
    }

    public DTXStepBuild buildExecuteTimes(Integer executeTimes) {
        s.setExecuteTimes(executeTimes);
        logs.buildExecuteTimes(executeTimes);
        return this;
    }

    public DTXStepBuild buildConfirmTimes(Integer confirmTimes) {
        s.setConfirmTimes(confirmTimes);
        logs.buildConfirmTimes(confirmTimes);
        return this;
    }

    public DTXStepBuild buildRollbackTimes(Integer rollbackTimes) {
        s.setRollbackTimes(rollbackTimes);
        logs.buildRollbackTimes(rollbackTimes);
        return this;
    }

    public DTXStepBuild buildTime(Date createTime, Date updateTime) {
        return this.buildCreateTime(createTime).buildUpdateTime(updateTime);
    }

    public DTXStepBuild buildCreateTime(Date createTime) {
        s.setCreateTime(createTime);
        logs.buildCreateTime(createTime);
        return this;
    }

    public DTXStepBuild buildUpdateTime(Date updateTime) {
        s.setUpdateTime(updateTime);
        logs.buildUpdateTime(updateTime);
        return this;
    }

    public DTXStepBuild buildConfirmTime(Date confirmTime) {
        s.setConfirmTime(confirmTime);
        logs.buildConfirmTime(confirmTime);
        return this;
    }

    public DTXStepBuild buildRollbackTime(Date rollbackTime) {
        s.setRollbackTime(rollbackTime);
        logs.buildRollbackTime(rollbackTime);
        return this;
    }

    public DTXStepBuild buildRemark(String remark) {
        s.setRemark(remark);
        return this;
    }

    public DTXStepBuild buildServiceName(String serviceName) {
        s.setServiceName(serviceName);
        logs.buildServiceName(serviceName);
        return this;
    }

    public DTXStepBuild buildRequestContent(String requestContent) {
        s.setRequestContent(requestContent);
        logs.buildRequestContent(requestContent);
        return this;
    }

    public DTXStepBuild buildRequestContentClass(String requestContentClass) {
        s.setRequestContentClass(requestContentClass);
        logs.buildRequestContentClass(requestContentClass);
        return this;
    }

    public DTXStepBuild buildResponseContent(String responseContent) {
        s.setResponseContent(responseContent);
        logs.buildResponseContent(responseContent);
        return this;
    }

    public DTXStepBuild buildResponseContentClass(String responseContentClass) {
        s.setResponseContentClass(responseContentClass);
        logs.buildResponseContentClass(responseContentClass);
        return this;
    }

    public LogsContentBuild getLogsContentBuild() {
        return this.logs;
    }

    public DTXStep build() {
        return s;
    }
}
