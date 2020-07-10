package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.annotation.DTransaction;
import com.yqkj.components.dtransaction.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 审核业务
 *
 */
@Service
@DTransaction
public class ApprovalService implements IDTransation{

    @Autowired
    private ApprovalSubA approvalSubA;

    @Autowired
    private ApprovalSubB approvalSubB;

    @Override
    public String getDTServiceName() {
        return "ApprovalService";
    }

    @Override
    public IDTransactionResponse execute(IDTransactionHeader request) {
        ApprovalRequest ar = (ApprovalRequest)request;

        ApprovalSubARequest asar = new ApprovalSubARequest(ar.isSubAPass(), ar.isSubAConfirmPass(), ar.isSubARollbackPass());
        approvalSubA.execute(asar);

        ApprovalSubBRequest asbr = new ApprovalSubBRequest(ar.isSubBPass(), ar.isSubBConfirmPass(), ar.isSubBRollbackPass());
        approvalSubB.execute(asbr);

        return null;
    }

    @Override
    public IDTransactionResponse confirm(IDTransactionHeader request) {
        return null;
    }

    @Override
    public IDTransactionResponse rollback(IDTransactionHeader request) {
        return null;
    }
}
