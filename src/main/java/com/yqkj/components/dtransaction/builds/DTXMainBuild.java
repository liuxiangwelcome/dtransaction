package com.yqkj.components.dtransaction.builds;

import com.yqkj.components.dtransaction.enums.MainStatus;
import com.yqkj.components.dtransaction.pojo.DTXMain;

import java.util.Date;

public class DTXMainBuild {
    private DTXMain m = new DTXMain();

    public DTXMainBuild() {
    }

    public DTXMainBuild(DTXMain main) {
        this.m = main;
    }

    public DTXMainBuild buildSessionId(Long sessionId) {
        m.setSessionId(sessionId);
        return this;
    }

    public DTXMainBuild buildStatus(MainStatus status) {
        m.setStatus(status);
        return this;
    }

    public DTXMainBuild buildTime(Date createTime, Date updateTime, Date expiredTime) {
        return buildCreateTime(createTime).buildUpdateTime(updateTime).buildExpiredTime(expiredTime);
    }

    public DTXMainBuild buildExecuteTimes(int executeTimes) {
        m.setExecuteTimes(executeTimes);
        return this;
    }

    public DTXMainBuild buildConfirmTimes(int confirmTimes) {
        m.setConfirmTimes(confirmTimes);
        return this;
    }

    public DTXMainBuild buildRollbackTimes(int rollbackTimes) {
        m.setRollbackTimes(rollbackTimes);
        return this;
    }

    public DTXMainBuild buildCreateTime(Date createTime) {
        m.setCreateTime(createTime);
        return this;
    }

    public DTXMainBuild buildUpdateTime(Date updateTime) {
        m.setUpdateTime(updateTime);
        return this;
    }

    public DTXMainBuild buildExpiredTime(Date expiredTime) {
        m.setExpiredTime(expiredTime);
        return this;
    }

    public DTXMainBuild buildRemakr(String remark) {
        m.setRemark(remark);
        return this;
    }

    public DTXMain build() {
        return m;
    }
}
