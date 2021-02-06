package com.aotuman.studydemo.jetpack.company;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageViewModel extends ViewModel {

    private List<XWViewModel> viewModels = new ArrayList<>();

    private Map<String/**pagecode*/, XWViewModel> map = new HashMap<>();

    public void setViewModels(List<XWViewModel> viewModels) {
        this.viewModels = viewModels;
    }

    public List<XWViewModel> getViewModels() {
        return viewModels;
    }

    public XWViewModel fetchVMbyCode(String code) {
        return map.get(code);
    }
}
