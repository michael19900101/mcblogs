package com.aotuman.kotlindemo.leak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aotuman.kotlindemo.R

class SecondActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_second_layout)
    }
}