package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.annotation.DTransaction;
import com.yqkj.components.dtransaction.bean.IDTransactionHeader;
import com.yqkj.components.dtransaction.bean.IDTransactionResponse;
import com.yqkj.components.dtransaction.bean.IncomeRequest;
import com.yqkj.components.dtransaction.bean.PaymentRequest;
import com.yqkj.components.dtransaction.exceptions.IncomeException;
import com.yqkj.components.dtransaction.exceptions.PaymentException;
import com.yqkj.components.dtransaction.utils.MathTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 入账业务
 *
 */
@Service
@DTransaction
public class IncomeService implements IDTransation{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 入账明细
     */
    private static Map<Long, BigDecimal> details = new HashMap<>();

    @Override
    public String getDTServiceName() {
        return "IncomeService";
    }

    /**
     * 入账操作
     *
     * @param request
     * @return
     */
    @Override
    public IDTransactionResponse execute(IDTransactionHeader request) {
        IncomeRequest ir = (IncomeRequest)request;

        logger.debug("开始入账操作，账户 {}， 金额 {}", ir.getUser(), ir.getAmount());

        // 判断账户是否存在
        if (!TestDataUtil.users.contains(ir.getUser())) {
            logger.error(MessageFormat.format("账户 {0} 不存在", ir.getUser()));
            throw new IncomeException(MessageFormat.format("账户 {0} 不存在", ir.getUser()));
        }

        // 增加账户金额
        BigDecimal balance = TestDataUtil.accounts.get(ir.getUser());
        BigDecimal newBalance = MathTool.getInstance(balance).add(ir.getAmount()).build();
        TestDataUtil.accounts.put(ir.getUser(), newBalance);

        // 记录扣减明细
        details.put(request.getTransactionHeader().getSessionId(), ir.getAmount());

        logger.debug("入账操作完成， 账户 {}， 余额 {}", ir.getUser(), TestDataUtil.accounts.get(ir.getUser()));

        return null;
    }

    @Override
    public IDTransactionResponse confirm(IDTransactionHeader request) {
        return null;
    }

    @Override
    public IDTransactionResponse rollback(IDTransactionHeader request) {
        IncomeRequest ir = (IncomeRequest) request;

        logger.debug("***开始入账回滚， 账户 {}， 回滚金额 {}", ir.getUser(), ir.getAmount());

        // 判断是否存在扣减明细，不存在则不用回滚
        BigDecimal de = details.get(request.getTransactionHeader().getSessionId());
        if (de == null) {
            return null;
        }

        // 判断回滚金额和扣减金额是否相同，不相同则抛出异常（），相同则允许回滚
        if (de.compareTo(ir.getAmount()) != 0) {
            logger.error(MessageFormat.format("***回滚数量 {} 和扣减数量 {} 不一致", ir.getAmount(), de));
            throw new IncomeException(MessageFormat.format("回滚数量 {} 和扣减数量 {} 不一致", ir.getAmount(), de));
        }

        // 回滚账户金额
        BigDecimal newBalance = MathTool.getInstance(TestDataUtil.accounts.get(ir.getUser())).subtract(ir.getAmount()).build();
        TestDataUtil.accounts.put(ir.getUser(), newBalance);

        // 移除扣减明细
        details.remove(request.getTransactionHeader().getSessionId());

        logger.debug("***入账回滚完成， 账户 {}， 余额 {}", ir.getUser(), TestDataUtil.accounts.get(ir.getUser()));

        return null;
    }
}
