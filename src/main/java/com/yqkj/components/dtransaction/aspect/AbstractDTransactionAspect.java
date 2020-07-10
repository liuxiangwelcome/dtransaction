package com.yqkj.components.dtransaction.aspect;

import com.yqkj.components.dtransaction.DTStepSeqManager;
import com.yqkj.components.dtransaction.IDTransactionManager;
import com.yqkj.components.dtransaction.bean.IDTransactionHeader;
import com.yqkj.components.dtransaction.exceptions.DTransactionException;
import com.yqkj.components.dtransaction.utils.DTransactionUtil;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public abstract class AbstractDTransactionAspect {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected IDTransactionManager dtManager;
    @Autowired
    protected DTStepSeqManager stepSeqManager;

    protected void beginDTX() {
        dtManager.createDTX();
    }

    protected void checkParam(JoinPoint joinPoint) {
        // 检查入参，判断是否为空，判断是否继承AbstractDTransactionRequest类
        Object[] params = joinPoint.getArgs();
        if (params == null || params.length != 1) {
            throw new DTransactionException(DTransactionUtil.ERROR_CODE_PARAM, MessageFormat.format("target {0} must have one param", joinPoint.getTarget()));
        }

        if (params[0] == null) {
            throw new DTransactionException(DTransactionUtil.ERROR_CODE_PARAM, MessageFormat.format("target {0} param is null", joinPoint.getTarget()));
        }

        if (!(params[0] instanceof IDTransactionHeader)) {
            throw new DTransactionException(DTransactionUtil.ERROR_CODE_PARAM, MessageFormat.format("target {0} param {1} must implements IDTransactionHeader", joinPoint.getTarget(), params[0]));
        }
    }

}
