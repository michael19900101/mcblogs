package com.aotuman.studydemo.glide.transformations;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.aotuman.studydemo.utils.Utils;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class WaterTransformation extends BitmapTransformation {
    private static TextPaint textPaint;
    private String waterStr;
    private String watermarkposition;
    private String saveFilePath;
    private int saveFileLength;
    private boolean needCompress;
    private TransformListener listener;

    public interface TransformListener {
        void transformError(Exception e);
    }

    public WaterTransformation(Builder builder) {
        this.waterStr = builder.waterStr;
        this.watermarkposition = builder.watermarkposition;
        this.saveFilePath = builder.saveFilePath;
        this.saveFileLength = builder.saveFileLength;
        this.needCompress = builder.needCompress;
        this.listener = builder.listener;
    }

    public static class Builder {
        private String waterStr;
        private String saveFilePath;
        private int saveFileLength;
        private String watermarkposition;
        private boolean needCompress;
        private TransformListener listener;

        public Builder setWaterStr(String waterStr) {
            this.waterStr = waterStr;
            return this;
        }

        public Builder setWatermarkposition(String watermarkposition) {
            this.watermarkposition = watermarkposition;
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

        public Builder setTransformListener(TransformListener listener) {
            this.listener = listener;
            return this;
        }

        public WaterTransformation bulid() {
            return new WaterTransformation(this);
        }
    }

    static {
        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setFilterBitmap(true);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        try {
            // 1.添加水印
            Bitmap bitmap = addWaterMark(toTransform);
            // 2.压缩保存文件
            if (needCompress) {
                // 压缩保存文件
                Utils.compressBitmapToFile(bitmap, saveFilePath, saveFileLength, false);
            } else {
                Utils.saveBitmap2file(bitmap, saveFilePath);
            }
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.transformError(e);
            }
            return toTransform;
        }
    }

    private Bitmap addWaterMark(Bitmap oldBitmap) {
        if (TextUtils.isEmpty(waterStr)) return oldBitmap;
        int bitmapWidth = oldBitmap.getWidth();
        int bitmapHeight = oldBitmap.getHeight();
        Canvas canvas = new Canvas(oldBitmap);
        float textSize = Math.max(11, (float) Math.ceil(bitmapWidth / 25));
        textPaint.setTextSize(textSize);
        // 直接覆盖在图片之上，白色14号字，黑色阴影
        textPaint.setShadowLayer(5, 5, 5, Color.BLACK);
        int textPadding = dp2px(16);
        int textWidth = canvas.getWidth() - 2 * textPadding;
        StaticLayout textLayout = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder builder = StaticLayout.Builder.obtain(waterStr, 0, waterStr.length(), textPaint, textWidth);
            textLayout = builder.build();
        } else {
            textLayout = new StaticLayout(waterStr, textPaint, textWidth,
                    Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        }

        int textHeight = textLayout.getHeight();

        canvas.save();
        if ("top".equals(watermarkposition)) {
            // 上方，左对齐
            float x = textPadding;
            float y = textPadding;
            canvas.translate(x, y);
        } else if ("center".equals(watermarkposition)) {
            // 居中，左对齐
            float x = textPadding;
            float y = bitmapHeight / 2;
            canvas.translate(x, y);
        } else {
            // 下方，左对齐
            float x = textPadding;
            float y = bitmapHeight - textHeight - textPadding;
            canvas.translate(x, y);
        }

        textLayout.draw(canvas);
        canvas.restore();
        return oldBitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        //此处去设置唯一标志符
        String s = "com.aotuman.studydemo." + waterStr;
        try {
            s.getBytes(STRING_CHARSET_NAME);
            messageDigest.update(s.getBytes(STRING_CHARSET_NAME));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private int dp2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}