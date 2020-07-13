package com.yqkj.components.dtransaction.builds;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class LogsContentBuild {

    private LogsContentBuild(){}

    public static LogsContentBuild getInstance() {
        return new LogsContentBuild();
    }

    private static final String KEY_ID = "id";
    private static final String KEY_PARENTID = "parentId";
    private static final String KEY_SESSIONID = "sessionId";
    private static final String KEY_STEPSEQ = "stepSeq";
    private static final String KEY_STATUS = "status";
    private static final String KEY_CONFIRMTIME = "confirmTime";
    private static final String KEY_CONFIRMTIMES = "confirmTimes";
    private static final String KEY_EXECUTETIME = "executeTime";
    private static final String KEY_EXECUTETIMES = "executeTimes";
    private static final String KEY_ROLLBACKTIME = "rollbackTime";
    private static final String KEY_ROLLBACKTIMES = "rollbackTimes";
    private static final String KEY_CREATETIME = "createTime";
    private static final String KEY_UPDATETIME = "updateTime";

    private static final String KEY_SERVICENAME = "serviceName";
    private static final String KEY_REQUESTCONTENT = "requestContent";
    private static final String KEY_REQUESTCONTENTCLASS = "requestContentClass";
    private static final String KEY_RESPONSECONTENT = "responseContent";
    private static final String KEY_RESPONSECONTENTCLASS = "responseContentClass";

    private JSONObject o = new JSONObject();

    public LogsContentBuild buildId(Long id) {
        o.put(KEY_ID, id);
        return this;
    }
    public LogsContentBuild buildparentId(Long parentId) {
        o.put(KEY_PARENTID, parentId);
        return this;
    }

    public LogsContentBuild buildSessionId(Long sessionId) {
        o.put(KEY_SESSIONID, sessionId);
        return this;
    }

    public LogsContentBuild buildStepSeq(int stepSeq) {
        o.put(KEY_STEPSEQ, stepSeq);
        return this;
    }

    public LogsContentBuild buildStatus(String status) {
        o.put(KEY_STATUS, status);
        return this;
    }

    public LogsContentBuild buildConfirmTime(Date confirmTime) {
        o.put(KEY_CONFIRMTIME, confirmTime);
        return this;
    }

    public LogsContentBuild buildConfirmTimes(int confirmTimes) {
        o.put(KEY_CONFIRMTIMES, confirmTimes);
        return this;
    }

    public LogsContentBuild buildExecuteTime(Date executeTime) {
        o.put(KEY_EXECUTETIME, executeTime);
        return this;
    }

    public LogsContentBuild buildExecuteTimes(int executeTimes) {
        o.put(KEY_EXECUTETIMES, executeTimes);
        return this;
    }

    public LogsContentBuild buildRollbackTime(Date rollbackTime) {
        o.put(KEY_ROLLBACKTIME, rollbackTime);
        return this;
    }

    public LogsContentBuild buildRollbackTimes(int rollbackTimes) {
        o.put(KEY_ROLLBACKTIMES, rollbackTimes);
        return this;
    }

    public LogsContentBuild buildCreateTime(Date createTime) {
        o.put(KEY_CREATETIME, createTime);
        return this;
    }

    public LogsContentBuild buildUpdateTime(Date updateTime) {
        o.put(KEY_UPDATETIME, updateTime);
        return this;
    }

    public LogsContentBuild buildServiceName(String serviceName) {
        o.put(KEY_SERVICENAME, serviceName);
        return this;
    }

    public LogsContentBuild buildRequestContent(String requestContent) {
        o.put(KEY_REQUESTCONTENT, requestContent);
        return this;
    }

    public LogsContentBuild buildRequestContentClass(String requestContentClass) {
        o.put(KEY_REQUESTCONTENTCLASS, requestContentClass);
        return this;
    }

    public LogsContentBuild buildResponseContent(String responseContent) {
        o.put(KEY_RESPONSECONTENT, responseContent);
        return this;
    }

    public LogsContentBuild buildResponseContentClass(String responseContentClass) {
        o.put(KEY_RESPONSECONTENTCLASS, responseContentClass);
        return this;
    }

    public LogsContentBuild buildObject(String key, Object obj){
        o.put(key, obj);
        return this;
    }

    public String build() {
        return o.toJSONString();
    }
}
