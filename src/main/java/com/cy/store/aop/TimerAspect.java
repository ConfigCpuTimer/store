package com.cy.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimerAspect {
    @Around("execution(* com.cy.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis(); // 先记录当前时间
        Object result = point.proceed(); // 执行目标方法
        long end = System.currentTimeMillis(); // 记录结束时间

        System.out.println("耗时：" + (end - start));

        return result;
    }
}
