package com.aotuman.studydemo.jetpack.company.view;

import android.content.Context;
import android.util.AttributeSet;

public class FormTextView extends androidx.appcompat.widget.AppCompatTextView implements FormViewBehavior {

    public FormTextView(Context context) {
        super(context);
        setText("初始化文字");
    }

    public FormTextView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setText("初始化文字");
    }

    @Override
    public void refresh(Object data) {
        if (data instanceof String) {
            setText(data.toString());
        }
    }
}
