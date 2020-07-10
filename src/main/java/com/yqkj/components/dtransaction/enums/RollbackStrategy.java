package com.yqkj.components.dtransaction.enums;

/**
 * 回滚策略
 */
public enum RollbackStrategy {
    /**
     * 中断策略，默认该选项，中断后应由人工介入
     */
    Interrupt,

    /**
     * 尽全力策略，其中一个回滚失败，继续回滚其他事务
     */
    TryBest;
}
