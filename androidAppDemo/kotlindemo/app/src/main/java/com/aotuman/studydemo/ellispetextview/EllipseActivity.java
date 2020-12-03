package com.aotuman.studydemo.ellispetextview;

import android.content.Context;
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
        ExpandableTextView expandableTextView = findViewById(R.id.expanded_text);
        int viewWidth = getWindowManager().getDefaultDisplay().getWidth() - dp2px(this, 20f);
//        expandableTextView.initWidth(viewWidth);
        expandableTextView.setMaxLines(1);
        expandableTextView.setHasAnimation(true);
        expandableTextView.setCloseInNewLine(false);
        expandableTextView.setOpenSuffix("点开展示更多");
        expandableTextView.setOpenSuffixColor(getResources().getColor(R.color.colorAccent));
        expandableTextView.setCloseSuffixColor(getResources().getColor(R.color.colorAccent));
        expandableTextView.setOriginalText("中华人民共和国中央人民政府仿小红书实现的文本展开/收起的功能");

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                expandableTextView.setOriginalText("jbjb:"+TEXT);
//            }
//        },5000);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        int res = 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        if (dpValue < 0)
            res = -(int) (-dpValue * scale + 0.5f);
        else
            res = (int) (dpValue * scale + 0.5f);
        return res;
    }
}
