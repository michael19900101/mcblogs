package com.aotuman.studydemo.statelayout;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.aotuman.statelayout.anim.ViewAnimProvider;

/**
 * <pre>
 *     time   : 2020/03/13
 *     desc   : 左进右出动画
 *     version: 1.0
 * </pre>
 */

public class CustomViewAnimProvider implements ViewAnimProvider {

    public Animation showAnimation() {
        AnimationSet set = new AnimationSet(false);
        Animation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,-1f,Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
        set.addAnimation(translateAnimation);
        set.setDuration(200);
        return set;
    }

    @Override
    public Animation hideAnimation() {
        AnimationSet set = new AnimationSet(false);
        Animation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,1f,
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
        set.addAnimation(translateAnimation);
        set.setDuration(200);
        return set;
    }
}
