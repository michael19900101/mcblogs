package com.aotuman.studydemo.leak

import android.content.Intent
import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.aotuman.studydemo.R
import com.aotuman.studydemo.glide.GlideMainActivity
import kotlinx.android.synthetic.main.activity_leak_first_layout.*

class AActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_first_layout)
        button_jump.setOnClickListener {
            val intent = Intent(this, GlideMainActivity::class.java)
            startActivity(intent)
        }
    }
}