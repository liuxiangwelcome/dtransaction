package com.yqkj.components.dtransaction.bean;

public abstract class AbstractDTransactionRequest implements IDTransactionHeader {

    private DTransactionHeader transactionHeader;

    @Override
    public DTransactionHeader getTransactionHeader() {
        return transactionHeader;
    }

    @Override
    public void setTransactionHeader(DTransactionHeader transactionHeader) {
        this.transactionHeader = transactionHeader;
    }
}
