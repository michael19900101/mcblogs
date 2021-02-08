package com.aotuman.studydemo.jetpack.company.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;

import com.aotuman.studydemo.jetpack.company.ViewFactory;
import com.aotuman.studydemo.jetpack.company.viewmodel.FormViewModel;
import com.aotuman.studydemo.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormArrayItemLayout extends LinearLayout {

    private Map<String/**ctrlcode*/, View> rowViewMap;

    public FormArrayItemLayout(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public void addViews(Map<String/**ctrlcode*/, View> rowViewMap) {
        this.rowViewMap = rowViewMap;
        for (Map.Entry<String/**ctrlcode*/, View> entry : rowViewMap.entrySet()) {
            addView(entry.getValue());
        }
    }

    public Map<String, View> getRowViewMap() {
        return rowViewMap;
    }

    public FormArrayItemLayout generateNewItemLayout(List<FormViewModel> rowViewModels, LifecycleOwner lifecycleOwner){
        FormArrayItemLayout layout = new FormArrayItemLayout(getContext());
        Map<String/**ctrlcode*/, View> rowViewMap = new HashMap<>();
        for (FormViewModel viewModel: rowViewModels) {
            View view = ViewFactory.getView(getContext(), viewModel, lifecycleOwner);
            // 测试，为了列表高度显示没有那么紧凑
            if (view instanceof TextView) {
                ((TextView)view).setMinHeight(Utils.dp2px(50));
            }
            rowViewMap.put(viewModel.getCode(), view);
        }
        layout.addViews(rowViewMap);
        return layout;
    }

}
