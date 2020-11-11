package com.aotuman.studydemo.richtextview;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.aotuman.studydemo.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RichTextActivity extends AppCompatActivity {
    private TextView mIconSpan;
    private TextView mMultiIconSpan;
    private TextView mVerticalCenterIcon;
    private TextView mUrlSpan;
    private TextView mForegroundColorSpan;
    private TextView mBackgroundColorSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richtextview);
        mIconSpan = findViewById(R.id.tv1);
        mMultiIconSpan = findViewById(R.id.tv2);
        mVerticalCenterIcon = findViewById(R.id.tv3);
        mUrlSpan = findViewById(R.id.tv4);
        mForegroundColorSpan = findViewById(R.id.tv5);
        mBackgroundColorSpan = findViewById(R.id.tv6);
        setIconSpan(mIconSpan.getText());
        setMultiIconSpan(mMultiIconSpan.getText());
        setVerticalCenterIconSpan(mVerticalCenterIcon.getText());
        setUrlSpan(mUrlSpan.getText());
        setForegroundColorSpan(mForegroundColorSpan.getText());
        setBackgroundColorSpan(mBackgroundColorSpan.getText());
        testRxJava();
    }

    private void setIconSpan(CharSequence charSequence) {

        String text = "[icon] " + charSequence;
        SpannableString spannable = new SpannableString(text);
        Drawable drawable = this.getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        //第一个参数drawable 第二个参数对齐方式
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(imageSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mIconSpan.setText(spannable);
    }

    private void setMultiIconSpan(CharSequence charSequence) {
        String text = "[icon] ";
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        Drawable drawable1 = this.getResources().getDrawable(R.mipmap.ic_launcher);
        drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(), drawable1.getIntrinsicHeight());
        ImageSpan imageSpan1 = new ImageSpan(drawable1, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(imageSpan1, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //追加一个icon
        spannable.append("[icon] " + charSequence);
        Drawable drawable2 = this.getResources().getDrawable(R.mipmap.ic_launcher);
        drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable1.getIntrinsicHeight());
        ImageSpan imageSpan2 = new ImageSpan(drawable2, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(imageSpan2, 7, 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mMultiIconSpan.setText(spannable);

    }

    private void setVerticalCenterIconSpan(CharSequence charSequence) {
        String text = "[icon] " + charSequence;
        SpannableString spannable = new SpannableString(text);
        Drawable drawable = this.getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        //图片居中
        CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(imageSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mVerticalCenterIcon.setText(spannable);
    }

    private void setUrlSpan(CharSequence charSequence) {
        SpannableString spannableString = new SpannableString(charSequence);
        //URLSpan
        URLSpan urlSpan = new URLSpan("https://www.jianshu.com/u/9006081639f4");
        spannableString.setSpan(urlSpan, 4, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mUrlSpan.setMovementMethod(LinkMovementMethod.getInstance());
        mUrlSpan.setHighlightColor(ContextCompat.getColor(this, R.color.colorAccent));
        mUrlSpan.setText(spannableString);
    }

    private void setForegroundColorSpan(CharSequence charSequence) {
        SpannableString spannableString = new SpannableString(charSequence);
        //ForegroundColorSpan
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        spannableString.setSpan(colorSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mForegroundColorSpan.setText(spannableString);
    }

    private void setBackgroundColorSpan(CharSequence charSequence) {
        SpannableString spannableString = new SpannableString(charSequence);
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent));
        spannableString.setSpan(colorSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mBackgroundColorSpan.setText(spannableString);
    }

    private void testRxJava() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("jbjb", "onSubscribe: 告诉观察者已经成功订阅了被观察者");
            }
            @Override
            public void onNext(String s) {
                Log.d("jbjb", "onNext : " + s);
            }
            @Override
            public void onError(Throwable e) {
                Log.d("jbjb", "onError : " + e.toString());
            }
            @Override
            public void onComplete() {
                Log.d("jbjb", "onComplete");
            }
        });
    }
}
