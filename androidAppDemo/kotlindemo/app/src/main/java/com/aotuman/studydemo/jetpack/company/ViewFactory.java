package com.aotuman.studydemo.jetpack.company;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;

import com.aotuman.studydemo.jetpack.company.view.FormArrayItemLayout;
import com.aotuman.studydemo.jetpack.company.view.FormListView;
import com.aotuman.studydemo.jetpack.company.view.FormTextView;
import com.aotuman.studydemo.jetpack.company.viewmodel.FormListViewModel;
import com.aotuman.studydemo.jetpack.company.viewmodel.FormViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewFactory {

    public static View getView(Context context, FormViewModel viewModel, LifecycleOwner lifecycleOwner){
        String type = viewModel.getType();
        if(type == null){
            return null;
        }
        if(type.equalsIgnoreCase("text")){
            return new FormTextView(context);
        } else if(type.equalsIgnoreCase("input")){
            return new EditText(context);
        } else if(type.equalsIgnoreCase("list")){
            return getListView(context, viewModel, lifecycleOwner);
        }
        return null;
    }

    private static View getListView(Context context, FormViewModel formViewModel, LifecycleOwner lifecycleOwner){
        List<FormViewModel> rowViewModels = ((FormListViewModel)formViewModel).getChildVMs();
        // 生成子控件(测试数据)
        Map<String/**ctrlcode*/, View> rowViewMap = new HashMap<>();
        FormArrayItemLayout itemLayout = new FormArrayItemLayout(context);
        for (FormViewModel viewModel : rowViewModels) {
            View view = getView(context, viewModel, lifecycleOwner);
            rowViewMap.put(viewModel.getCode(), view);
        }
        itemLayout.addViews(rowViewMap);
        return new FormListView(context, lifecycleOwner, rowViewModels, itemLayout);
    }
}
