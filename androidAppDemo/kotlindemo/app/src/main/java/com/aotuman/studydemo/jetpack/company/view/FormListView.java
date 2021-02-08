package com.aotuman.studydemo.jetpack.company.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aotuman.studydemo.jetpack.company.model.User;
import com.aotuman.studydemo.jetpack.company.viewmodel.FormViewModel;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FormListView extends RecyclerView implements FormViewBehavior {

    private FormListAdapter adapter;
    //列表数据
    private List<Object> listData = new ArrayList<>();

    private FormArrayItemLayout itemLayout;

    private LifecycleOwner lifecycleOwner;

    private List<FormViewModel> rowViewModels;

    private List<List<FormViewModel>> allViewModels = new ArrayList<>();

    public FormListView(Context context, LifecycleOwner lifecycleOwner,
                        List<FormViewModel> rowViewModels, FormArrayItemLayout itemLayout) {
        super(context);
        this.rowViewModels = rowViewModels;
        this.lifecycleOwner = lifecycleOwner;
        this.itemLayout = itemLayout;
        init(context);
    }

    @Override
    public void refresh(Object data) {
        if (data instanceof List) {
            allViewModels.clear();
            listData = (List<Object>) data;
            for (int i = 0; i < listData.size(); i ++) {
                List<FormViewModel> newRowViewModels = createRowVM(rowViewModels);
                allViewModels.add(newRowViewModels);
            }
            adapter = new FormListAdapter(getContext(), listData);
            setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private List<FormViewModel> createRowVM(List<FormViewModel> rowViewModels) {
        List<FormViewModel> newRowViewModels = new ArrayList<>();
        for (FormViewModel viewModel: rowViewModels) {
            try {
                Constructor constructor = viewModel.getClass().getDeclaredConstructor();
                constructor.setAccessible(true);
                FormViewModel vm = (FormViewModel) constructor.newInstance();
                newRowViewModels.add(vm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newRowViewModels;
    }

    private void init(Context context) {
        addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        setLayoutManager(new LinearLayoutManager(context));
        adapter = new FormListAdapter(context, listData);
        setAdapter(adapter);
    }

    private void updateObserveLiveData(Map<String/**ctrlcode*/, View> rowViewMap, int rowPosition) {
        if (allViewModels == null || allViewModels.isEmpty()) return;
        List<FormViewModel> rowViewModels = allViewModels.get(rowPosition);
        for (FormViewModel viewModel : rowViewModels) {
            for (MutableLiveData<Object> liveData: viewModel.getLiveDataList()) {
                // 先移除之前的数据监听者View
                liveData.removeObservers(lifecycleOwner);
                // 重新绑定当前行的数据监听者View
                liveData.observe(lifecycleOwner,
                        // update UI
                        value -> {
                            Log.d("FormListView", "value change");
                            View view = rowViewMap.get(viewModel.getCode());
                            if (view instanceof FormViewBehavior) {
                                ((FormViewBehavior) view).refresh(value);
                            }
                        }
                );
            }
        }
    }

    public class FormListAdapter extends RecyclerView.Adapter<FormViewHolder> {

        List<Object> listdata;
        Context context;

        FormListAdapter(Context context, List<Object> data) {
            this.context = context;
            this.listdata = data;
        }

        public Object getItem(int i) {
            if (i < listData.size()) {
                return listdata.get(i);
            } else {
                return null;
            }
        }

        @NonNull
        @Override
        public FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("FormListView", "onCreateViewHolder");
            FormArrayItemLayout layout = itemLayout.generateNewItemLayout(rowViewModels, lifecycleOwner);
            FormViewHolder holder = new FormViewHolder(layout);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull FormViewHolder holder, int position) {
            Log.d("FormListView", "onBindViewHolder");
            Object itemData = getItem(position);
            holder.itemData = itemData;
            holder.position = position;
            updateObserveLiveData(holder.layout.getRowViewMap(), position);
            // 测试给行控件的某一个子控件赋值
            testSetChildData(holder.layout, itemData);
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }
    }

    class FormViewHolder extends ViewHolder {
        FormArrayItemLayout layout;
        Object itemData;
        int position = -1;

        public FormViewHolder(FormArrayItemLayout itemView) {
            super(itemView);
            this.layout = itemView;
        }
    }

    // 测试给行控件的某一个子控件赋值
    private void testSetChildData(FormArrayItemLayout layout, Object itemData) {
        if (itemData instanceof User) {
            View view = layout.getRowViewMap().get("tv-001");
            if (view instanceof FormTextView) {
                ((FormTextView) view).refresh(((User) itemData).getName());
            }
        }
    }
}
