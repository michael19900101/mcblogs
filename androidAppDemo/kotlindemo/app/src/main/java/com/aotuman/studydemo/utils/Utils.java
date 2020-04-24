package com.aotuman.studydemo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.aotuman.studydemo.glide.ImageOption;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class Utils {

    public static final String TAG = "Utils";

    private Utils() {
        // Utility class.
    }

    public static Drawable getMaskDrawable(Context context, int maskId) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(maskId);
        } else {
            drawable = context.getResources().getDrawable(maskId);
        }

        if (drawable == null) {
            throw new IllegalArgumentException("maskId is invalid");
        }

        return drawable;
    }

    public static int getScreenWidth() {
        DisplayMetrics metrics = ApplicationContext.context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics metrics = ApplicationContext.context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * px转换成sp
     *
     * @param pxValue
     * @return
     */
    public static float px2sp(float pxValue) {
        float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp转换成px
     *
     * @param dpValue
     * @return
     */
    public static int dp2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * 保存bitmap到sd卡filePath文件中 如果有，则删除
     *
     * @param bitmap
     * @param filePath :图片绝对路径
     * @return boolean :是否成功
     */
    public static boolean saveBitmap2file(Bitmap bitmap, String filePath) {
        if (bitmap == null) {
            return false;
        }
        //压缩格式
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();//创建父目录
        }
        if (file.exists()) {
            file.delete();
        }

        try {
            stream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap.compress(format, quality, stream);
    }

    public static ImageOption getImageOption(String path, int targetWidth, int targetHeight) {
        ImageOption imageOption = new ImageOption();
        File file = new File(path);
        if (file == null || file.isDirectory()) {
            return imageOption;
        }
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;//不返回实际bitmap但可获取图片宽高信息
        BitmapFactory.decodeFile(file.getAbsolutePath(), option);
        int bitmapWidth = option.outWidth;
        int bitmapHeight = option.outHeight;
        Log.d("jbjb", String.format("[原图宽:%d，高：%d],  [目标宽：%d, 高：%d]",
                bitmapWidth, bitmapHeight, targetWidth, targetHeight));
        int resultWidth = 0;
        int resultHeight = 0;
        if (bitmapWidth < targetWidth) {
            if (bitmapHeight > targetHeight) {
                resultWidth = targetHeight * bitmapWidth / bitmapHeight;
                resultHeight = targetHeight;
            } else {
                resultWidth = bitmapWidth;
                resultHeight = bitmapHeight;
            }
        } else {
            if (bitmapHeight > targetHeight) {
                if (bitmapWidth > bitmapHeight) {
                    resultWidth = targetWidth;
                    resultHeight = targetWidth * bitmapHeight / bitmapWidth;
                } else {
                    resultWidth = targetHeight * bitmapWidth / bitmapHeight;
                    resultHeight = targetHeight;
                }
            } else {
                resultWidth = targetWidth;
                resultHeight = targetWidth * bitmapHeight / bitmapWidth;
            }
        }
        imageOption.setWidth(resultWidth);
        imageOption.setHeight(resultHeight);
        Log.d("jbjb", String.format("[缩放后宽:%d，高：%d]",
                resultWidth, resultHeight));
        return imageOption;
    }

    /***
     * 压缩图片
     * @return
     */
    public static void compressBitmapToFile(Bitmap currentBitmap, String saveFilePath, int requireSize, boolean needRecycle)
            throws OutOfMemoryError {

        /** ------------ 根据图片方向，作旋转处理 ------------ */
        FileOutputStream fos = null;
        WeakReference<Bitmap> bitmapWeakReference = null;
        try {
            if (currentBitmap != null) {
                bitmapWeakReference = new WeakReference<>(currentBitmap);
                if(bitmapWeakReference.get() != null){
                    ByteArrayOutputStream oStream = new ByteArrayOutputStream();
                    bitmapWeakReference.get().compress(Bitmap.CompressFormat.JPEG, 100, oStream);
                    //---------------------------------当前文件大小------------------------
                    Log.d(TAG, "origin byte array size : " + oStream.size() / 1024 + "kb");
                    //压缩
                    int quality = 100;
                    while (oStream.size() > (requireSize * 1024) && quality > 10) {
                        oStream.reset();
                        quality = quality - 5;
                        if(bitmapWeakReference.get() != null){
                            bitmapWeakReference.get().compress(Bitmap.CompressFormat.JPEG, quality, oStream);
                        }
                    }
                    Log.d(TAG,"final byte array size : " + oStream.size() / 1024 + "kb");

                    fos = new FileOutputStream(saveFilePath);
                    fos.write(oStream.toByteArray());
                    fos.flush();
                    oStream.close();
                    if(needRecycle && bitmapWeakReference != null && bitmapWeakReference.get() != null){
                        bitmapWeakReference.get().recycle();
                    }
                }
            }
        } catch (NullPointerException ex) {
            //softreference 有被回收的风险
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(needRecycle && bitmapWeakReference != null && bitmapWeakReference.get() != null && !bitmapWeakReference.get().isRecycled()){
                bitmapWeakReference.get().recycle();
            }
        }
    }
}
