package com.yqkj.components.dtransaction.bean;

public class ApprovalSubARequest extends AbstractDTransactionRequest {
    public ApprovalSubARequest(boolean subPass, boolean subAConfirmPass, boolean subARollbackPass) {
        this.subPass = subPass;
        this.subAConfirmPass = subAConfirmPass;
        this.subARollbackPass = subARollbackPass;
    }

    private boolean subPass;

    private boolean subAConfirmPass = true;

    private boolean subARollbackPass = true;

    public boolean isSubPass() {
        return subPass;
    }

    public void setSubPass(boolean subPass) {
        this.subPass = subPass;
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
}
