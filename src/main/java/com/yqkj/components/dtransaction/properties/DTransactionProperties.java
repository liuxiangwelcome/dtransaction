package com.yqkj.components.dtransaction.properties;

import com.yqkj.components.dtransaction.enums.StepStatus;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "yqkj.components.dtransaction")
public class DTransactionProperties {

    public DTransactionProperties(){
        allowConfirmStepStatus.add(StepStatus.SUCCEED);
        allowConfirmStepStatus.add(StepStatus.CONFIRMFAILED);

        allowRollbackStepStatus.add(StepStatus.SUCCEED);
        allowRollbackStepStatus.add(StepStatus.CONFIRMDONE);
        allowRollbackStepStatus.add(StepStatus.CONFIRMFAILED);
        allowRollbackStepStatus.add(StepStatus.ROLLEBACKFAILED);
    }

    /**
     * 子事务confirm和cancel重试次数，默认为3
     *
     */
    private int stepRetrySize = 3;

    /**
     * 子事务confirm和cancel重试间隔，默认睡眠100毫秒
     */
    private long stepRetrySleepTime = 100l;

    /**
     * 定义子事务哪些状态下可以提交，默认为SUCCEED（完成），CONFIRMFAILED（提交失败）
     */
    private List<StepStatus> allowConfirmStepStatus = new ArrayList<>();

    /**
     * 定义子事务哪些状态下可以回滚，默认为SUCCEED（完成），CONFIRMDONE（提交成功），CONFIRMFAILED（提交失败），ROLLEBACKFAILED（回滚失败）
     */
    private List<StepStatus> allowRollbackStepStatus = new ArrayList<>();

    public int getStepRetrySize() {
        return stepRetrySize;
    }

    public void setStepRetrySize(int stepRetrySize) {
        this.stepRetrySize = stepRetrySize;
    }

    public long getStepRetrySleepTime() {
        return stepRetrySleepTime;
    }

    public void setStepRetrySleepTime(long stepRetrySleepTime) {
        this.stepRetrySleepTime = stepRetrySleepTime;
    }

    public List<StepStatus> getAllowConfirmStepStatus() {
        return allowConfirmStepStatus;
    }

    public void setAllowConfirmStepStatus(List<StepStatus> allowConfirmStepStatus) {
        this.allowConfirmStepStatus = allowConfirmStepStatus;
    }

    public List<StepStatus> getAllowRollbackStepStatus() {
        return allowRollbackStepStatus;
    }

    public void setAllowRollbackStepStatus(List<StepStatus> allowRollbackStepStatus) {
        this.allowRollbackStepStatus = allowRollbackStepStatus;
    }
}
