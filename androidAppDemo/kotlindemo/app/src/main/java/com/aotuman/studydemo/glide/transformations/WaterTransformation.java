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
    private static TextPaint textPaint;
    private String waterStr;
    private boolean isAddWater;
    private String saveFilePath;
    private int saveFileLength;
    private boolean needCompress;

    public WaterTransformation(Builder builder) {
        this.isAddWater = builder.isAddWater;
        this.waterStr = builder.waterStr;
        this.saveFilePath = builder.saveFilePath;
        this.saveFileLength = builder.saveFileLength;
        this.needCompress = builder.needCompress;
    }

    public static class Builder {
        private String waterStr;
        private boolean isAddWater;
        private String saveFilePath;
        private int saveFileLength;
        private boolean needCompress;

        public Builder setWaterStr(String waterStr) {
            this.waterStr = waterStr;
            return this;
        }

        public Builder setAddWater(boolean addWater) {
            isAddWater = addWater;
            return this;
        }

        public Builder setSaveFilePath(String saveFilePath) {
            this.saveFilePath = saveFilePath;
            return this;
        }

        public Builder setSaveFileLength(int saveFileLength) {
            this.saveFileLength = saveFileLength;
            return this;
        }

        public Builder setNeedCompress(boolean needCompress) {
            this.needCompress = needCompress;
            return this;
        }

        public WaterTransformation bulid() {
            return new WaterTransformation(this);
        }
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
        Log.e("jbjb","oldBitmap内存："+oldBitmap.getAllocationByteCount());
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
        if (needCompress) {
            // 压缩保存文件
            Utils.compressBitmapToFile(oldBitmap, saveFilePath, saveFileLength);
        } else {
            Utils.saveBitmap2file(oldBitmap, saveFilePath);
        }
        Log.e("jbjb","保存文件成功！");
        return oldBitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        //此处去设置唯一标志符
        String s = "com.aotuman.studydemo" + waterStr;
        try {
            s.getBytes(STRING_CHARSET_NAME);
            messageDigest.update(s.getBytes(STRING_CHARSET_NAME));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}