package com.yqkj.components.dtransaction.bean;

import java.math.BigDecimal;

public class IncomeRequest extends AbstractDTransactionRequest {
    /**
     * 收款人
     */
    private String user;
    /**
     * 收款金额
     */
    private BigDecimal amount;

    public IncomeRequest(String user, BigDecimal amount) {
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
