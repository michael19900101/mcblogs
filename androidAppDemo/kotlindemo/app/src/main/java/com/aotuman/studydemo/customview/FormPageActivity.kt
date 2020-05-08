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
        editTextView1.errorLineView?.visibility = View.VISIBLE
        editTextView1.errorTextView?.visibility = View.VISIBLE
        editTextView1.titleView?.visibility = View.VISIBLE
        editTextView1.titleView?.setRequire(true)
        editTextView1.titleView?.setTitle("plain模式水平方向显示错误")
        editTextView1.setOrientation(FormBaseViewGroup.HORIZONTAL)
        editTextView1.setMode(FormBaseViewGroup.PLAIN)
        dynamicView1.addChildView(editTextView1)

        val editTextView2 = FormEditTextView(this)
        editTextView2.errorLineView?.visibility = View.GONE
        editTextView2.errorTextView?.visibility = View.GONE
        editTextView2.titleView?.visibility = View.VISIBLE
        editTextView2.titleView?.setRequire(false)
        editTextView2.titleView?.setTitle("plain模式水平方向隐藏错误")
        editTextView2.setOrientation(FormBaseViewGroup.HORIZONTAL)
        editTextView2.setMode(FormBaseViewGroup.PLAIN)
        dynamicView2.addChildView(editTextView2)

        val editTextView3 = FormEditTextView(this)
        editTextView3.errorLineView?.visibility = View.VISIBLE
        editTextView3.errorTextView?.visibility = View.VISIBLE
        editTextView3.titleView?.visibility = View.VISIBLE
        editTextView3.titleView?.setRequire(true)
        editTextView3.titleView?.setTitle("plain模式竖直方向显示错误")
        editTextView3.setOrientation(FormBaseViewGroup.VERTICAL)
        editTextView3.setMode(FormBaseViewGroup.PLAIN)
        dynamicView3.addChildView(editTextView3)

        val editTextView4 = FormEditTextView(this)
        editTextView4.errorLineView?.visibility = View.GONE
        editTextView4.errorTextView?.visibility = View.GONE
        editTextView4.titleView?.visibility = View.VISIBLE
        editTextView4.titleView?.setRequire(false)
        editTextView4.titleView?.setTitle("plain模式竖直方向隐藏错误")
        editTextView4.setOrientation(FormBaseViewGroup.VERTICAL)
        editTextView4.setMode(FormBaseViewGroup.PLAIN)
        dynamicView4.addChildView(editTextView4)

        val editTextView5 = FormEditTextView(this)
        editTextView5.errorLineView?.visibility = View.VISIBLE
        editTextView5.errorTextView?.visibility = View.VISIBLE
        editTextView5.titleView?.visibility = View.VISIBLE
        editTextView5.titleView?.setRequire(true)
        editTextView5.titleView?.setTitle("free模式水平方向显示错误")
        editTextView5.setOrientation(FormBaseViewGroup.HORIZONTAL)
        editTextView5.setMode(FormBaseViewGroup.FREE)
        dynamicView5.addChildView(editTextView5)

        val editTextView6 = FormEditTextView(this)
        editTextView6.errorLineView?.visibility = View.GONE
        editTextView6.errorTextView?.visibility = View.GONE
        editTextView6.titleView?.visibility = View.VISIBLE
        editTextView6.titleView?.setRequire(false)
        editTextView6.titleView?.setTitle("free模式水平方向隐藏错误")
        editTextView6.setOrientation(FormBaseViewGroup.HORIZONTAL)
        editTextView6.setMode(FormBaseViewGroup.FREE)
        dynamicView6.addChildView(editTextView6)

        val editTextView7 = FormEditTextView(this)
        editTextView7.errorLineView?.visibility = View.VISIBLE
        editTextView7.errorTextView?.visibility = View.VISIBLE
        editTextView7.titleView?.visibility = View.VISIBLE
        editTextView7.titleView?.setRequire(true)
        editTextView7.titleView?.setTitle("free模式竖直方向显示错误")
        editTextView7.setOrientation(FormBaseViewGroup.VERTICAL)
        editTextView7.setMode(FormBaseViewGroup.FREE)
        dynamicView7.addChildView(editTextView7)

        val editTextView8 = FormEditTextView(this)
        editTextView8.errorLineView?.visibility = View.GONE
        editTextView8.errorTextView?.visibility = View.GONE
        editTextView8.titleView?.visibility = View.VISIBLE
        editTextView8.titleView?.setRequire(false)
        editTextView8.titleView?.setTitle("free模式竖直方向隐藏错误")
        editTextView8.setOrientation(FormBaseViewGroup.VERTICAL)
        editTextView8.setMode(FormBaseViewGroup.FREE)
        dynamicView8.addChildView(editTextView8)

        val editTextView9 = FormEditTextView(this)
        editTextView9.errorLineView?.visibility = View.GONE
        editTextView9.errorTextView?.visibility = View.GONE
        editTextView9.titleView?.visibility = View.VISIBLE
        editTextView9.titleView?.setRequire(false)
        editTextView9.titleView?.setTitle("basic模式水平方向隐藏错误")
        editTextView9.setOrientation(FormBaseViewGroup.HORIZONTAL)
        editTextView9.setMode(FormBaseViewGroup.BASIC)
        dynamicView9.addChildView(editTextView9)
    }
}