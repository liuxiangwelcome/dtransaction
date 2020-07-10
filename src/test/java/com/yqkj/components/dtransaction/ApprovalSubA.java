package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.annotation.DTransaction;
import com.yqkj.components.dtransaction.bean.ApprovalSubARequest;
import com.yqkj.components.dtransaction.bean.IDTransactionHeader;
import com.yqkj.components.dtransaction.bean.IDTransactionResponse;
import com.yqkj.components.dtransaction.exceptions.ApprovalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@DTransaction
public class ApprovalSubA implements IDTransation{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getDTServiceName() {
        return "ApprovalSubA";
    }

    @Override
    public IDTransactionResponse execute(IDTransactionHeader request) {
        ApprovalSubARequest asa = (ApprovalSubARequest)request;

        logger.debug("开始审核子业务A {}", asa.isSubPass());

        if (!asa.isSubPass()){
            logger.error("审核子业务A不通过");
            throw new ApprovalException("审核子业务A不通过");
        }

        logger.debug("审核子业务A完成");

        return null;
    }

    @Override
    public IDTransactionResponse confirm(IDTransactionHeader request) {
        ApprovalSubARequest asa = (ApprovalSubARequest)request;

        logger.debug("开始提交子业务A {}", asa.isSubAConfirmPass());

        if (!asa.isSubAConfirmPass()){
            logger.error("提交子业务A不通过");
            throw new ApprovalException("提交子业务A不通过");
        }

        logger.debug("提交子业务A完成");
        return null;
    }

    @Override
    public IDTransactionResponse rollback(IDTransactionHeader request) {

        ApprovalSubARequest asa = (ApprovalSubARequest)request;

        logger.debug("开始回滚子业务A {}", asa.isSubARollbackPass());

        if (!asa.isSubARollbackPass()){
            logger.error("回滚子业务A不通过");
            throw new ApprovalException("回滚子业务A不通过");
        }

        logger.debug("回滚子业务A完成");

        return null;
    }
}
