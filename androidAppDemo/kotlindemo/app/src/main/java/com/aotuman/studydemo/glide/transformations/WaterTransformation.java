package com.aotuman.studydemo.glide.transformations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.aotuman.studydemo.utils.Utils;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class WaterTransformation extends BitmapTransformation {
    private String waterStr;
    private boolean isAddWater;
    private static TextPaint textPaint;
    private String saveFilePath;
    private int saveFileLength;

    public WaterTransformation(boolean isAddWater, String waterStr, String saveFilePath, int saveFileLength) {
        this.isAddWater = isAddWater;
        this.waterStr = waterStr;
        this.saveFilePath = saveFilePath;
        this.saveFileLength = saveFileLength;
    }

    public WaterTransformation setTextSize(float size) {
        textPaint.setTextSize(Utils.sp2px(size));
        return this;
    }

    public WaterTransformation setAlpha(int alpha) {
        textPaint.setAlpha(alpha);
        return this;
    }

    public WaterTransformation isSetWater(boolean isAddWater) {
        this.isAddWater = isAddWater;
        return this;
    }

    static {
        textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setFilterBitmap(true);

    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (isAddWater) return addWaterMark(toTransform);
        else return toTransform;
    }

    private Bitmap addWaterMark(Bitmap oldBitmap) {
        int bitmapWidth = oldBitmap.getWidth();
        int bitmapHeight = oldBitmap.getHeight();

        Log.e("jbjb", String.format("oldBitmap width:%d,oldbitmap height:%d", bitmapWidth, bitmapHeight));

        Canvas canvas = new Canvas(oldBitmap);

        float textSize = Math.max(11, (float) Math.ceil(bitmapWidth / 25));
        textPaint.setTextSize(textSize);

        int textPadding = Utils.dp2px(5);
        int textWidth = canvas.getWidth() - 2 * textPadding;

        StaticLayout textLayout = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder builder = StaticLayout.Builder.obtain(waterStr,0, waterStr.length(),textPaint, textWidth);
            textLayout = builder.build();
        } else {
            textLayout = new StaticLayout(waterStr,  textPaint, textWidth,
                    Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        }

        int textHeight = textLayout.getHeight();

        float x = textPadding;
        float y = bitmapHeight - textHeight - textPadding;

        // draw text to the Canvas center
        canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();

        Log.e("jbjb","开始保存文件");
        // 压缩保存文件
        Utils.compressBitmapToFile(oldBitmap, saveFilePath, saveFileLength);

//        Utils.saveBitmap2file(oldBitmap, saveFilePath);

        Log.e("jbjb","保存文件成功！");
        return oldBitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) { //此处去设置唯一标志符
//        String s = "包名" + waterStr;
        String s = "com.aotuman.studydemo" + waterStr;
        try {
            s.getBytes(STRING_CHARSET_NAME);
            messageDigest.update(s.getBytes(STRING_CHARSET_NAME));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}