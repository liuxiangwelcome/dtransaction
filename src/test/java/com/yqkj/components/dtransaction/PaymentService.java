package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.annotation.DTransaction;
import com.yqkj.components.dtransaction.bean.IDTransactionHeader;
import com.yqkj.components.dtransaction.bean.IDTransactionResponse;
import com.yqkj.components.dtransaction.bean.PaymentRequest;
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
 * 扣款业务
 */
@Service
@DTransaction
public class PaymentService implements IDTransation {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 扣款明细
     */
    private static Map<Long, BigDecimal> details = new HashMap<>();

    @Override
    public String getDTServiceName() {
        return "PaymentService";
    }

    /**
     * 扣款操作
     *
     * @param request
     * @return
     */
    @Override
    public IDTransactionResponse execute(IDTransactionHeader request) {
        PaymentRequest pr = (PaymentRequest) request;

        logger.debug("开始扣款操作，账户 {}， 金额 {}", pr.getUser(), pr.getAmount());

        // 判断账户是否存在
        if (!TestDataUtil.users.contains(pr.getUser())) {
            logger.error(MessageFormat.format("账户 {0} 不存在", pr.getUser()));
            throw new PaymentException(MessageFormat.format("账户 {0} 不存在", pr.getUser()));
        }

        // 判断账户余额是否充足
        BigDecimal balance = TestDataUtil.accounts.get(pr.getUser());
        if (balance.compareTo(pr.getAmount()) < 0) {
            logger.error(MessageFormat.format("账户 {0} 的余额 {1} 不足", pr.getUser(), balance));
            throw new PaymentException(MessageFormat.format("账户 {0} 的余额 {1} 不足", pr.getUser(), balance));
        }

        // 扣减账户金额
        BigDecimal newBalance = MathTool.getInstance(balance).subtract(pr.getAmount()).build();
        TestDataUtil.accounts.put(pr.getUser(), newBalance);

        // 记录扣减明细
        details.put(request.getTransactionHeader().getSessionId(), pr.getAmount());

        logger.debug("扣款操作完成， 账户 {}， 余额 {}", pr.getUser(), TestDataUtil.accounts.get(pr.getUser()));

        return null;
    }

    @Override
    public IDTransactionResponse confirm(IDTransactionHeader request) {
        return null;
    }

    @Override
    public IDTransactionResponse rollback(IDTransactionHeader request) {
        PaymentRequest pr = (PaymentRequest) request;

        logger.debug("***开始扣款回滚， 账户 {}， 回滚金额 {}", pr.getUser(), pr.getAmount());

        // 判断是否存在扣减明细，不存在则不用回滚
        BigDecimal de = details.get(request.getTransactionHeader().getSessionId());
        if (de == null) {
            return null;
        }

        // 判断回滚金额和扣减金额是否相同，不相同则抛出异常（），相同则允许回滚
        if (de.compareTo(pr.getAmount()) != 0) {
            logger.error(MessageFormat.format("***回滚数量 {} 和扣减数量 {} 不一致", pr.getAmount(), de));
            throw new PaymentException(MessageFormat.format("回滚数量 {} 和扣减数量 {} 不一致", pr.getAmount(), de));
        }

        // 回滚账户金额
        BigDecimal newBalance = MathTool.getInstance(TestDataUtil.accounts.get(pr.getUser())).add(pr.getAmount()).build();
        TestDataUtil.accounts.put(pr.getUser(), newBalance);

        // 记录扣减明细
        details.remove(request.getTransactionHeader().getSessionId());

        logger.debug("***扣款回滚完成， 账户 {}， 余额 {}", pr.getUser(), TestDataUtil.accounts.get(pr.getUser()));

        return null;
    }
}
