package com.aotuman.studydemo.jetpack.company;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aotuman.studydemo.jetpack.company.view.FormTextView;

public class ViewFactory {

    public static View getView(Context context, String type){
        if(type == null){
            return null;
        }
        if(type.equalsIgnoreCase("text")){
            return new FormTextView(context);
        } else if(type.equalsIgnoreCase("input")){
            return new EditText(context);
        }
        return null;
    }
}
