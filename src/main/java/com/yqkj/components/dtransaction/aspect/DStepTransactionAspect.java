package com.yqkj.components.dtransaction.aspect;

import com.yqkj.components.dtransaction.IDTransation;
import com.yqkj.components.dtransaction.annotation.DTransaction;
import com.yqkj.components.dtransaction.bean.DTransactionHeader;
import com.yqkj.components.dtransaction.bean.IDTransactionHeader;
import com.yqkj.components.dtransaction.enums.RollbackStrategy;
import com.yqkj.components.dtransaction.enums.StepStatus;
import com.yqkj.components.dtransaction.utils.DTransactionUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
public class DStepTransactionAspect extends DTransactionGlobalAspect {

    /**
     * 拦截具有DTransaction注解的方法
     * TODO 定义入参和出参规格
     */
    @Pointcut("@target(com.yqkj.components.dtransaction.annotation.DTransaction) && this(com.yqkj.components.dtransaction.IDTransation) && execution(public * *.execute(..))")
    public void executeTransaction() {
    }

    /**
     * 事务拦截器，执行前置拦截，后置拦截，执行主业务代码，异常拦截；
     *
     * 前置，后置拦截，执行业务代码若出现异常，将会抛出，由doRollback方法（@AfterThrowing注解标注）监听，并触发回滚操作
     *
     * 若都执行成功，则在根节点会触发统一提交操作；
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("executeTransaction()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable{

        Long stepId = null;
        Object result = null;

        try {
            // 前置拦截
            stepId = this.doBefore(point);

            // 执行业务主体代码
            result = this.doProcess(point);

            // 后置拦截
            this.doAfterReturning(stepId, point, result);

        } catch (Throwable e){
            // 主业务异常拦截
            if (stepId != null) {
                this.doThrows(stepId, point);
            }
            throw e;
        } finally {
            // 将stepId从栈中取出，若相同则移出
            Long currentStepId = stepSeqManager.peekStepStack();
            if (stepId != null && currentStepId != null && stepId.equals(currentStepId)) {
                stepSeqManager.popStepStack();
            }
        }

        // 执行“提交”操作，当所有事务处理都完成时，即堆栈头部只剩下StepID=0L时，开始调用confirmAll提交
        if (DTransactionUtil.DEFAULT_PARENT_STEP_ID.equals(stepSeqManager.peekStepStack())) {
            this.confirmDTX();
        }

        return result;
    }

    /**
     * 只有在根节点才会触发回滚
     *
     * @param joinPoint
     */
    @AfterThrowing("executeTransaction()")
    public void doRollback(JoinPoint joinPoint) {
        // 执行“回滚”操作，当所有异常事务处理都完成时，即堆栈头部只剩下StepID=0L时，开始调用rollbackAll回滚
        if (DTransactionUtil.DEFAULT_PARENT_STEP_ID.equals(stepSeqManager.peekStepStack())) {
            RollbackStrategy rollbackStrategy = joinPoint.getTarget().getClass().getAnnotation(DTransaction.class).ROLLBACK_STRATEGY();
            logger.debug("rollback strategy {}", rollbackStrategy);
            this.rollbackDTX(rollbackStrategy);
        }
    }

    /**
     * 前置拦截
     *
     * @param joinPoint
     */
    protected Long doBefore(JoinPoint joinPoint) {

        // 初始化全局事务
        this.initDTX();

        logger.debug("DTX Step aspect doBefore , sessionId {}|{}", this.getDTService(joinPoint).getDTServiceName(), dtManager.getSessionId());

        // 检查参数
        this.checkParam(joinPoint);

        // 从线程变量中取父事务ID和seq顺序
        Long parentStepId = stepSeqManager.peekStepStack();
        Integer seq = stepSeqManager.addStepOne(parentStepId);

        // 创建子事务
        Long sid = dtManager.createStep(parentStepId, seq, this.getDTService(joinPoint).getDTServiceName(), joinPoint.getArgs()[0]);

        logger.debug("DTX create step id {}|{}", this.getDTService(joinPoint).getDTServiceName(), sid);

        // 创建完成将当前事务stepId压入堆栈
        stepSeqManager.pushStepStack(sid);

        // 设置对象的header属性，将事务信息暴露给业务系统
        this.generatorAndSetHeader(sid, joinPoint);

        return sid;
    }

    /**
     * 执行主业务代码
     *
     * @param point
     * @return
     * @throws Throwable
     */
    protected Object doProcess(ProceedingJoinPoint point) throws Throwable {
        logger.debug("DTX Step aspect doProcess before, service {} sessionId {} stepId {}",
                this.getDTService(point).getDTServiceName(), dtManager.getSessionId(), stepSeqManager.peekStepStack());

        Object[] args = point.getArgs();
        Object result = point.proceed(args);

        logger.debug("DTX Step aspect doProcess after, service {} sessionId {} stepId {}",
                this.getDTService(point).getDTServiceName(), dtManager.getSessionId(), stepSeqManager.peekStepStack());

        return result;
    }

    /**
     * 后置拦截
     *
     * @param joinPoint
     * @param result
     */
    protected void doAfterReturning(Long stepId, JoinPoint joinPoint, Object result) {
        // 更新事务状态
        logger.debug("DTX doAfterReturning step id {}|{}", this.getDTService(joinPoint).getDTServiceName(), stepId);
        dtManager.updateStepStatus(stepId, StepStatus.SUCCEED, null);
    }

    /**
     * 异常拦截
     *
     * @param joinPoint
     */
    protected void doThrows(Long stepId, JoinPoint joinPoint) {
        // 更新事务状态
        logger.debug("DTX doThrows step id {}|{}", this.getDTService(joinPoint).getDTServiceName(), stepId);
        dtManager.updateStepStatus(stepId, StepStatus.FAILED, null);
    }

    protected IDTransation getDTService(JoinPoint joinPoint) {
        IDTransation target = (IDTransation) joinPoint.getTarget();
        return target;
    }

    protected DTransactionHeader getHeader(JoinPoint joinPoint) {
        IDTransactionHeader header = (IDTransactionHeader) joinPoint.getArgs()[0];
        return header.getTransactionHeader();
    }

    protected void generatorAndSetHeader(Long stepId, JoinPoint joinPoint) {
        DTransactionHeader header = new DTransactionHeader();
        header.setSessionId(dtManager.getSessionId());
        header.setStepId(stepId);

        IDTransactionHeader headerInter = (IDTransactionHeader) joinPoint.getArgs()[0];
        headerInter.setTransactionHeader(header);
    }
}
