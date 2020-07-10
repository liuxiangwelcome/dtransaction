package com.yqkj.components.dtransaction.bean;

import java.math.BigDecimal;

public class PaymentRequest extends AbstractDTransactionRequest {
    /**
     * 付款人
     */
    private String user;
    /**
     * 付款金额
     */
    private BigDecimal amount;

    public PaymentRequest(String user, BigDecimal amount) {
        this.user = user;
        this.amount = amount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
