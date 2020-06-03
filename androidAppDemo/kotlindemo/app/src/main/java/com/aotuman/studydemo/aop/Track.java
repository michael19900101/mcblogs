package com.aotuman.studydemo.aop;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Track {

    @Pointcut("execution(* com.aotuman.studydemo.dsl.MainActivity(..))")
    public void logForActivityDestory(){}

    @Before("logForActivityDestory()")
    public void logForDestroy(JoinPoint joinPoint) {
        //对于使用Annotation的AspectJ而言，JoinPoint就不能直接在代码里得到多了，而需要通过
        //参数传递进来。
        Log.e("jbjb", "AOP 埋点：[initViewPager]" + joinPoint.toShortString());
    }

}
