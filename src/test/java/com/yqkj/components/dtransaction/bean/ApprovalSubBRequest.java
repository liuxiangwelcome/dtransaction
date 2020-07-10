package com.yqkj.components.dtransaction.bean;

public class ApprovalSubBRequest extends AbstractDTransactionRequest {
    public ApprovalSubBRequest(boolean subPass, boolean subBConfirmPass, boolean subBRollbackPass) {
        this.subPass = subPass;
        this.subBConfirmPass = subBConfirmPass;
        this.subBRollbackPass = subBRollbackPass;
    }

    private boolean subPass;

    private boolean subBConfirmPass = true;

    private boolean subBRollbackPass = true;

    public boolean isSubPass() {
        return subPass;
    }

    public void setSubPass(boolean subPass) {
        this.subPass = subPass;
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
