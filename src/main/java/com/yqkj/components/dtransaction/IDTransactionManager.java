package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.enums.MainStatus;
import com.yqkj.components.dtransaction.enums.RollbackStrategy;
import com.yqkj.components.dtransaction.enums.StepStatus;

public interface IDTransactionManager {

    /**
     * 获取会话ID，在当前线程中，只能获取到同一个会话ID，不会产生多个会话ID
     *
     * @return
     */
    public Long getSessionId();

    /**
     * 判断是否已经创建会话
     *
     * @return
     */
    public boolean isExistSession();

    /**
     * 创建全局事务，该方法为独立事务，不与业务事务有关联，在DTXMain创建一条记录
     */
    public void createDTX();

    /**
     * 创建子事务，该方法为独立事务，不与业务事务有关联，在DTXStep创建一条记录
     * 记录请求的服务名，请求参数信息
     *
     * @param parentStepId      父事务ID
     * @param seq               事务顺序
     * @param serviceName       服务名（confirm和cancel中根据它获取spring bean）
     * @param requetsContent    请求参数内容（JSON格式）
     * @return
     */
    public Long createStep(Long parentStepId, Integer seq, String serviceName, Object requetsContent);

    /**
     * 更新全局事务状态“成功”
     *
     * @param sessionId
     * @param remark
     */
    public void updateMainSuccess(Long sessionId, String remark);

    /**
     * 更新全局事务状态“失败”
     *
     * @param sessionId
     * @param remark
     */
    public void updateMainFailed(Long sessionId, String remark);

    /**
     * 更新子事务状态，该方法为独立事务，不与业务事务有关联
     *
     * @param stepId
     * @param status
     * @param remark
     */
    public void updateStepStatus(Long stepId, StepStatus status, String remark);

    /**
     * 所有子事务提交
     * 原则：
     * 1.中断原则，即，中途有一个事务提交出错，则后续事务不再被调用confirm；
     * 2.重试原则，即，子事务提交出错，将在一次全局事务中重试N次（每次全局事务重试，也会对出错的子事务进行再重试）；
     * 3.子事务（DTXStep）状态为“成功”“提交失败”，可以发起confirm操作
     *
     */
    public void confirmAll();

    /**
     * 所有子事务回滚
     * 原则：
     * 1.回滚策略分为：
     *      1.1：中断原则，即，中途有一个事务回滚出错，则后续事务不再被调用rollback；
     *      1.2：尽全力原则，即，中途有一个事务回滚出错，后续事务依然继续被调用rollback；
     * 2.重试原则，即，子事务回滚出错，将在一次全局事务中重试N次（每次全局事务重试，也会对出错的子事务进行再重试）；
     * 3.子事务（DTXStep）状态为“成功”“提交成功”“提交失败”“回滚失败”，可发起回滚操作；
     *
     * @param rollbackStrategy  回滚策略
     */
    public void rollbackAll(RollbackStrategy rollbackStrategy);

    /**
     * 清空线程变量，必须要调用，否则会造成内存溢出
     *
     */
    public void clear();
}
