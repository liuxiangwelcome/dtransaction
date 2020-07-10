package com.yqkj.components.dtransaction.utils;

public class DTransactionUtil {

    /**
     * 初始化虚拟根节点，默认为-1
     */
    public static final Long INIT_ROOT_STEP_ID = -1L;

    /**
     * 父事务id，默认为0
     */
    public static final Long DEFAULT_PARENT_STEP_ID = 0L;

    /**
     * 事务顺序，默认为1
     */
    public static final Integer DEFAULT_STEP_SEQ = 0;

    /**
     * 参数异常代码
     */
    public static final String ERROR_CODE_PARAM = "700001";

    /**
     * 反射异常代码
     */
    public static final String ERROR_CODE_REFLEX = "700002";

    /**
     * 提交（confirm）步骤异常
     */
    public static final String ERROR_CODE_CONFIRM = "700003";

    /**
     * 回滚（rollback）步骤异常
     */
    public static final String ERROR_CODE_ROLLBACK = "700004";
}
