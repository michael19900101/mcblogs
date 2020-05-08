package com.aotuman.studydemo.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IntDef;

import com.aotuman.studydemo.R;
import com.aotuman.studydemo.utils.Utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class FormBaseViewGroup extends ViewGroup {

    View contentView;
    View errorLineView;
    View bottomLineView;
    FormBaseLabelView titleView;
    TextView errorTextView;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int PLAIN = 0;
    public static final int CARD = 1;
    public static final int FREE = 2;
    public static final int GRID = 3;
    public static final int COMPACT = 4;
    public static final int BASIC = 5;
    private static final int ERROR_TEXT_SIZE = 9;
    private static final int ERROR_LINE_WIDTH = Utils.dp2px(5);
    private static final int BOTTOM_LINE_HEIGHT = Utils.dp2px(0.5f);
    private static final int ERROR_TEXT_HEIGHT = Utils.dp2px(12f);
    private static final String BOTTOM_LINE_COLOR = "#DEDFE0";
    private static final String FREE_MODE_BG_COLOR = "#EEEEEE";
    private int errorTextViewWidth = 0;
    private int errorTextViewHeight = 0;
    private int errorLineViewWidth = ERROR_LINE_WIDTH;
    private int bottomLineViewHeight = 0;
    private int titleViewHeight = 0;
    private int titleViewWidth = 0;
    private int contentViewWidth = 0;
    private int contentViewHeight = 0;
    private int paddingLeft = Utils.dp2px(12);
    private int paddingRight = Utils.dp2px(12);
    private int paddingTop = Utils.dp2px(12);
    // 现在paddingtop 和 errortextheight 高度一样，内容以及达到竖直方向居中效果，paddingBottom暂时不用设置
    private int paddingBottom = 0;
    private int orientation;
    private int dispalyMode;
    private float titleFlex = 0.33f;

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {}

    @IntDef({PLAIN, CARD, FREE, GRID, COMPACT, BASIC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayMode{}

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

        dispalyMode = setDisplayMode();
        orientation = setOrientation();
        contentView = initContentView(context);
        // 给contentView设置边距
        contentView.setPadding(Utils.dp2px(10), 0, 0, 0);
        setAttribute(dispalyMode);
        if (contentView != null) {
            addView(contentView);
        }
    }

    protected abstract @DisplayMode int setDisplayMode();

    protected abstract @OrientationMode int setOrientation();

    public void setOrientation(@OrientationMode int orientation) {
        if (this.orientation != orientation) {
            this.orientation = orientation;
            requestLayout();
        }
    }

    public void setDispalyMode(@DisplayMode int dispalyMode) {
        if (this.dispalyMode != dispalyMode) {
            this.dispalyMode = dispalyMode;
            setAttribute(dispalyMode);
            requestLayout();
        }
    }

    protected abstract View initContentView(Context context);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (bottomLineView.getVisibility() == VISIBLE) {
            bottomLineViewHeight = BOTTOM_LINE_HEIGHT;
        } else {
            bottomLineViewHeight = 0;
        }
        if (orientation == HORIZONTAL) {
            titleView.setMaxLine(Integer.MAX_VALUE);
            if (errorTextView.getVisibility() == VISIBLE) {
                errorTextViewWidth = parentWidth - paddingLeft - errorLineViewWidth - paddingRight;
                errorTextViewHeight = ERROR_TEXT_HEIGHT;
            } else {
                errorTextViewWidth = 0;
                errorTextViewHeight = ERROR_TEXT_HEIGHT;
            }
        } else {
            if (errorTextView.getVisibility() == VISIBLE) {
                errorTextViewWidth = parentWidth - paddingLeft - errorLineViewWidth - paddingRight;
                errorTextViewHeight = ERROR_TEXT_HEIGHT;
            } else {
                errorTextViewWidth = 0;
                errorTextViewHeight = ERROR_TEXT_HEIGHT;
            }
        }

        if (orientation == VERTICAL) {
            contentViewWidth = parentWidth - errorLineViewWidth - paddingLeft - paddingRight;
            titleViewWidth = parentWidth - errorLineViewWidth - paddingLeft - paddingRight;
            measureChild(errorLineView,  MeasureSpec.makeMeasureSpec(errorLineViewWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            measureChild(titleView, MeasureSpec.makeMeasureSpec(titleViewWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            measureChild(errorTextView, MeasureSpec.makeMeasureSpec(errorTextViewWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            if (contentView != null) {
                measureChild(contentView, MeasureSpec.makeMeasureSpec(contentViewWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            }
        } else {
            // 根据titleflex算出 titleView 和contentView各自的宽度
            int remainWidth = parentWidth - paddingLeft - paddingRight - errorLineViewWidth;
            titleViewWidth = (int) (remainWidth * titleFlex);
            contentViewWidth = remainWidth - titleViewWidth;
            measureChild(errorLineView, MeasureSpec.makeMeasureSpec(errorLineViewWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            measureChild(titleView, MeasureSpec.makeMeasureSpec(titleViewWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            measureChild(errorTextView, MeasureSpec.makeMeasureSpec(errorTextViewWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            if (contentView != null) {
                measureChild(contentView, MeasureSpec.makeMeasureSpec(contentViewWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            }
        }

        if (titleView.getVisibility() == VISIBLE) {
            titleViewHeight = titleView.getMeasuredHeight();
        } else {
            titleViewWidth = 0;
            titleViewHeight = 0;
        }
        if (contentView != null) {
            contentViewHeight = contentView.getMeasuredHeight();
        } else {
            contentViewWidth = 0;
            contentViewHeight = 0;
        }
        if (orientation == HORIZONTAL) {
            if (errorTextView.getVisibility() == VISIBLE) {
                errorTextViewWidth = errorTextView.getMeasuredWidth();
            } else {
                errorTextViewWidth = 0;;
            }
        } else {
            if (errorTextView.getVisibility() == VISIBLE) {
                errorTextViewWidth = errorTextView.getMeasuredWidth();
            } else {
                errorTextViewWidth = 0;
            }
        }

        int totalHeight;
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
                    l,
                    b - bottomLineViewHeight - paddingBottom,
                    r,
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
                        b - bottomLineViewHeight - paddingBottom - errorTextViewHeight);
            }
        } else {
            // 根据titleflex算出 titleView 和contentView各自的宽度
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
                    l,
                    b - bottomLineViewHeight - paddingBottom,
                    r,
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
                        b - bottomLineViewHeight - paddingBottom - errorTextViewHeight);
            }
        }
    }

    private void setAttribute(@DisplayMode int dispalyMode) {
        switch (dispalyMode) {
            case FREE:
                paddingLeft = 0;
                paddingRight = Utils.dp2px(12);
                setBackgroundColor(Color.parseColor(FREE_MODE_BG_COLOR));
                if(contentView != null) {
                    contentView.setBackgroundResource(R.drawable.uimode_bg);
                }
                bottomLineView.setVisibility(GONE);
                break;
            default:
                paddingLeft = 0;
                paddingRight = Utils.dp2px(12);
                setBackgroundColor(Color.WHITE);
                break;
        }

    }
}
