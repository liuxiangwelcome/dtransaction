package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.bean.PayRequest;
import com.yqkj.components.dtransaction.bean.PayResponse;
import com.yqkj.components.dtransaction.exceptions.ApprovalException;
import com.yqkj.components.dtransaction.exceptions.DTransactionException;
import com.yqkj.components.dtransaction.exceptions.IncomeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
public class TestDTransaction {

    @Autowired
    private PayBiz payBiz;

    @Test
    public void test001(){
        PayRequest pr = new PayRequest("zhangsan", BigDecimal.TEN, "lisi", 10);
        PayResponse resp = (PayResponse) payBiz.execute(pr);
    }

    /**
     * 模拟收款账户不存在
     */
    @Test(expected = IncomeException.class)
    public void test002(){
        PayRequest pr = new PayRequest("zhangsan", BigDecimal.TEN, "wugong", 10);
        PayResponse resp = (PayResponse) payBiz.execute(pr);
    }

    /**
     * 模拟嵌套事务，审核B不通过，回滚成功
     */
    @Test(expected = ApprovalException.class)
    public void test003(){
        PayRequest pr = new PayRequest("zhangsan", BigDecimal.TEN, "lisi", 10, true,
                true, true, true,
                false, true, true);
        PayResponse resp = (PayResponse) payBiz.execute(pr);
    }

    /**
     * 模拟嵌套事务，审核B提交失败，回滚成功
     */
    @Test(expected = DTransactionException.class)
    public void test004(){
        try {
            PayRequest pr = new PayRequest("zhangsan", BigDecimal.TEN, "lisi", 10, true,
                    true, true, true,
                    true, false, true);
            PayResponse resp = (PayResponse) payBiz.execute(pr);
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 模拟嵌套事务，审核A提交失败，回滚A失败，模拟中断回滚（即A回滚失败就停止，B不产于回滚）
     */
    @Test(expected = DTransactionException.class)
    public void test005(){
        try {
            PayRequest pr = new PayRequest("zhangsan", BigDecimal.TEN, "lisi", 10, true,
                    true, false, false,
                    true, true, true);
            PayResponse resp = (PayResponse) payBiz.execute(pr);
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 模拟嵌套事务，审核A提交失败，回滚A失败，模拟TryBest回滚（即A回滚失败，继续回滚B）
     *
     * 测试该案例时须手动修改，PayBiz的@DTransaction注解为RollbackStrategy.TryBest
     */
    @Test(expected = DTransactionException.class)
    public void test006(){
        try {
            PayRequest pr = new PayRequest("zhangsan", BigDecimal.TEN, "lisi", 10, true,
                    true, false, false,
                    true, true, true);
            PayResponse resp = (PayResponse) payBiz.execute(pr);
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
}
