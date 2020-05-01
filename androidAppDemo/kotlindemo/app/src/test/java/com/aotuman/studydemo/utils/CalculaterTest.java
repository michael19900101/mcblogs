package com.aotuman.studydemo.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
   参考文章 Android 单元测试(一) 之JUnit基础
   https://blog.csdn.net/chaoyangsun/article/details/80095249
   https://github.com/simplezhli/AndroidUT
 */
public class CalculaterTest {

    private Calculater mCalculator;
    private int a = 1;
    private int b = 3;

    // 在测试开始之前回调的方法
    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculater();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sum() {
        int result = mCalculator.sum(a, b);
        // 第一个参数："sum(a, b)" 打印的tag信息 （可省略）
        // 第二个参数： 3 期望得到的结果
        // 第三个参数  result：实际返回的结果
        // 第四个参数  0 误差范围（可省略）
        assertEquals("sum(a, b)",4,result,0);
    }

    @Test
    public void substract() {
    }

    @Test
    public void divide() {
    }

    @Test
    public void multiply() {
    }
}