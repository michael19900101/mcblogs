package com.aotuman.studydemo.dagger2stu.cstudy;

import dagger.Module;
import dagger.Provides;

@Module
public class CstudyModule {
    private int money;

    public CstudyModule(int money) {
        this.money = money;
    }
    @Provides
    Soul providesSoul() {
        Soul soul = new Soul();
        soul.setMoney(this.money);
        return soul;
    }

    @Provides
    Woman providesWoman(Soul soul) {
        return new Woman(soul);
    }
}