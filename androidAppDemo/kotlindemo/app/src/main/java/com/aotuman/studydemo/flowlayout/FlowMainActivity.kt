package com.aotuman.studydemo.flowlayout

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.aotuman.studydemo.R
import kotlinx.android.synthetic.main.layout_flow.*

class FlowMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_flow)
        for(i in 0..3) {
            val layout = LayoutInflater.from(this).inflate(R.layout.item_layout_text_objpicker, null)
            flow.addView(layout)
        }

    }
}