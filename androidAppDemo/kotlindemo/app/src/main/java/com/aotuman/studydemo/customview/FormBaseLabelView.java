package com.aotuman.studydemo.customview;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aotuman.studydemo.R;

public class FormBaseLabelView extends LinearLayout{

    private boolean require = false;
    private String title = "";
    private TextView tv_title;
    private View view_require;
    private boolean titleBold;

    public FormBaseLabelView(Context context){
        super(context);
        init(context);
    }

    private void init(Context context){
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.layout_label_view,this);
        tv_title = findViewById(R.id.formview_label_title);
        view_require = findViewById(R.id.formview_label_require);
        setRequire(require);
        setTitle(title);
    }

    public void setRequire(boolean require){
        this.require = require;
        view_require.setVisibility(require? View.VISIBLE:View.INVISIBLE);
    }

    public void setTitle(String title){
        this.title = title;
        tv_title.setText(TextUtils.isEmpty(title)?"":title);
    }

    private void setTitleBold(boolean titleBold){
        if (null != tv_title && titleBold){
            TextPaint tp = tv_title.getPaint();
            tp.setFakeBoldText(true);
        }
    }

    public void setMaxLine(int maxLine) {
        tv_title.setMaxLines(maxLine);
    }

    public void setTvTitleVisibility(int visibility){
        tv_title.setVisibility(visibility);
    }

    public void setTitleColor(int color){
        tv_title.setTextColor(color);
    }
}
