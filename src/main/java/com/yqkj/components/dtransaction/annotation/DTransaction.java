package com.yqkj.components.dtransaction.annotation;

import com.yqkj.components.dtransaction.enums.RollbackStrategy;

import java.lang.annotation.*;

/**
 * 事务开启注解，应用于类上，扫描时将检查该类是否实现IDTransaction接口，若未实现则不开启子事务
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DTransaction {

    String name() default "";

    // 回滚失败的策略，根节点有效，默认为中断策略
    RollbackStrategy ROLLBACK_STRATEGY() default RollbackStrategy.Interrupt;
}
