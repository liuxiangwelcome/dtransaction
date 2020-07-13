package com.yqkj.components.dtransaction.builds;

import com.yqkj.components.dtransaction.enums.DTXLogsType;
import com.yqkj.components.dtransaction.pojo.DTXLogs;
import com.yqkj.components.dtransaction.utils.DateUtil;

import java.util.Date;

public class DTXLogsBuild {

    private DTXLogs logs = new DTXLogs();

    public static DTXLogsBuild getInstance(){
        return new DTXLogsBuild();
    }

    private DTXLogsBuild(){
        logs = new DTXLogs();
    }

    public DTXLogsBuild(DTXLogs logs){
        this.logs = logs;
    }

    public DTXLogsBuild buildId(Long id) {
        logs.setId(id);
        return this;
    }

    public DTXLogsBuild buildSessionId(Long sessionId) {
        logs.setSessionId(sessionId);
        return this;
    }

    public DTXLogsBuild buildType(DTXLogsType type) {
        logs.setTypes(type);
        return this;
    }

    public DTXLogsBuild buildSourceId(Long sourceId) {
        logs.setSourceId(sourceId);
        return this;
    }

    public DTXLogsBuild buildContent(String content) {
        logs.setContent(content);
        return this;
    }

    public DTXLogsBuild buildCreateTime(Date createTime) {
        logs.setCreateTime(createTime);
        return this;
    }

    public DTXLogsBuild buildUpdateTime(Date updateTime) {
        logs.setUpdateTime(updateTime);
        return this;
    }

    public DTXLogsBuild buildCommon(Long sessionId, DTXLogsType type, Long sourceId, String content){
        return this.buildSessionId(sessionId)
                .buildType(type)
                .buildSourceId(sourceId)
                .buildContent(content)
                .buildCreateTime(DateUtil.current())
                .buildUpdateTime(DateUtil.current());
    }

    public DTXLogs build(){
        return logs;
    }
}
