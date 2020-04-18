package com.aotuman.studydemo

import android.app.Application
import com.aotuman.studydemo.utils.ApplicationContext

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationContext.context = applicationContext
        PathConstant.init(this)
    }
}