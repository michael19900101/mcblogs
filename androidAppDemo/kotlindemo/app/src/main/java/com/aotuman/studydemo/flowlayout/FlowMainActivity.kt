package com.aotuman.studydemo.flowlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aotuman.studydemo.R
import kotlinx.android.synthetic.main.layout_flow.*
import java.lang.Exception

class FlowMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_flow)
        for(i in 1..18) {
            val layout = LayoutInflater.from(this).inflate(
                R.layout.item_layout_text_objpicker,
                null
            )
            layout.findViewById<TextView>(R.id.tv_node_text).text = "测试文字$i"
            layout.findViewById<ImageView>(R.id.iv_node_del).setOnClickListener{
                flow.removeView(layout)
            }
            flow.addView(layout)
        }
        flow.showMoreView.setOnClickListener {
            Toast.makeText(this, "显示更多", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (flow.childCount > flow.maxDrawChildIndex) {
//            val delChildViews = mutableListOf<View>()
//            for (index in flow.maxDrawChildIndex+1 until flow.childCount) {
//                delChildViews.add(flow.getChildAt(index))
//            }
//            for (view in delChildViews) {
//                flow.removeView(view)
//            }


//            try {
//                flow.removeViews(flow.maxDrawChildIndex,flow.childCount - flow.maxDrawChildIndex)
//            } catch (e:Exception) {
//                e.printStackTrace()
//            }
        }

    }
}