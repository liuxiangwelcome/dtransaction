package com.yqkj.components.dtransaction.bean;

public class ApprovalRequest extends AbstractDTransactionRequest {
    public ApprovalRequest(boolean subAPass, boolean subAConfirmPass, boolean subARollbackPass, boolean subBPass, boolean subBConfirmPass, boolean subBRollbackPass) {
        this.subAPass = subAPass;
        this.subBPass = subBPass;
        this.subAConfirmPass = subAConfirmPass;
        this.subARollbackPass = subARollbackPass;
        this.subBConfirmPass = subBConfirmPass;
        this.subBRollbackPass = subBRollbackPass;
    }

    /**
     * 子审核A是否通过
     */
    private boolean subAPass;

    /**
     * 子审核A是否提交通过
     */
    private boolean subAConfirmPass = true;

    /**
     * 子审核A是否回滚通过
     */
    private boolean subARollbackPass = true;

    /**
     * 子审核B是否通过
     */
    private boolean subBPass;

    /**
     * 子审核B是否提交通过
     */
    private boolean subBConfirmPass = true;

    /**
     * 子审核B是否回滚通过
     */
    private boolean subBRollbackPass = true;

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
