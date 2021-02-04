package com.aotuman.studydemo.yoga

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.soloader.SoLoader
import com.facebook.yoga.*
import com.facebook.yoga.android.YogaLayout
import com.aotuman.studydemo.R


class DynamicViewActivity : AppCompatActivity() {
    private lateinit var yogaLayout: YogaLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        frontCount = 0;
        SoLoader.init(this, false)
        setContentView(R.layout.yoga_dynamic_layout)
        yogaLayout = findViewById(R.id.yogaLayout)
        yogaLayout.yogaNode.flexDirection = YogaFlexDirection.ROW
        yogaLayout.yogaNode.wrap = YogaWrap.WRAP
        yogaLayout.yogaNode.alignItems = YogaAlign.FLEX_START

//        addChildren(yogaLayout.yogaNode)
        addChildren()
        yogaLayout.invalidate()
    }

    private fun addChildren(root: YogaNode){

        for (index in 0..10){
            val itemView = View(this)
            val bgColor = if (index % 2 == 0 ) Color.BLUE else Color.GREEN
            itemView.setBackgroundColor(bgColor)

            val node = YogaNode.create()
            node.flex = 1F
            node.setMinWidth(
                if (index % 2 == 0) DisplayUtil.dp2px(100F).toFloat() else DisplayUtil.dp2px(
                    50F
                ).toFloat()
            )
            node.setHeight(DisplayUtil.dp2px(100F).toFloat())
            node.data = itemView
            node.setMargin(YogaEdge.VERTICAL, DisplayUtil.dp2px(10F).toFloat())

            yogaLayout.addView(itemView, node)
            root.addChildAt(node, index)
        }
    }

    private fun addChildren(){

        for (index in 0..10){
            val item = TextView(this)
            val bgColor = if (index % 2 == 0 ) Color.BLUE else Color.GREEN
            item.setBackgroundColor(bgColor)
            item.text = if (index % 2 == 0 ) "adsda" else "dasdasdasdsdassdasdads"
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            yogaLayout.addView(item, index, params)
            val node = yogaLayout.getYogaNodeForView(item)

            node.flex = 1F
            node.setMinWidth(if (index % 2 == 0 ) DisplayUtil.dp2px(100F).toFloat() else DisplayUtil.dp2px(50F).toFloat())
            node.setMargin(YogaEdge.VERTICAL, DisplayUtil.dp2px(10F).toFloat())
        }
    }
}

object DisplayUtil {

    /**
     * 根据手机的分辨率从 dp(dip) 的单位 转成为 px(像素)
     */
    @JvmStatic
    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp(dip)
     */
    @JvmStatic
    fun px2dp(pxValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * sp转换成px
     */
    @JvmStatic
    fun sp2px(spValue: Float): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * px转换成sp
     */
    @JvmStatic
    fun px2sp(pxValue: Float): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    @JvmStatic
    fun screenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    @JvmStatic
    fun screenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}