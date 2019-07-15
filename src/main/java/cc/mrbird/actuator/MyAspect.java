package cc.mrbird.actuator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by FangYan on 2019/7/15.
 */
@Component
@Aspect
public class MyAspect {

    @Around("execution(* cc.mrbird.actuator.Student.t1())")
    public void t1(ProceedingJoinPoint point) throws Throwable {
        System.out.println("拦截前");
        point.proceed();
        System.out.println("拦截后");
    }
}
