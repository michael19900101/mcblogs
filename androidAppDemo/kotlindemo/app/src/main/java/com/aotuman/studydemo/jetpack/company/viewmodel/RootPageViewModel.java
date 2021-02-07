package com.aotuman.studydemo.jetpack.company.viewmodel;

import androidx.lifecycle.ViewModel;

import com.aotuman.studydemo.jetpack.company.viewmodel.FormViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 一个页面只有一个JetPack的ViewModel
public class RootPageViewModel extends ViewModel {

    private List<FormViewModel> viewModels = new ArrayList<>();

    private Map<String/**ctrlcode*/, FormViewModel> map = new HashMap<>();

    public void setViewModels(List<FormViewModel> viewModels) {
        this.viewModels = viewModels;
    }

    public List<FormViewModel> getViewModels() {
        return viewModels;
    }

    public FormViewModel fetchVMbyCode(String code) {
        return map.get(code);
    }
}
