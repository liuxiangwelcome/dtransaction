package com.yqkj.components.dtransaction.bean;

public class StockRequest extends AbstractDTransactionRequest {
    public StockRequest(int size) {
        this.size = size;
    }

    private int size ;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
