package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.bean.IDTransactionHeader;
import com.yqkj.components.dtransaction.bean.IDTransactionResponse;

public interface IDTransation {

    /**
     * 返回在系统内唯一的名称，提供给系统调用confirm和rollback
     *
     * @return
     */
    public String getDTServiceName();

    /**
     * 主业务操作
     *
     * @param request
     * @return
     */
    public IDTransactionResponse execute(IDTransactionHeader request);

    /**
     * 提交操作
     *
     * @param request
     * @return
     */
    public IDTransactionResponse confirm(IDTransactionHeader request);

    /**
     * 回滚操作
     *
     * @param request
     * @return
     */
    public IDTransactionResponse rollback(IDTransactionHeader request);
}
