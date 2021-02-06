package com.aotuman.studydemo.jetpack.company;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormPageActivity extends AppCompatActivity {

    private FormPage formPage;

    private PageViewModel pageViewModel;

    // 页面所有动态生成的View
    private Map<String/**pagecode*/, View> pageViewMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        pageViewModel = viewModelProvider.get(PageViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 1.加载表单
        loadPage();
        // 2.渲染View
        renderView();
        // 3.view观察LiveData变化
        observeLivaData();
    }

    // 1.加载表单
    private void loadPage() {
        formPage = new FormPage();

        // 解析表单ViewBean属性, 设置PageVM所有viewmodels
        List<XWViewModel> allVMs = addTestVM();
        pageViewModel.setViewModels(allVMs);
    }

    // 2.渲染View
    private void renderView() {
        for (XWViewModel viewModel : pageViewModel.getViewModels()) {
            // 利用工厂模式，根据type动态生成控件View
            View view = ViewFactory.getView(this, viewModel.getType());
            pageViewMap.put(viewModel.getCode(), view);
        }
    }

    // 3.view观察LiveData变化
    // todo 每种控件都有自己的数据类型加载
    private void observeLivaData() {
        for (XWViewModel viewModel : pageViewModel.getViewModels()) {
            // viewModel里不止一种数据要被观察
            for (MutableLiveData<Object> liveData: viewModel.getLiveDataList()) {
                liveData.observe(this,
                        value -> {
                            View view = pageViewMap.get(viewModel.getCode());
                            // todo view update value
                        }
                );
            }
        }
    }

    // 测试数据
    private List<XWViewModel> addTestVM(){
        List<XWViewModel> allVMs = new ArrayList<>();
        TextViewModel textViewModel1 = new TextViewModel();
        textViewModel1.setCode("tv-001");
        textViewModel1.setText("测试文字001");
        TextViewModel textViewModel2 = new TextViewModel();
        textViewModel1.setCode("tv-002");
        textViewModel1.setText("测试文字002");
        allVMs.add(textViewModel1);
        allVMs.add(textViewModel2);
        return allVMs;
    }
}
