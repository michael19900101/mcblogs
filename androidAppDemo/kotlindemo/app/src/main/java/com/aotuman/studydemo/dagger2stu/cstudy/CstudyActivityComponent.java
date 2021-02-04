package com.aotuman.studydemo.dagger2stu.cstudy;

import dagger.Component;

@Component(modules = CstudyModule.class)
public interface CstudyActivityComponent {
    void injectTo(CstudyActivity cstudyActivity);
}