package com.yqkj.components.dtransaction;

import com.yqkj.components.dtransaction.bean.StackStepObject;
import com.yqkj.components.dtransaction.utils.DTransactionUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 事务序列、堆栈管理模块
 *
 */
@Component
public class DTStepSeqManager {

    /**
     * 存放事务父子关系的堆栈
     */
    private ThreadLocal<Stack<Long>> stepRelation = new ThreadLocal<>();

    /**
     * 存放子事务顺序
     */
    private ThreadLocal<Map<Long, StackStepObject>> stepSeq = new ThreadLocal<>();

    /**
     * 清空线程变量，必须要调用，否则会造成内存溢出
     */
    public void clearStepSeq() {
        if (stepSeq.get() != null) {
            stepSeq.get().clear();
        }
        stepSeq.remove();

        if (stepRelation.get() != null) {
            stepRelation.get().clear();
        }
        stepRelation.remove();
    }

    /**
     * 初始化线程变量
     */
    public void initStepSeq() {
        this.clearStepSeq();
        stepSeq.set(new HashMap<>());

        stepRelation.set(new Stack<>());
    }

    /**
     * 向堆栈中压入事务StepId，同时初始化子事务seq顺序为0
     *
     * @param stepId
     */
    public void pushStepStack(Long stepId) {
        stepRelation.get().push(stepId);
        stepSeq.get().put(stepId, new StackStepObject(stepId, DTransactionUtil.DEFAULT_STEP_SEQ));
    }

    /**
     * 取堆栈最顶端的事务id，不移除
     *
     * @return
     */
    public Long peekStepStack() {
        return stepRelation.get().empty() ? null : stepRelation.get().peek();
    }

    /**
     * 取堆栈最顶端的事务id，同时移除
     *
     * @return
     */
    public Long popStepStack() {
        return stepRelation.get().empty() ? null : stepRelation.get().pop();
    }

    /**
     * seq顺序加1
     *
     * @param stepId
     * @return
     */
    public Integer addStepOne(Long stepId) {
        StackStepObject sto = stepSeq.get().get(stepId);
        sto.setSeq(sto.getSeq() + 1);
        return sto.getSeq();
    }
}
