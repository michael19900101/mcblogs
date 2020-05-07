package com.aotuman.studydemo.customview

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aotuman.studydemo.R
import kotlinx.android.synthetic.main.activity_dynamicview.*

class FormPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamicview)
        val editTextView1 = FormEditTextView(this)
        editTextView1.errorLineView.visibility = View.VISIBLE
        editTextView1.titleView.visibility = View.VISIBLE
        editTextView1.titleView.setRequire(true)
        editTextView1.titleView.setTitle("测试显示title")
        dynamicView.addChildView(editTextView1)
    }
}