package com.aotuman.studydemo.glide

import android.Manifest
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aotuman.studydemo.PathConstant
import com.aotuman.studydemo.R
import com.aotuman.studydemo.glide.transformations.WaterTransformation
import com.aotuman.studydemo.livepermissions.LivePermissions
import com.aotuman.studydemo.livepermissions.PermissionResult
import com.aotuman.studydemo.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_glide_main.*
import java.io.File
import java.lang.Exception

class GlideMainActivity : AppCompatActivity() {
    private var saveFileLength = 90
    private var needCompress = false
    private var targetWidth = 600
    private var targetHeight = 840

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "照片压缩"
//        requestPermission()
        requestPermission2()
        setContentView(R.layout.activity_glide_main)
        btn_compress.setOnClickListener {
            for (index in 1 until 2) {
                compress(index)
            }
        }
    }

    private fun compress(index:Int) {
        val localImageUri = PathConstant.CAMERAPATH + File.separator + index + ".jpg"
        val saveImageUri = PathConstant.ATTACHMENT + File.separator + "save_"+ index + "_" + targetWidth + "X" + targetHeight + ".jpg"
//        val dialog: Dialog = AlertDialog.Builder(this)
//            .setMessage("正在处理图片，请稍候..")
//            .setTitle("提示")
//            .create()
//        dialog.show()
        val waterStr = "我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女"
        val waterTransformation = WaterTransformation.Builder()
            .setWaterStr(waterStr)
            .setWatermarkposition("bottom")
            .setSaveFilePath(saveImageUri)
            .setSaveFileLength(saveFileLength)
            .setNeedCompress(needCompress)
            .setTransformListener { Toast.makeText(this,"处理照片出错！",Toast.LENGTH_SHORT).show()}
            .bulid()

        val imageOption = Utils.getImageOption(localImageUri, targetWidth, targetHeight)
        var target = object :CustomTarget<Drawable?>() {

            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable?>?
            ) {
                Log.e("jbjb","加载完毕")
                Toast.makeText(this@GlideMainActivity,"处理照片完毕！",Toast.LENGTH_SHORT).show()
//                dialog.dismiss()
            }
            override fun onLoadCleared(placeholder: Drawable?) {
//                dialog.dismiss()
            }
        }
        Glide.with(this)
            .load(localImageUri)
            .format(DecodeFormat.PREFER_RGB_565)
            .override(imageOption.width, imageOption.height)
            .apply(RequestOptions.bitmapTransform(waterTransformation))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(target)
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

    private fun requestPermission2() {
        LivePermissions(this).request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        ).observe(this, Observer {
            when (it) {
                is PermissionResult.Grant -> {  //权限允许
                    Toast.makeText(this, "Grant", Toast.LENGTH_SHORT).show()
                }
                is PermissionResult.Rationale -> {  //权限拒绝
                    it.permissions.forEach {s->
                        println("Rationale:${s}")
                    }
                    Toast.makeText(this, "Rationale", Toast.LENGTH_SHORT)
                        .show()
                }
                is PermissionResult.Deny -> {   //权限拒绝，且勾选了不再询问
                    it.permissions.forEach {s->
                        println("deny:${s}")
                    }
                    Toast.makeText(this, "deny", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.imagesize_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.k_origin -> {
                needCompress = false
                true
            }
            R.id.k_90 -> {
                needCompress = true
                saveFileLength = 90
                true
            }
            R.id.size_screen_w_h -> {
                targetWidth = Utils.getScreenWidth()
                targetHeight = Utils.getScreenHeight()
                true
            }
            R.id.size_600_840 -> {
                targetWidth = 600
                targetHeight = 840
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    //        Glide.with(this)
////            .load(Constants.IMAGE_URIS[8])
//            .load(localImageUri)
//            .apply(RequestOptions.bitmapTransform(waterTransformation))
//            .into(glide_image)

}