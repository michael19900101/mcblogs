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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aotuman.studydemo.R;

public class EllipseActivity extends AppCompatActivity {

    final static String TEXT = "2015年，江西男童范小勤因酷似马云走红。一名村民从外地回家时，发现范小勤和阿里巴巴总裁马云竟然长得十分相像。" +
            "他把将范小勤的照片上传至网络，“小马云”顿时成为了网红，成为网友们的谈资。" +
            "马云还曾调侃称：乍一看到这小子，还以为是家里人上传了我小时候的照片，这英武的神态，我真的感觉自己是在照镜子啊。" +
            "之后有报道称，他被人资助到石家庄上学，本应在学校的他已有大半年没有露面。不过范小勤三年级上学期没来，" +
            "下学期也没来，其保姆的答复是因为有疫情，不能够上学。" +
            "抖音账号显示，他被人带着在广东等地做美食直播。也就是说，如今的范小勤根本就没有在上学而是被保姆带着各地去录制视频赚钱，成为一个“工具人”。" +
            "企查查APP显示，背后推手刘长江为范小勤爸爸（范家发）成立小马总（北京）商贸有限公司和江西小马总文化传媒有限公司两家公司。" +
            "目前小马总（北京）商贸有限公司申请注册多个小马总商标，耐人寻味的是，该公司作品著作权包含“让山区不再有贫困和受苦的孩子”、“让全天下不再有贫困和受苦的孩子”、小总裁艺术LOGO等内容。";
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
        ((EllipsingTextView)tvEnd).setShowMoreText("显示更多");
        ((EllipsingTextView)tvEnd).addEllipsizeListener(new EllipsingTextView.EllipsizeListener() {
            @Override
            public void ellipsizeStateChanged(boolean ellipsized) {
                Log.e("jbjb", String.valueOf(ellipsized));
            }
        });
        ((EllipsingTextView)tvEnd).setShowMoreListener(new EllipsingTextView.ShowMoreListener() {
            @Override
            public void onClick() {
                Toast.makeText(EllipseActivity.this,"发生了点击效果",Toast.LENGTH_SHORT).show();
                ((EllipsingTextView) tvEnd).showFullText(TEXT);
            }
        });
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                tvEnd.setText("近日，四川甘孜20岁藏族小伙丁");
//                tvEnd.setText("jbjb:"+TEXT);
//            }
//        },5000);


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
