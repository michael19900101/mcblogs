package com.aotuman.studydemo.wheelview.test

import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Looper
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aotuman.studydemo.R
import com.aotuman.studydemo.wheelview.dialog.DateTimeWheelDialog
import kotlinx.android.synthetic.main.fragment_wheel_dialog.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var dialog1: DateTimeWheelDialog? = null
    var dialog2: DateTimeWheelDialog? = null
    var dialog3: DateTimeWheelDialog? = null
    var dialog4: DateTimeWheelDialog? = null
    var dialog5: DateTimeWheelDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_wheel_dialog)
        btn_choose_time_1.setOnClickListener {
            if (dialog1 == null) dialog1 = createDialog(1) else dialog1!!.show()
        }
        btn_choose_time_2.setOnClickListener {
            if (dialog2 == null) dialog2 = createDialog(2) else dialog2!!.show()
        }
        btn_choose_time_3.setOnClickListener {
            if (dialog3 == null) dialog3 = createDialog(3) else dialog3!!.show()
        }
        btn_choose_time_4.setOnClickListener {
            if (dialog4 == null) dialog4 = createDialog(4) else dialog4!!.show()
        }
        btn_choose_time_5.setOnClickListener {
            if (dialog5 == null) dialog5 = createDialog(5) else dialog5!!.show()
        }
    }

    private fun createDialog(type: Int): DateTimeWheelDialog? {
        var calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = 2015
        calendar[Calendar.MONTH] = 0
        calendar[Calendar.DAY_OF_MONTH] = 1
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        val startDate = calendar.time
        calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = 2022
        val endDate = calendar.time
        val dialog = DateTimeWheelDialog(this)
        //        dialog.setShowCount(7);
//        dialog.setItemVerticalSpace(24);
        dialog.show()
        dialog.setTitle("选择时间")
        var config: Int = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR_MINUTE
        when (type) {
            1 -> config = DateTimeWheelDialog.SHOW_YEAR
            2 -> config = DateTimeWheelDialog.SHOW_YEAR_MONTH
            3 -> config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY
            4 -> config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR
            5 -> config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR_MINUTE
        }
        dialog.configShowUI(config)
        dialog.setCancelButton("取消", null)
        dialog.setOKButton("确定", object : DateTimeWheelDialog.OnClickCallBack {
            override fun callBack(v: View?, selectedDate: Date): Boolean {
                tv_result.setText(SimpleDateFormat.getInstance().format(selectedDate))
                return false
            }

        })
        dialog.setDateArea(startDate, endDate, true)
        dialog.updateSelectedDate(Date())
        return dialog
    }

    override fun onResume() {
        super.onResume()
        val thread = Thread {
//            Looper.prepare()
            Toast.makeText(
                this@MainActivity,
                "子线程弹出Toast",
                Toast.LENGTH_SHORT
            ).show()
//            Looper.loop()
        }
        thread.start()
    }
}
