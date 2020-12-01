package com.aotuman.studydemo.ellispetextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;

import com.aotuman.studydemo.R;

import java.util.ArrayList;
import java.util.List;

public class EllipsizingTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static String ELLIPSIS;
    private final List<EllipsizeListener> ellipsizeListeners = new ArrayList<EllipsizeListener>();
    private String blankText = " ";
    private String sumText = "共101项";
    private boolean isEllipsized;
    private boolean isStale = true;
    private boolean programmaticChange;
    private String fullText;
    private float lineSpacingMultiplier = 1.0f;
    private float lineAdditionalVerticalPadding = 0.0f;
    public EllipsizingTextView(Context context) {
        super(context);
        ELLIPSIS = context.getString(R.string.ellipsis);
    }

    public EllipsizingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ELLIPSIS = context.getString(R.string.ellipsis);
    }

    public EllipsizingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ELLIPSIS = context.getString(R.string.ellipsis);
    }

    public void addEllipsizeListener(EllipsizeListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        ellipsizeListeners.add(listener);
    }

    public void removeEllipsizeListener(EllipsizeListener listener) {
        ellipsizeListeners.remove(listener);
    }

    public boolean isEllipsized() {
        return isEllipsized;
    }

    @Override
    public void setLineSpacing(float add, float mult) {
        this.lineAdditionalVerticalPadding = add;
        this.lineSpacingMultiplier = mult;
        super.setLineSpacing(add, mult);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        if (!programmaticChange) {
            fullText = text.toString();
            isStale = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isStale) {
            resetText();
        }
        super.onDraw(canvas);
    }

//    public int getMaxLines() {
//        Class<TextView> textViewClassInstance = TextView.class;
//        try {
//            Field MaxMode = textViewClassInstance.getDeclaredField("mMaxMode");
//            MaxMode.setAccessible(true);
//            int mMaxMode = MaxMode.getInt(this);
//            Field Maximum = textViewClassInstance.getDeclaredField("mMaximum");
//            Maximum.setAccessible(true);
//            int mMaximum = Maximum.getInt(this);
//            Field LINES = textViewClassInstance.getDeclaredField("LINES");
//            LINES.setAccessible(true);
//            int mLINES = LINES.getInt(this);
//            return mMaxMode == mLINES ? mMaximum : -1;
//        } catch (NoSuchFieldException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return -1;
//    }
private void resetText() {
    int maxLines = getMaxLines();
    SpannableString sss = new SpannableString(fullText);
    String workingText = fullText;
    boolean ellipsized = false;
    if (maxLines != -1) {
        Layout layout = createWorkingLayout(workingText);
        int originalLineCount = layout.getLineCount();
        if (originalLineCount > maxLines) {
            workingText = fullText.substring(0, layout.getLineEnd(maxLines - 1)).trim();
            while (createWorkingLayout(workingText + ELLIPSIS + blankText + sumText).getLineCount() > maxLines) {
                int lastSpace = workingText.lastIndexOf(' ');
                if (lastSpace == -1) {
                    workingText = workingText.substring(0, workingText.length() - 1);
                } else {
                    workingText = workingText.substring(0, lastSpace);
                }
            }
            workingText = workingText + ELLIPSIS + blankText + sumText;

            sss = new SpannableString(workingText);

            sss.setSpan(new MyClickText(getContext()),workingText.length() - sumText.length() ,
                    workingText.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setMovementMethod(LinkMovementMethod.getInstance()); // 不设置 没有点击事件
            setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
            ellipsized = true;
        }
    }

    if (!workingText.equals(getText())) {
        programmaticChange = true;
        try {
//            setText(workingText);
            setText(sss);
        } finally {
            programmaticChange = false;
        }
    }
    isStale = false;
    if (ellipsized != isEllipsized) {
        isEllipsized = ellipsized;
        for (EllipsizeListener listener : ellipsizeListeners) {
            listener.ellipsizeStateChanged(ellipsized);
        }
    }
}

    private Layout createWorkingLayout(String workingText) {
        StaticLayout staticLayout;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            final StaticLayout.Builder layoutBuilder = StaticLayout.Builder.obtain(
                    workingText, 0, workingText.length(),  getPaint(), Math.round(getWidth()));
            staticLayout = layoutBuilder.build();
        } else {
            staticLayout = new StaticLayout(workingText, getPaint(), getWidth() - getPaddingLeft() - getPaddingRight(),
                    Alignment.ALIGN_NORMAL, lineSpacingMultiplier, lineAdditionalVerticalPadding, false);
        }
        return staticLayout;
    }



    public interface EllipsizeListener {
        void ellipsizeStateChanged(boolean ellipsized);
    }

}

