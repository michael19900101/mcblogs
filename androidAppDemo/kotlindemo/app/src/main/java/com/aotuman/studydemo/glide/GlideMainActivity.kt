package com.aotuman.studydemo.glide

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aotuman.studydemo.PathConstant
import com.aotuman.studydemo.R
import com.aotuman.studydemo.glide.transformations.WaterTransformation
import com.aotuman.studydemo.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.activity_glide_main.*
import java.io.File

class GlideMainActivity : AppCompatActivity() {

    private val index = 1
    private val localImageUri = PathConstant.CAMERAPATH + File.separator + index + ".jpg"
    private val saveImageUri = PathConstant.CAMERAPATH + File.separator + "save_"+ index + ".jpg"
    private var target = object :CustomTarget<Drawable?>() {

        override fun onResourceReady(
            resource: Drawable,
            transition: Transition<in Drawable?>?
        ) {
            Log.e("jbjb","加载完毕")
        }
        override fun onLoadCleared(placeholder: Drawable?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide_main)
        val waterStr = "我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女我是美女"
        val waterTransformation = WaterTransformation(true, waterStr, saveImageUri, 90)
        val imageOption = Utils.getImageOption(localImageUri)
        Glide.with(this)
            .load(localImageUri)
            .override(imageOption.width, imageOption.height)
            .apply(RequestOptions.bitmapTransform(waterTransformation))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(target)
    }

    override fun onStop() {
        super.onStop()
        Glide.with(this).clear(glide_image)
        Glide.with(this).clear(target)
    }

    //        Glide.with(this)
////            .load(Constants.IMAGE_URIS[8])
//            .load(localImageUri)
//            .apply(RequestOptions.bitmapTransform(waterTransformation))
//            .into(glide_image)

}