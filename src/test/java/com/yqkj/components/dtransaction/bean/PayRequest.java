package com.yqkj.components.dtransaction.bean;

import java.math.BigDecimal;

public class PayRequest extends AbstractDTransactionRequest {

    /**
     * 付款人
     */
    private String user;

    /**
     * 收款人
     */
    private String target;

    /**
     * 付款金额
     */
    private BigDecimal amount;

    /**
     * 购买商品数量
     */
    private int size;

    private boolean approvel = false;

    private boolean subAPass = false;

    private boolean subAConfirmPass = true;

    private boolean subARollbackPass = true;

    private boolean subBPass = false;

    private boolean subBConfirmPass = true;

    private boolean subBRollbackPass = true;

    public PayRequest(String user, BigDecimal amount, String target, int size) {
        this.user = user;
        this.amount = amount;
        this.target = target;
        this.size = size;
    }

    /*public PayRequest(String user, BigDecimal amount, String target, int size, boolean subAPass, boolean subBPass) {
        this.user = user;
        this.target = target;
        this.amount = amount;
        this.size = size;
        this.subAPass = subAPass;
        this.subBPass = subBPass;
    }*/

    public PayRequest(String user, BigDecimal amount, String target, int size, boolean approvel,
                      boolean subAPass, boolean subAConfirmPass, boolean subARollbackPass,
                      boolean subBPass, boolean subBConfirmPass, boolean subBRollbackPass) {
        this.user = user;
        this.target = target;
        this.amount = amount;
        this.size = size;
        this.approvel = approvel;
        this.subAPass = subAPass;
        this.subBPass = subBPass;
        this.subAConfirmPass = subAConfirmPass;
        this.subARollbackPass = subARollbackPass;
        this.subBConfirmPass = subBConfirmPass;
        this.subBRollbackPass = subBRollbackPass;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSubAPass() {
        return subAPass;
    }

    public void setSubAPass(boolean subAPass) {
        this.subAPass = subAPass;
    }

    public boolean isSubBPass() {
        return subBPass;
    }

    public void setSubBPass(boolean subBPass) {
        this.subBPass = subBPass;
    }

    public boolean isApprovel() {
        return approvel;
    }

    public void setApprovel(boolean approvel) {
        this.approvel = approvel;
    }

    public boolean isSubAConfirmPass() {
        return subAConfirmPass;
    }

    public void setSubAConfirmPass(boolean subAConfirmPass) {
        this.subAConfirmPass = subAConfirmPass;
    }

    public boolean isSubARollbackPass() {
        return subARollbackPass;
    }

    public void setSubARollbackPass(boolean subARollbackPass) {
        this.subARollbackPass = subARollbackPass;
    }

    public boolean isSubBConfirmPass() {
        return subBConfirmPass;
    }

    public void setSubBConfirmPass(boolean subBConfirmPass) {
        this.subBConfirmPass = subBConfirmPass;
    }

    public boolean isSubBRollbackPass() {
        return subBRollbackPass;
    }

    public void setSubBRollbackPass(boolean subBRollbackPass) {
        this.subBRollbackPass = subBRollbackPass;
    }
}
