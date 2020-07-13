package com.yqkj.components.dtransaction.pojo;

import com.yqkj.components.dtransaction.enums.DTXLogsType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "dtx_logs", indexes = {@Index(columnList = "session_id")})
public class DTXLogs implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 会话ID
     */
    @Column(nullable = false, name = "session_id")
    private Long sessionId;

    /**
     * 日志类型：1：全局事务，2：子事务
     */
    @Column(nullable = false, name = "types")
    @Convert(converter = DTXLogsType2DB.class)
    private DTXLogsType types;

    /**
     * 来源ID，默认为空
     */
    @Column(nullable = false, name = "source_id")
    private Long sourceId = 0l ;

    /**
     * 变更记录
     */
    @Column(nullable = true, name = "content")
    @Lob
    private String content;

    /**
     * 创建时间
     */
    @Column(nullable = false, name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(nullable = false, name = "update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public DTXLogsType getTypes() {
        return types;
    }

    public void setTypes(DTXLogsType types) {
        this.types = types;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
}
