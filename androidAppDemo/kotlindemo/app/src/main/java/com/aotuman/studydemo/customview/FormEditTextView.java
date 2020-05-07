package com.aotuman.studydemo.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.aotuman.studydemo.R;

public class FormEditTextView extends FormBaseViewGroup{

    public FormEditTextView(Context context) {
        super(context);
        initView(context);
    }

    public FormEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FormEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public FormEditTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {

//        titleView.setVisibility(GONE);
//        errorLineView.setVisibility(GONE);
        titleView.setTitle("测试title");
        errorTextView.setText("必填不能为空！");
    }

    @Override
    protected int setOrientation() {
        return FormBaseViewGroup.VERTICAL;
    }

    @Override
    protected View initContentView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.custom_edit_layout, this, false);
    }
}
