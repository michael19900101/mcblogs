package com.aotuman.studydemo.glide.recyclerview

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aotuman.studydemo.PathConstant
import com.aotuman.studydemo.R
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File

class GlideRvActvity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var myDataset: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rvglide_main)

        requestPermission()

        for (index in 1 until 7) {
            val localImageUri = PathConstant.CAMERAPATH + File.separator + index + ".jpg"
            myDataset.add(localImageUri)
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = ImageAdapter(myDataset)

        recyclerView = findViewById<RecyclerView>(R.id.rvglide_recyclerview).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    private fun requestPermission() {
        val rxPermission = RxPermissions(this).also {
            it
                .requestEach(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET
                )
                .subscribe { permission ->
                    permission?.granted.let {
                        // 用户已经同意该权限
                        Log.d("jbjb", permission.name + " is granted.")
                    }
                }
        }
    }
}