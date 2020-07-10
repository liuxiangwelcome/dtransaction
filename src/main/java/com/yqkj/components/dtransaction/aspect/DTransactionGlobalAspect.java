package com.yqkj.components.dtransaction.aspect;

import com.yqkj.components.dtransaction.enums.RollbackStrategy;
import com.yqkj.components.dtransaction.utils.DTransactionUtil;

/**
 * 全局事务拦截处理
 *
 */
public class DTransactionGlobalAspect extends AbstractDTransactionAspect {

    /**
     * 初始化全局事务
     * 判断是否存在sessionId，若不存在则初始化全局事务
     * 压入根节点事务，默认ID=0L
     *
     */
    protected void initDTX(){
        if (!this.dtManager.isExistSession()) {

            logger.debug("begin init global transaction.");

            // 初始化子事务堆栈，同时压入根节点事务ID（0）
            stepSeqManager.initStepSeq();
            stepSeqManager.pushStepStack(DTransactionUtil.DEFAULT_PARENT_STEP_ID);
            dtManager.createDTX();

            logger.debug("create new transaction sessionId {}", this.dtManager.getSessionId());
        }
    }

    /**
     * 全局提交，将根据事务树从子节点开始提交
     *
     */
    protected void confirmDTX(){
        Long sessionId = this.dtManager.getSessionId();

        logger.debug("DTX begin confirm sessionId {}", sessionId);

        // 更新全局事务状态为“成功”
        this.dtManager.updateMainSuccess(this.dtManager.getSessionId(), null);
        // 调用所有子事务的confirm
        this.dtManager.confirmAll();
        // 清空子事务堆栈（若是提交失败，会抛出异常，暂时不用清理线程变量，由回滚操作来完成清理）
        stepSeqManager.clearStepSeq();
        // 清空事务管理器线程变量
        this.dtManager.clear();

        logger.debug("DTX end confirm sessionId {}", sessionId);
    }

    /**
     * 全局回滚，将根据事务树从子节点开始回滚，只回滚状态为（SUCCEED，CONFIRMDONE，CONFIRMFAILED，ROLLEBACKFAILED）的事务
     *
     * @param rollbackStrategy  回滚策略
     */
    protected void rollbackDTX(RollbackStrategy rollbackStrategy) {
        Long sessionId = this.dtManager.getSessionId();

        logger.debug("DTX begin rollback sessionId {}", sessionId);

        try {
            // 更新全局事务状态为“失败”
            this.dtManager.updateMainFailed(this.dtManager.getSessionId(), null);
            // 调用已**子事务的rollback
            this.dtManager.rollbackAll(rollbackStrategy);

            logger.debug("DTX end rollback sessionId {}", sessionId);

        } catch (Throwable e) {
            throw e;
        } finally {
            // 清空子事务堆栈（回滚操作已是最后一道关卡，必须要清理线程变量，会造成内存溢出）
            stepSeqManager.clearStepSeq();
            // 清空事务管理器线程变量
            this.dtManager.clear();
        }
    }
}
