package com.aotuman.studydemo.jetpack.company;

import android.view.View;


public class ViewPresenter {
    View view;
    FormViewModel viewModel;

    public ViewPresenter(FormViewModel viewModel){
        this.viewModel = viewModel;
    }

    public View genViewByVM() {
        return view;
    }
}
