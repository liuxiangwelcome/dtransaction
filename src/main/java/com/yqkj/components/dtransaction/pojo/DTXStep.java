package com.yqkj.components.dtransaction.pojo;

import com.yqkj.components.dtransaction.enums.StepStatus;
import com.yqkj.components.dtransaction.utils.DTransactionUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "dtx_step")
public class DTXStep implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 会话ID
     */
    @Column(nullable = false, name = "session_id")
    private Long sessionId;

    /**
     * 父事务ID，若没有父事务时，则设置为0
     */
    @Column(nullable = false, name = "parent_id")
    private Long parentId = DTransactionUtil.DEFAULT_PARENT_STEP_ID;

    /**
     * 子事务序号
     */
    @Column(nullable = false, name = "step_seq")
    private Integer stepSeq = 1;

    /**
     * 子事务状态，1：创建，2：成功，3：失败，4：提交成功，5：提交失败，6：回滚成功，7：回滚失败
     */
    @Column(nullable = false, name = "status")
    @Convert(converter = StepStatus2DB.class)
    private StepStatus status;

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
     * 提交时间
     */
    @Column(nullable = true, name = "confirm_time")
    private Date confirmTime;

    /**
     * 回滚时间
     */
    @Column(nullable = true, name = "rollback_time")
    private Date rollbackTime;

    /**
     * 服务名称
     */
    @Column(name = "service_name", length = 50)
    private String serviceName;

    /**
     * 请求内容
     */
    @Lob
    @Column(name = "request_content")
    private String requestContent;

    /**
     * 请求内容对象Class全路径
     */
    @Column(name = "request_content_class")
    private String requestContentClass;

    /**
     * 响应内容
     */
    @Lob
    @Column(name = "response_content")
    private String responseContent;

    /**
     * 响应内容对象Class全路径
     */
    @Column(name = "response_content_class")
    private String responseContentClass;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getStepSeq() {
        return stepSeq;
    }

    public void setStepSeq(Integer stepSeq) {
        this.stepSeq = stepSeq;
    }

    public StepStatus getStatus() {
        return status;
    }

    public void setStatus(StepStatus status) {
        this.status = status;
    }

    public Integer getExecuteTimes() {
        return executeTimes;
    }

    public void setExecuteTimes(Integer executeTimes) {
        this.executeTimes = executeTimes;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRequestContentClass() {
        return requestContentClass;
    }

    public void setRequestContentClass(String requestContentClass) {
        this.requestContentClass = requestContentClass;
    }

    public String getResponseContentClass() {
        return responseContentClass;
    }

    public void setResponseContentClass(String responseContentClass) {
        this.responseContentClass = responseContentClass;
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

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getRollbackTime() {
        return rollbackTime;
    }

    public void setRollbackTime(Date rollbackTime) {
        this.rollbackTime = rollbackTime;
    }
}
