package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.annotation.DTransaction;
import com.yqkj.components.dtransaction.bean.IDTransactionHeader;
import com.yqkj.components.dtransaction.bean.IDTransactionResponse;
import com.yqkj.components.dtransaction.bean.StockRequest;
import com.yqkj.components.dtransaction.exceptions.StockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@DTransaction
public class StockService implements IDTransation{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 记录库存明细（回滚时根据该明细进行核账）
     */
    public static Map<Long, Integer> details = new HashMap<>();

    @Override
    public String getDTServiceName() {
        return "StockService";
    }

    @Override
    public IDTransactionResponse execute(IDTransactionHeader request) {

        StockRequest req = (StockRequest)request;

        logger.debug("开始扣减库存，数量 {}", req.getSize());

        if (req.getSize() > TestDataUtil.STOCK_SIZE){
            throw new RuntimeException("库存不足");
        }

        TestDataUtil.STOCK_SIZE = TestDataUtil.STOCK_SIZE - req.getSize();

        details.put(request.getTransactionHeader().getSessionId(), req.getSize());

        logger.debug("扣减库存成功，库存还剩 {}", TestDataUtil.STOCK_SIZE);

        return null;
    }

    @Override
    public IDTransactionResponse confirm(IDTransactionHeader request) {
        return null;
    }

    @Override
    public IDTransactionResponse rollback(IDTransactionHeader request) {
        StockRequest req = (StockRequest)request;
        logger.debug("***开始回滚库存，数量 {}, sessionId {}", req.getSize(), request.getTransactionHeader().getSessionId());

        // 判断是否存在扣减明细，不存在则不用回滚
        if (details.get(request.getTransactionHeader().getSessionId()) == null) {
            return null;
        }

        // 判断回滚数量和扣减数量是否相同，不相同则抛出异常（），相同则允许回滚
        if (req.getSize() != details.get(request.getTransactionHeader().getSessionId())){
            logger.error("***回滚数量 {} 和扣减数量 {} 不一致", details.get(request.getTransactionHeader().getSessionId()), req.getSize());
            throw new StockException("回滚数量和扣减数量不一致");
        }

        // 回滚库存
        TestDataUtil.STOCK_SIZE+=details.get(request.getTransactionHeader().getSessionId());

        // 移除库存明细
        details.remove(request.getTransactionHeader().getSessionId());

        logger.debug("***回滚库存完成，库存量 {} ", TestDataUtil.STOCK_SIZE);

        return null;
    }
}
