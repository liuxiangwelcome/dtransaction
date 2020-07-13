package com.yqkj.components.dtransaction.builds;

import com.yqkj.components.dtransaction.enums.MainStatus;
import com.yqkj.components.dtransaction.pojo.DTXMain;

import java.util.Date;

public class DTXMainBuild {

    private DTXMain m = new DTXMain();

    private LogsContentBuild logs = LogsContentBuild.getInstance();

    public static DTXMainBuild getInstance(){
        return new DTXMainBuild();
    }

    public static DTXMainBuild getInstance(DTXMain main){
        return new DTXMainBuild(main);
    }

    private DTXMainBuild() {
    }

    private DTXMainBuild(DTXMain main) {
        this.m = main;
    }

    public DTXMainBuild buildSessionId(Long sessionId) {
        m.setSessionId(sessionId);
        logs.buildSessionId(sessionId);
        return this;
    }

    public DTXMainBuild buildStatus(MainStatus status) {
        m.setStatus(status);
        logs.buildStatus(status.name());
        return this;
    }

    public DTXMainBuild buildTime(Date createTime, Date updateTime, Date expiredTime) {
        return buildCreateTime(createTime).buildUpdateTime(updateTime).buildExpiredTime(expiredTime);
    }

    public DTXMainBuild buildExecuteTimes(int executeTimes) {
        m.setExecuteTimes(executeTimes);
        logs.buildExecuteTimes(executeTimes);
        return this;
    }

    public DTXMainBuild buildConfirmTimes(int confirmTimes) {
        m.setConfirmTimes(confirmTimes);
        logs.buildConfirmTimes(confirmTimes);
        return this;
    }

    public DTXMainBuild buildRollbackTimes(int rollbackTimes) {
        m.setRollbackTimes(rollbackTimes);
        logs.buildRollbackTimes(rollbackTimes);
        return this;
    }

    public DTXMainBuild buildCreateTime(Date createTime) {
        m.setCreateTime(createTime);
        logs.buildCreateTime(createTime);
        return this;
    }

    public DTXMainBuild buildUpdateTime(Date updateTime) {
        m.setUpdateTime(updateTime);
        logs.buildUpdateTime(updateTime);
        return this;
    }

    public DTXMainBuild buildExpiredTime(Date expiredTime) {
        m.setExpiredTime(expiredTime);
        return this;
    }

    public DTXMainBuild buildRemark(String remark) {
        m.setRemark(remark);
        return this;
    }

    public LogsContentBuild getLogsContentBuild() {
        return this.logs;
    }

    public DTXMain build() {
        return m;
    }
}
