package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.annotation.DTransaction;
import com.yqkj.components.dtransaction.bean.ApprovalSubBRequest;
import com.yqkj.components.dtransaction.bean.IDTransactionHeader;
import com.yqkj.components.dtransaction.bean.IDTransactionResponse;
import com.yqkj.components.dtransaction.exceptions.ApprovalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@DTransaction
public class ApprovalSubB implements IDTransation {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getDTServiceName() {
        return "ApprovalSubB";
    }

    @Override
    public IDTransactionResponse execute(IDTransactionHeader request) {
        ApprovalSubBRequest asa = (ApprovalSubBRequest) request;

        logger.debug("开始审核子业务B {}", asa.isSubPass());

        if (!asa.isSubPass()) {
            logger.error("审核子业务B不通过");
            throw new ApprovalException("审核子业务B不通过");
        }

        logger.debug("审核子业务B完成");

        return null;
    }

    @Override
    public IDTransactionResponse confirm(IDTransactionHeader request) {
        ApprovalSubBRequest asa = (ApprovalSubBRequest) request;
        logger.debug("开始提交子业务B {}", asa.isSubBConfirmPass());
        if (!asa.isSubBConfirmPass()) {
            throw new RuntimeException("提交子业务B提交失败");
        }
        logger.debug("提交子业务B完成");
        return null;
    }

    @Override
    public IDTransactionResponse rollback(IDTransactionHeader request) {
        ApprovalSubBRequest asa = (ApprovalSubBRequest) request;
        logger.debug("开始回滚子业务B {}", asa.isSubBRollbackPass());
        if (!asa.isSubBRollbackPass()) {
            throw new RuntimeException("回滚子业务B回滚成功");
        }
        logger.debug("回滚子业务B完成");
        return null;
    }
}
