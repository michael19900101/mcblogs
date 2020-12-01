package com.aotuman.studydemo.ellispetextview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aotuman.studydemo.R;

public class EllipseActivity extends AppCompatActivity {

    final static String TEXT = "近日，四川甘孜20岁藏族小伙丁真因为一脸纯真朴素的笑容意外走红网络，成为“新晋顶流”。" +
            "走红后的丁真与成为理塘县的旅游大使，为当地旅游贡献力量。"
            +"11月25日，丁真为家乡拍摄的宣传片《丁真的世界》正式上线，并火速席卷全网。";
    TextView tvFull, tvNone, tvStart, tvMiddle, tvEnd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ellipse);
        tvFull = (EllipsingTextView) findViewById(R.id.textViewFull);
        tvNone = (EllipsingTextView) findViewById(R.id.textViewNone);
        tvStart = (EllipsingTextView) findViewById(R.id.textViewStart);
        tvMiddle = (EllipsingTextView) findViewById(R.id.textViewMiddle);
        tvEnd = (EllipsingTextView) findViewById(R.id.textViewEnd);
        tvFull.setText(TEXT);
        tvNone.setText(TEXT);
        tvStart.setText(TEXT);
        tvMiddle.setText(TEXT);
        tvEnd.setText(TEXT);
        ((EllipsingTextView)tvEnd).addEllipsizeListener(new EllipsingTextView.EllipsizeListener() {
            @Override
            public void ellipsizeStateChanged(boolean ellipsized) {
                Log.e("jbjb", String.valueOf(ellipsized));
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvEnd.setText("近日，四川甘孜20岁藏族小伙丁");
            }
        },5000);


        TextView tv = findViewById(R.id.tv_test);
        tv.post(new Runnable() {
            @Override
            public void run() {
                if (tv.getVisibility() == View.GONE) return;
                StaticLayout layout = Utils.createStaticLayout(TEXT, tv);
                String endText = "共103项";
                String realText = Utils.getRealText(layout,tv,TEXT,endText,3);
                Log.e("jbjb",realText);
//                tv.setText(realText);

                SpannableString sss = new SpannableString(realText);
                sss.setSpan(new ForegroundColorSpan(Color.BLUE), realText.length() - endText.length() ,
                        realText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sss.setSpan(new MyClickText(EllipseActivity.this),realText.length() - endText.length() ,
                        realText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
                tv.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
                tv.setText(sss);

            }
        });

    }
}
