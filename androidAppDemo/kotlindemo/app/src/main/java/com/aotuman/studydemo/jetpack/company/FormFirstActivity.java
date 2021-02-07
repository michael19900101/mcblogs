package com.aotuman.studydemo.jetpack.company;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aotuman.studydemo.R;
import com.aotuman.studydemo.jetpack.company.view.FormPageActivity;

public class FormFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formpage_first);
        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormFirstActivity.this, FormPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
