package com.aotuman.studydemo.dagger2stu.bstudy;

import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;

@Component(modules = BstudyActivityModule.class)
public interface BstudyActivityComponent {
    void injectTo(BstudyActivity bstudyActivity);
}