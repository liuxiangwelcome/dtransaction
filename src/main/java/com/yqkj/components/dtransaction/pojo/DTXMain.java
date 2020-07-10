package com.yqkj.components.dtransaction.pojo;

import com.yqkj.components.dtransaction.enums.MainStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "dtx_main")
public class DTXMain implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 会话ID
     */
    @Column(nullable = false, unique = true, name = "session_id")
    private Long sessionId;

    /**
     * 全局事务状态，1：创建，2：成功，3：失败，4：提交成功，5：提交失败，6：回滚成功，7：回滚失败
     */
    @Column(nullable = false, name = "status")
    @Convert(converter = MainStatus2DB.class)
    private MainStatus status;

    /**
     * 主程序执行次数
     */
    @Column(nullable = false, name = "execute_times")
    private Integer executeTimes = 0;

    /**
     * 提交执行次数
     */
    @Column(nullable = false, name = "confirm_times")
    private Integer confirmTimes = 0;

    /**
     * 回滚执行次数
     */
    @Column(nullable = false, name = "rollback_times")
    private Integer rollbackTimes = 0;

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

    /**
     * 失效时间
     */
    @Column(nullable = true, name = "expired_time")
    private Date expiredTime;

    /**
     * 备注
     */
    @Column(nullable = true, name = "remark", length = 255)
    private String remark;

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

    public MainStatus getStatus() {
        return status;
    }

    public void setStatus(MainStatus status) {
        this.status = status;
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

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getExecuteTimes() {
        return executeTimes;
    }

    public void setExecuteTimes(Integer executeTimes) {
        this.executeTimes = executeTimes;
    }

    public Integer getConfirmTimes() {
        return confirmTimes;
    }

    public void setConfirmTimes(Integer confirmTimes) {
        this.confirmTimes = confirmTimes;
    }

    public Integer getRollbackTimes() {
        return rollbackTimes;
    }

    public void setRollbackTimes(Integer rollbackTimes) {
        this.rollbackTimes = rollbackTimes;
    }
}
