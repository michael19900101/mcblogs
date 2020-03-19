package com.aotuman.kotlindemo.leak

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aotuman.kotlindemo.R
import kotlinx.android.synthetic.main.activity_leak_first_layout.*

class FirstActivity : AppCompatActivity() , ContainerManager.Companion.DummyListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_first_layout)
        button_jump.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        ContainerManager.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        ContainerManager.unregisterListener(this)
    }

    override fun onAction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}