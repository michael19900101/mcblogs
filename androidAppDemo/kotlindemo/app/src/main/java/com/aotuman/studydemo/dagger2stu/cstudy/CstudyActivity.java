package com.aotuman.studydemo.dagger2stu.cstudy;

import android.widget.Toast;

import com.aotuman.studydemo.R;
import com.aotuman.studydemo.dagger2stu.base.BaseActivity;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;


/*
 * 3.复杂Module的使用(接下来少写代码，全在文中展示)
 * */
//注意：在Module的构造函数带有参数且参数被使用的情况下，所生产的Component类就没有create()方法了。
//在这里的module如果没有providesSoul()方法的话，还有一种情况只要在Soul的构造方法有@Inject也是可行的。
//你可以注释CstudyModule里的providesSoul()，然后给Soul的构造方法加上@Inject，运行项目试试

public class CstudyActivity extends BaseActivity {
    @Inject
    Woman woman;

    @Override
    public String getActivityTitle() {
        return getString(R.string.dagger2_c);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_astudy;
    }

    @Override
    protected void processLogic() {
        DaggerCstudyActivityComponent.builder().cstudyModule(new CstudyModule(100)).build().injectTo(this);

        Toast.makeText(this, woman.getSoul().getMoney()+ "", Toast.LENGTH_SHORT).show();
    }
}
