package com.yqkj.components.dtransaction;

import com.alibaba.fastjson.JSONObject;
import com.yqkj.components.dtransaction.bean.DTransactionHeader;
import com.yqkj.components.dtransaction.bean.IDTransactionHeader;
import com.yqkj.components.dtransaction.bean.StepTree;
import com.yqkj.components.dtransaction.builds.DTXLogsBuild;
import com.yqkj.components.dtransaction.builds.DTXMainBuild;
import com.yqkj.components.dtransaction.builds.DTXStepBuild;
import com.yqkj.components.dtransaction.builds.LogsContentBuild;
import com.yqkj.components.dtransaction.dao.DTXLogsDao;
import com.yqkj.components.dtransaction.dao.DTXMainDao;
import com.yqkj.components.dtransaction.dao.DTXStepDao;
import com.yqkj.components.dtransaction.enums.DTXLogsType;
import com.yqkj.components.dtransaction.enums.MainStatus;
import com.yqkj.components.dtransaction.enums.RollbackStrategy;
import com.yqkj.components.dtransaction.enums.StepStatus;
import com.yqkj.components.dtransaction.exceptions.DTransactionException;
import com.yqkj.components.dtransaction.pojo.DTXLogs;
import com.yqkj.components.dtransaction.pojo.DTXMain;
import com.yqkj.components.dtransaction.pojo.DTXStep;
import com.yqkj.components.dtransaction.properties.DTransactionProperties;
import com.yqkj.components.dtransaction.utils.DTransactionUtil;
import com.yqkj.components.dtransaction.utils.DateUtil;
import com.yqkj.components.dtransaction.utils.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultDTransactionManager implements IDTransactionManager {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 执行次数，默认为1
     */
    protected static final int DEFAULT_EXECUTE_TIMES = 1;

    @Autowired
    private DTransactionProperties transactionProperties;
    @Autowired
    private DTXMainDao mainDao;
    @Autowired
    private DTXStepDao stepDao;
    @Autowired
    private DTXLogsDao logsDao;
    @Autowired
    private DTransactionFactory transactionFactory;

    /**
     * 会话ID
     * 须调用clear手动回收
     */
    private ThreadLocal<Long> sessionId = new ThreadLocal<>();

    @Override
    public Long getSessionId() {
        if (sessionId.get() != null) {
            return sessionId.get();
        }
        Long sid = this.generatorSessionId();
        this.sessionId.set(sid);
        return sid;
    }

    @Override
    public boolean isExistSession() {
        return this.sessionId.get() != null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void createDTX() {
        DTXMainBuild mainBuild = DTXMainBuild.getInstance().buildSessionId(getSessionId())
                .buildTime(DateUtil.current(), DateUtil.current(), null)
                .buildExecuteTimes(DEFAULT_EXECUTE_TIMES)
                .buildStatus(MainStatus.CREATED);
        DTXMain main = mainBuild.build();
        main = this.mainDao.save(main);

        if (transactionProperties.isWriteLogs()) {
            DTXLogs logs = DTXLogsBuild.getInstance()
                    .buildCommon(main.getSessionId(), DTXLogsType.Main, main.getId(), mainBuild.getLogsContentBuild().build()).build();
            this.logsDao.save(logs);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Long createStep(Long parentStepId, Integer seq, String serviceName, Object requestContent) {
        DTXStepBuild stepBuild = DTXStepBuild.getInstance().buildSessionId(getSessionId())
                .buildParentId(parentStepId)
                .buildStepSeq(seq)
                .buildStepStatus(StepStatus.CREATED)
                .buildExecuteTimes(DEFAULT_EXECUTE_TIMES)
                .buildTime(DateUtil.current(), DateUtil.current())
                .buildServiceName(serviceName)
                .buildRequestContentClass(requestContent.getClass().getName())
                .buildRequestContent(JSONObject.toJSONString(requestContent));
        DTXStep step = stepBuild.build();
        step = this.stepDao.save(step);

        if (transactionProperties.isWriteLogs()) {
            String content = stepBuild.getLogsContentBuild().build();
            DTXLogs logs = DTXLogsBuild.getInstance().buildCommon(step.getSessionId(), DTXLogsType.Step, step.getId(), content).build();
            this.logsDao.save(logs);
        }

        return step.getId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateStepStatus(Long stepId, StepStatus status, String remark) {
        DTXStep step = this.stepDao.findById(stepId).get();

        DTXStepBuild stepBuild = DTXStepBuild.getInstance(step);
        step = stepBuild.buildStepStatus(status).buildRemark(remark).buildUpdateTime(DateUtil.current()).build();

        this.stepDao.save(step);

        if (transactionProperties.isWriteLogs()) {
            String content = stepBuild.getLogsContentBuild().build();
            DTXLogs logs = DTXLogsBuild.getInstance()
                    .buildCommon(step.getSessionId(), DTXLogsType.Step, step.getId(), content).build();
            this.logsDao.save(logs);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateMainSuccess(Long sessionId, String remark) {
        DTXMain main = this.mainDao.queryMainBySession(sessionId);

        DTXMainBuild mainBuild = DTXMainBuild.getInstance(main);
        main = mainBuild.buildStatus(MainStatus.SUCCEED).buildRemark(remark).buildUpdateTime(DateUtil.current()).build();

        this.mainDao.save(main);

        if (transactionProperties.isWriteLogs()) {
            String content = mainBuild.getLogsContentBuild().build();
            DTXLogs logs = DTXLogsBuild.getInstance()
                    .buildCommon(main.getSessionId(), DTXLogsType.Main, main.getId(), content).build();
            this.logsDao.save(logs);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateMainFailed(Long sessionId, String remark){
        DTXMain main = this.mainDao.queryMainBySession(sessionId);

        DTXMainBuild mainBuild = DTXMainBuild.getInstance(main);
        main = mainBuild.buildStatus(MainStatus.FAILED).buildRemark(remark).buildUpdateTime(DateUtil.current()).build();

        this.mainDao.save(main);

        if (transactionProperties.isWriteLogs()) {
            String content = mainBuild.getLogsContentBuild().build();
            DTXLogs logs = DTXLogsBuild.getInstance()
                    .buildCommon(main.getSessionId(), DTXLogsType.Main, main.getId(), content).build();
            this.logsDao.save(logs);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateMain(DTXMain main) {
        this.mainDao.save(main);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStep(DTXStep step) {
        this.stepDao.save(step);
    }

    @Override
    public void confirmAll() {

        // 加载事务明细
        List<DTXStep> steps = stepDao.queryStepBySession(this.getSessionId());

        // 构造事务树
        StepTree root = this.constructStepTree(steps);

        // 按树形结构，从叶子开始提交
        boolean result = false;
        try {
            this.submitConfirm(root);
            result = true;
        } catch (Throwable e) {
            throw e;
        } finally {
            // 更新全局事务状态
            DTXMain main = this.mainDao.queryMainBySession(this.getSessionId());
            DTXMainBuild mainBuild = DTXMainBuild.getInstance(main)
                    .buildConfirmTimes(main.getConfirmTimes() + 1)
                    .buildUpdateTime(DateUtil.current())
                    .buildStatus(result ? MainStatus.CONFIRMDONE : MainStatus.CONFIRMFAILED);
            main = mainBuild.build();
            this.updateMain(main);

            if (transactionProperties.isWriteLogs()) {
                String content = mainBuild.getLogsContentBuild().build();
                DTXLogs logs = DTXLogsBuild.getInstance().buildCommon(main.getSessionId(), DTXLogsType.Main, main.getId(), content).build();
                this.logsDao.save(logs);
            }
        }
    }

    /**
     * 递归提交事务，从叶子开始提交，所有叶子提交完成后，再提交主干
     *
     * @param st
     * @return
     */
    protected void submitConfirm(StepTree st) {
        if (st.getChilds().isEmpty()){
            this.confirm(st.getParent());
        } else {
            // 提交所有叶子，一旦失败出现异常，则直接抛出返回
            for (StepTree child : st.getChilds()) {
                this.submitConfirm(child);
            }
            this.confirm(st.getParent());
        }
    }

    /**
     * 提交事务
     * 1.ID为0L时为根节点（虚拟节点），不用提交；
     * 2.判断判断，状态为“成功”“提交失败”，可以发起confirm操作；
     * 3.提交后更新状态
     *
     * @param step
     * @return
     */
    protected void confirm(DTXStep step){
        /**
         * 虚拟根节点不参与
         */
        if (step.getId().equals(DTransactionUtil.DEFAULT_PARENT_STEP_ID)) {
            return;
        }

        logger.debug("confirm stepId {} status {}", step.getId(), step.getStatus());

        // 根据状态判断，不符合需要提交的事务返回
        if (!isConfirm(step.getStatus())) {
            return;
        }

        // 提交（含重试）
        int retry = 0;
        boolean success = false;
        String remark = null;
        Throwable throwable = null;
        while (retry < transactionProperties.getStepRetrySize()) {
            retry++;
            try {
                logger.debug("begin execute confirm {} times {} stepId {}", retry, step.getServiceName(), step.getId());
                IDTransation transation = transactionFactory.getDTransaction(step.getServiceName());
                IDTransactionHeader dth = (IDTransactionHeader) JSONObject.parseObject(step.getRequestContent(), Class.forName(step.getRequestContentClass()));
                DTransactionHeader header = this.generatorHeader(step);
                dth.setTransactionHeader(header);
                transation.confirm(dth);
                logger.debug("end execute confirm {} times {} stepId {}", retry, step.getServiceName(), step.getId());
                success = true;
                break;
            } catch (ClassNotFoundException e) {
                throwable = e;
                logger.error(MessageFormat.format("can not create class {0} stepId {1}", step.getRequestContentClass(), step.getId()), e);
                remark = MessageFormat.format("code {0}, reflex error", DTransactionUtil.ERROR_CODE_REFLEX);
                break;
            } catch (Throwable e) {
                throwable = e;
                logger.error(MessageFormat.format("execute confirm error {0} stepId {1}", step.getServiceName(), step.getId()));
                if (retry >= transactionProperties.getStepRetrySize()) {
                    break;
                }
                try {
                    Thread.sleep(transactionProperties.getStepRetrySleepTime());
                } catch (InterruptedException ex) {
                    logger.error("thread sleep error", ex);
                }
            }
        }

        DTXStepBuild stepBuild = DTXStepBuild.getInstance(step)
                .buildConfirmTimes(step.getConfirmTimes() + retry)
                .buildConfirmTime(DateUtil.current())
                .buildUpdateTime(DateUtil.current())
                .buildStepStatus(success ? StepStatus.CONFIRMDONE : StepStatus.CONFIRMFAILED)
                .buildRemark(remark);
        this.updateStep(stepBuild.build());

        if (this.transactionProperties.isWriteLogs()) {
            this.logsDao.save(DTXLogsBuild.getInstance().buildCommon(step.getSessionId(), DTXLogsType.Step, step.getId(), stepBuild.getLogsContentBuild().build()).build());
        }

        // 提交不成功，抛出异常，由拦截器获取并调用回滚操作，最终异常返回给调用者
        if (!success) {
            throw new DTransactionException(DTransactionUtil.ERROR_CODE_CONFIRM, MessageFormat.format("execute confirm error {0} stepId {1}", step.getServiceName(), step.getId()), throwable);
        }
    }

    /**
     * 控制提交
     * 主程序是“成功”“提交失败”，可以发起confirm操作
     *
     * @param status
     * @return
     */
    private boolean isConfirm(StepStatus status){
        return this.transactionProperties.getAllowConfirmStepStatus().contains(status);
    }

    @Override
    public void rollbackAll(RollbackStrategy rollbackStrategy) {

        boolean isAllSuccess = true;

        List<Throwable> throwables = new ArrayList<>();

        try {
            // 加载事务明细
            List<DTXStep> steps = stepDao.queryStepBySession(this.getSessionId());

            // 构造事务树
            StepTree root = this.constructStepTree(steps);

            // 按树形结构，从叶子开始回滚
            isAllSuccess = this.submitRollback(root, throwables, rollbackStrategy);

            // 将回滚的最后一条异常抛出（回滚策略为TryBest时可能会产生多条异常记录）
            if (!isAllSuccess) {
                throw new DTransactionException(DTransactionUtil.ERROR_CODE_ROLLBACK, "execute rollback error", throwables.isEmpty() ? null : throwables.get(0));
            }

        } catch (Throwable e){
            throw e;
        } finally {
            // 更新全局事务状态
            DTXMain main = this.mainDao.queryMainBySession(this.getSessionId());
            DTXMainBuild mainBuild = DTXMainBuild.getInstance(main)
                    .buildRollbackTimes(main.getRollbackTimes() + 1)
                    .buildUpdateTime(DateUtil.current())
                    .buildStatus(isAllSuccess ? MainStatus.ROLLBACKDONE : MainStatus.ROLLBACKFAILED);
            main = mainBuild.build();
            this.updateMain(main);

            if (this.transactionProperties.isWriteLogs()) {
                this.logsDao.save(DTXLogsBuild.getInstance().buildCommon(main.getSessionId(), DTXLogsType.Main, main.getId(), mainBuild.getLogsContentBuild().build()).build());
            }
        }
    }

    /**
     * 递归提交回滚，从叶子开始回滚，所有叶子回滚完成后，再回滚主干
     *
     * @param st
     * @return
     */
    public boolean submitRollback(StepTree st, List<Throwable> throwables, RollbackStrategy rollbackStrategy){
        if (st.getChilds().isEmpty()){
            return this.rollback(st.getParent(), throwables);
        } else {
            boolean result = true;
            for (StepTree child : st.getChilds()) {
                boolean success = this.submitRollback(child, throwables, rollbackStrategy);

                result = result && success;

                // 中断策略控制，则返回，不继续回滚
                if (!success && rollbackStrategy.equals(RollbackStrategy.Interrupt)) {
                    return success;
                }
            }
            return this.rollback(st.getParent(), throwables);
        }
    }

    /**
     * 提交回滚
     * 1.ID为0L时为根节点（虚拟节点），不用回滚；
     * 2.判断判断，状态为“成功”“提交成功”“提交失败”“回滚失败”，可以发起rollback操作；
     * 3.回滚后更新状态
     *
     * @param step
     * @return
     */
    private boolean rollback(DTXStep step, List<Throwable> throwables){
        /**
         * 虚拟根节点不参与
         */
        if (step.getId().equals(DTransactionUtil.DEFAULT_PARENT_STEP_ID)) {
            return true;
        }

        logger.debug("rollback stepId {} status {}", step.getId(), step.getStatus());

        // 根据状态判断，不符合需要回滚的事务，返回true
        if (!isRollback(step.getStatus())) {
            return true;
        }

        // 回滚（含重试）
        int retry = 0;
        boolean success = false;
        String remark = null;
        Throwable throwable = null;
        while (retry < transactionProperties.getStepRetrySize()) {
            retry++;
            try {
                logger.debug("begin execute rollback {} times {}", retry, step.getServiceName());
                IDTransation transation = transactionFactory.getDTransaction(step.getServiceName());
                IDTransactionHeader dth = (IDTransactionHeader) JSONObject.parseObject(step.getRequestContent(), Class.forName(step.getRequestContentClass()));
                DTransactionHeader header = this.generatorHeader(step);
                dth.setTransactionHeader(header);
                transation.rollback(dth);
                logger.debug("end execute rollback {} times {}", retry, step.getServiceName());
                success = true;
                break;
            } catch (ClassNotFoundException e) {
                remark = MessageFormat.format("code {0}, reflex error", DTransactionUtil.ERROR_CODE_REFLEX);
                logger.error(MessageFormat.format("can not create class {0}", step.getRequestContentClass()), e);
                break;
            } catch (Throwable e) {
                throwable = e;
                logger.error(MessageFormat.format("execute rollback error {0}{1}", retry, step.getServiceName()), e);
                if (retry >= transactionProperties.getStepRetrySize()) {
                    break;
                }
                try {
                    Thread.sleep(transactionProperties.getStepRetrySleepTime());
                } catch (InterruptedException ex) {
                    logger.error("thread sleep error", ex);
                }
            }
        }

        // 将异常加入异常集合中
        if (!success) {
            throwables.add(throwable);
        }

        DTXStepBuild stepBuild = DTXStepBuild.getInstance(step)
                .buildRollbackTimes(step.getRollbackTimes() + retry)
                .buildRollbackTime(DateUtil.current())
                .buildUpdateTime(DateUtil.current())
                .buildStepStatus(success ? StepStatus.ROLLBACKDONE : StepStatus.ROLLEBACKFAILED)
                .buildRemark(remark);
        this.updateStep(stepBuild.build());

        if (this.transactionProperties.isWriteLogs()) {
            this.logsDao.save(DTXLogsBuild.getInstance().buildCommon(step.getSessionId(), DTXLogsType.Step, step.getId(), stepBuild.getLogsContentBuild().build()).build());
        }

        return success;
    }

    /**
     * 控制回滚
     * 主程序是“成功”“提交成功”“提交失败”“回滚失败”，可发起回滚操作
     *
     * @param status
     * @return
     */
    private boolean isRollback(StepStatus status) {
        return this.transactionProperties.getAllowRollbackStepStatus().contains(status);
    }

    /**
     * 生成sessionId，格式时间毫秒+4位随机数
     *
     * @return
     */
    private Long generatorSessionId() {
        String time = String.valueOf(System.currentTimeMillis());
        String random = MathUtil.generateRandom(4);
        return Long.valueOf(time + random);
    }

    /**
     * 根据子事务集合构造树型事务数据结构
     * @param steps
     * @return
     */
    private StepTree constructStepTree(List<DTXStep> steps) {
        List<StepTree> list = new ArrayList<>();

        DTXStep rt = DTXStepBuild.getInstance().buildId(DTransactionUtil.DEFAULT_PARENT_STEP_ID).build();
        StepTree root = new StepTree(rt);

        Map<Long, List<DTXStep>> temp = new HashMap<>();

        for (DTXStep step : steps) {
            Long parent = step.getParentId();
            if (temp.get(parent) == null) {
                temp.put(parent, new ArrayList<>());
            }
            temp.get(parent).add(step);
        }

        this.constructStepTree(root, temp);

        return root;
    }

    private void constructStepTree(StepTree st, Map<Long, List<DTXStep>> temp) {
        List<DTXStep> childs = temp.get(st.getParent().getId());
        if (childs == null || childs.isEmpty()) {
            return;
        }
        for (DTXStep step : childs) {
            StepTree child = new StepTree(step);
            st.getChilds().add(child);
            this.constructStepTree(child, temp);
        }
    }

    private DTransactionHeader generatorHeader(DTXStep step){
        DTransactionHeader header = new DTransactionHeader();
        header.setSessionId(this.getSessionId());
        header.setStepId(step.getId());
        return header;
    }

    @Override
    public void clear() {
        this.sessionId.remove();
    }
}
