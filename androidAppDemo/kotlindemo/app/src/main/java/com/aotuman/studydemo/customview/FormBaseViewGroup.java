package com.aotuman.studydemo.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aotuman.studydemo.utils.Utils;

public abstract class FormBaseViewGroup extends ViewGroup {

    View contentView;
    View errorLineView;
    View bottomLineView;
    FormBaseLabelView titleView;
    TextView errorTextView;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static final int ERROR_TEXT_SIZE = 12;
    private static final int ERROR_LINE_WIDTH = Utils.dp2px(5);
    private static final int BOTTOM_LINE_HEIGHT = Utils.dp2px(0.5f);
    private static final String BOTTOM_LINE_COLOR = "#DEDFE0";
    private int errorTextViewWidth = 0;
    private int errorTextViewHeight = 0;
    private int errorLineViewWidth = 0;
    private int bottomLineViewHeight = 0;
    private int titleViewHeight = 0;
    private int titleViewWidth = 0;
    private int contentViewHeight = 0;
    private int paddingLeft = Utils.dp2px(12);
    private int paddingRight = Utils.dp2px(12);
    private int paddingTop = Utils.dp2px(5);
    private int paddingBottom = 0;
    private int orientation;
    private float titleFlex = 0.33f;

    public FormBaseViewGroup(Context context) {
        super(context);
        initView(context);
    }

    public FormBaseViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FormBaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public FormBaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        errorLineView = new View(context);
        errorLineView.setBackgroundColor(Color.RED);
        addView(errorLineView);

        errorTextView = new TextView(context);
        errorTextView.setTextSize(ERROR_TEXT_SIZE);
        errorTextView.setTextColor(Color.RED);
        addView(errorTextView);

        bottomLineView = new View(context);
        bottomLineView.setBackgroundColor(Color.parseColor(BOTTOM_LINE_COLOR));
        addView(bottomLineView);

        titleView = new FormBaseLabelView(context);
        titleView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(titleView);

        orientation = setOrientation();
        contentView = initContentView(context);
        if (contentView != null) {
            addView(contentView);
        }
    }

    protected abstract int setOrientation();

    protected abstract View initContentView(Context context);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        if (contentView != null) {
            contentViewHeight = contentView.getMeasuredHeight();
        } else {
            contentViewHeight = 0;
        }
        if (errorLineView.getVisibility() == VISIBLE) {
            errorLineViewWidth = ERROR_LINE_WIDTH;
        } else {
            errorLineViewWidth = 0;
        }
        if (titleView.getVisibility() == VISIBLE) {
            titleViewWidth = titleView.getMeasuredWidth();
            titleViewHeight = titleView.getMeasuredHeight();
        } else {
            titleViewWidth = 0;
            titleViewHeight = 0;
        }
        if (bottomLineView.getVisibility() == VISIBLE) {
            bottomLineViewHeight = BOTTOM_LINE_HEIGHT;
        } else {
            bottomLineViewHeight = 0;
        }
        if (errorTextView.getVisibility() == VISIBLE) {
            errorTextViewWidth = errorTextView.getMeasuredWidth();
            errorTextViewHeight = errorTextView.getMeasuredHeight();
        } else {
            errorTextViewWidth = 0;
            errorTextViewHeight = 0;
        }
        int totalHeight = 0;
        if (orientation == VERTICAL) {
            totalHeight = titleViewHeight + contentViewHeight + bottomLineViewHeight + errorTextViewHeight
                    + paddingTop + paddingBottom;
        } else {
            int maxHeight = Math.max(titleViewHeight, contentViewHeight);
            totalHeight = maxHeight + bottomLineViewHeight + errorTextViewHeight
                    + paddingTop + paddingBottom;
        }
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (orientation == VERTICAL) {
            errorLineView.layout(
                    l + paddingLeft,
                    t,
                    l + paddingLeft + errorLineViewWidth,
                    b - bottomLineViewHeight);
            bottomLineView.layout(
                    l + paddingLeft,
                    b - bottomLineViewHeight - paddingBottom,
                    r - paddingRight,
                    b - paddingBottom);
            titleView.layout(
                    l + paddingLeft + errorLineViewWidth,
                    t + paddingTop,
                    l + paddingLeft + errorLineViewWidth + titleViewWidth,
                    t  + paddingTop + titleViewHeight);
            errorTextView.layout(
                    r - paddingRight - errorTextViewWidth,
                    b - bottomLineViewHeight - paddingBottom - errorTextViewHeight,
                    r - paddingRight,
                    b - bottomLineViewHeight - paddingBottom);
            if (contentView != null) {
                contentView.layout(
                        l + paddingLeft + errorLineViewWidth,
                        t  + paddingTop + titleViewHeight,
                        r - paddingRight,
                        b - bottomLineViewHeight - paddingBottom);
            }
        } else {
            // 根据titleflex算出 titleView 和contentView 各自的宽度
            int remainWidth = r - l - paddingLeft - paddingRight - errorLineViewWidth;
            int titleViewWidth = (int) (remainWidth * titleFlex);

            // titleView 竖直方向居中显示
            int maxHeight = Math.max(titleViewHeight, contentViewHeight);

            errorLineView.layout(
                    l + paddingLeft,
                    t,
                    l + paddingLeft + errorLineViewWidth,
                    b - bottomLineViewHeight);
            bottomLineView.layout(
                    l + paddingLeft,
                    b - bottomLineViewHeight - paddingBottom,
                    r - paddingRight,
                    b - paddingBottom);
            titleView.layout(
                    l + paddingLeft + errorLineViewWidth,
                    t + paddingTop + (maxHeight - titleViewHeight)/2,
                    l + paddingLeft + errorLineViewWidth + titleViewWidth,
                    t  + paddingTop + + (maxHeight - titleViewHeight)/2 + titleViewHeight
            );
            errorTextView.layout(
                    r - paddingRight - errorTextViewWidth,
                    b - bottomLineViewHeight - paddingBottom - errorTextViewHeight,
                    r - paddingRight,
                    b - bottomLineViewHeight - paddingBottom);
            if (contentView != null) {
                contentView.layout(
                        l + paddingLeft + errorLineViewWidth + titleViewWidth,
                        t  + paddingTop,
                        r - paddingRight,
                        b - bottomLineViewHeight - paddingBottom);
            }
        }
    }
}
