package com.aotuman.studydemo.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.aotuman.studydemo.utils.Utils;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;

public class WaterMarkTransformation extends BitmapTransformation {

  private static final int VERSION = 1;
  private static final String ID =
      "com.aotuman.studydemo.glide.WaterMarkTransformation." + VERSION;

  private static Paint paint = new Paint();
  private int maskId;

  static {
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
  }

  /**
   * @param maskId If you change the mask file, please also rename the mask file, or Glide will get
   *               the cache with the old mask. Because key() return the same values if using the
   *               same make file name. If you have a good idea please tell us, thanks.
   */
  public WaterMarkTransformation(int maskId) {
    this.maskId = maskId;
  }

  @Override
  protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                             @NonNull Bitmap toTransform, int outWidth, int outHeight) {
    int width = toTransform.getWidth();
    int height = toTransform.getHeight();

    Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
    bitmap.setHasAlpha(true);

    Drawable mask = Utils.getMaskDrawable(context.getApplicationContext(), maskId);

    setCanvasBitmapDensity(toTransform, bitmap);

    Canvas canvas = new Canvas(bitmap);
    mask.setBounds(0, 0, width, height);
    mask.draw(canvas);
    canvas.drawBitmap(toTransform, 0, 0, paint);
    return bitmap;
  }

  @Override
  public String toString() {
    return "MaskTransformation(maskId=" + maskId + ")";
  }

  // 重写epquals和hashcode方法，确保对象唯一性，以和其他的图片变换做区分
  @Override
  public boolean equals(Object o) {
    return o instanceof WaterMarkTransformation &&
        ((WaterMarkTransformation) o).maskId == maskId;
  }

  // 重写epquals和hashcode方法，确保对象唯一性，以和其他的图片变换做区分
  @Override
  public int hashCode() {
    return ID.hashCode() + maskId * 10;
  }

  // 可通过内部算法 重写此方法自定义图片缓存key
  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + maskId).getBytes(CHARSET));
  }
}
