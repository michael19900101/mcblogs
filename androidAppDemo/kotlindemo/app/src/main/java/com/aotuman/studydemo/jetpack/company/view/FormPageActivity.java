package com.aotuman.studydemo.jetpack.company.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.aotuman.studydemo.R;
import com.aotuman.studydemo.jetpack.company.FormPage;
import com.aotuman.studydemo.jetpack.company.SimpleValueProtocol;
import com.aotuman.studydemo.jetpack.company.ViewFactory;
import com.aotuman.studydemo.jetpack.company.viewmodel.RootPageViewModel;
import com.aotuman.studydemo.jetpack.company.viewmodel.FormViewModel;
import com.aotuman.studydemo.jetpack.company.viewmodel.FormTextViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FormPageActivity extends AppCompatActivity {

    private FormPage formPage;

    private RootPageViewModel rootPageViewModel;

    // 页面所有动态生成的View
    private Map<String/**ctrlcode*/, View> pageViewMap = new HashMap<>();

    // 页面上所有自定义的ViewModel
    private List<FormViewModel> allVMs;

    private LinearLayout formPageLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formpage);
        formPageLayout = findViewById(R.id.formPageLayout);
        initPageViewModel();

        // 1.加载表单
        loadPage();
        // 2.渲染View
        renderView();
        // 3.view观察LiveData（可感知生命周期的组件）变化
        observeLiveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initPageViewModel() {
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        rootPageViewModel = viewModelProvider.get(RootPageViewModel.class);
    }

    // 1.加载表单
    private void loadPage() {
        formPage = new FormPage();

        // 解析表单ViewBean属性, 设置PageVM所有viewmodels
        allVMs = testAddViewModel();
        rootPageViewModel.setViewModels(allVMs);
    }

    // 2.渲染View
    private void renderView() {
        for (FormViewModel viewModel : rootPageViewModel.getViewModels()) {
            // 利用工厂模式，根据type动态生成控件View
            View view = ViewFactory.getView(this, viewModel.getType());
            pageViewMap.put(viewModel.getCode(), view);

            // 添加view到父布局
            formPageLayout.addView(view);
        }
    }

    // 3.view观察LiveData变化
    private void observeLiveData() {
        for (FormViewModel viewModel : rootPageViewModel.getViewModels()) {
            // viewModel里不止一种LiveData数据要被观察
            // 当调用 Activity 的 onDestroy() 方法时，LiveData 还会自动移除观察者。
            for (MutableLiveData<Object> liveData: viewModel.getLiveDataList()) {
                liveData.observe(this,
                        // update UI
                        value -> {
                            View view = pageViewMap.get(viewModel.getCode());
                            if (view instanceof FormViewBehavior) {
                                ((FormViewBehavior) view).refresh(value);
                            }
                        }
                );
            }
        }
    }

    // 测试添加ViewModel
    private List<FormViewModel> testAddViewModel(){
        List<FormViewModel> allVMs = new ArrayList<>();
        FormTextViewModel textViewModel1 = new FormTextViewModel();
        textViewModel1.setType("text");
        textViewModel1.setCode("tv-001");
        textViewModel1.setText("测试文字001");
        FormTextViewModel textViewModel2 = new FormTextViewModel();
        textViewModel2.setType("text");
        textViewModel2.setCode("tv-002");
        textViewModel2.setText("测试文字002");
        allVMs.add(textViewModel1);
        allVMs.add(textViewModel2);
        return allVMs;
    }

    private void testUpdateVMData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(20);

                    for (FormViewModel viewModel: allVMs) {
                        if (viewModel instanceof SimpleValueProtocol) {
                            ((SimpleValueProtocol) viewModel).updateValue("20秒之后，动态赋值:"+ new Random().nextInt());
                        }
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
