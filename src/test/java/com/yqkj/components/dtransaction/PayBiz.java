package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.annotation.DTransaction;
import com.yqkj.components.dtransaction.bean.*;
import com.yqkj.components.dtransaction.enums.RollbackStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DTransaction(ROLLBACK_STRATEGY = RollbackStrategy.Interrupt)
public class PayBiz implements IDTransation{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockService stockService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ApprovalService approvalService;

    @Override
    public String getDTServiceName() {
        return "PayBiz";
    }

    /**
     * 模拟购买商品业务
     *
     * @param request
     * @return
     */
    @Override
    public IDTransactionResponse execute(IDTransactionHeader request) {
        PayRequest pr = (PayRequest)request;

        logger.debug("付款人 {}， 收款人 {}， 金额 {}， 商品数量", pr.getUser(), pr.getTarget(), pr.getAmount(), pr.getSize());

        StockRequest sr = new StockRequest(pr.getSize());
        this.stockService.execute(sr);

        PaymentRequest paymentRequest = new PaymentRequest(pr.getUser(), pr.getAmount());
        paymentService.execute(paymentRequest);

        IncomeRequest ir = new IncomeRequest(pr.getTarget(), pr.getAmount());
        incomeService.execute(ir);

        // 判断是否要审核
        if (pr.isApprovel()) {
            ApprovalRequest ar = new ApprovalRequest(pr.isSubAPass(), pr.isSubAConfirmPass(), pr.isSubARollbackPass(), pr.isSubBPass(), pr.isSubBConfirmPass(), pr.isSubBRollbackPass());
            approvalService.execute(ar);
        }

        PayResponse resp = new PayResponse();
        resp.setResult("success");
        logger.debug("购买商品完成");
        return resp;
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
