package com.aotuman.studydemo.dagger2stu.astudy;

import dagger.Component;

@Component
public interface AstudyActivityComponent {
    void injectTo(AstudyActivity astudyActivity);
}