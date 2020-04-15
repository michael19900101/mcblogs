package com.aotuman.studydemo.leak

import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aotuman.studydemo.R
import kotlinx.android.synthetic.main.activity_leak_first_layout.*
import kotlinx.android.synthetic.main.activity_leak_second_layout.*

class BActivity : AppCompatActivity() {
    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_second_layout)

        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                Toast.makeText(this@BActivity, "AAAAAA", Toast.LENGTH_SHORT).show()
            }
        }

//        Thread(Runnable {
//            try {
//                Thread.sleep(20000)
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
//            // 步骤3：创建所需的消息对象
//
//            // 步骤3：创建所需的消息对象
//            val msg = Message.obtain()
//            msg.what = 1 // 消息标识
//
//            msg.obj = "A" // 消息内存存放
//
//
//            // 步骤4：在工作线程中 通过Handler发送消息到消息队列中
//
//            // 步骤4：在工作线程中 通过Handler发送消息到消息队列中
//            handler?.sendMessage(msg)
//        }).start()

        // 匿名内部类内存泄露
        // all retained object were garbage collected
        btn_handlerphoto.setOnClickListener {
            HandlePhotoService.handlePhoto(object : IHandlerPhoto{
                override fun handlePhoto() {
                    Log.e("jbjb","匿名内部类 处理完照片")
                }
            })
        }

        // 内部类内存泄露
        // 这个可以捕捉
//        btn_handlerphoto.setOnClickListener {
//            HandlePhotoService.handlePhoto(Inner())
//        }

        // 静态内部类不会持有外部类的引用，不会有内存泄露
        // all retained object were garbage collected
//        btn_handlerphoto.setOnClickListener {
//            HandlePhotoService.handlePhoto(StaticInner())
//        }

    }

    companion object {

        class StaticInner : IHandlerPhoto{

            override fun handlePhoto() {
                Log.e("jbjb","静态内部类 处理完照片")
            }
        }
    }

    inner class Inner : IHandlerPhoto{

        override fun handlePhoto() {
            Log.e("jbjb","内部类 处理完照片")
        }
    }
}