package com.aotuman.studydemo.leak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aotuman.studydemo.R

class SecondActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_second_layout)
    }
}