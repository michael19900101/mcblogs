package com.aotuman.studydemo.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.text.TextUtilsCompat;

import com.aotuman.studydemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * 流式布局，可以自定义最大显示行数，并且在最后一行的最后一个位置放置showMoreView
 * 下标为0的是showMoreView
 * 下标为1开始的是动态添加的View
 */
public class FlowLayout extends ViewGroup {

    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;

    // 存储每一行所有的childView
    protected List<List<View>> mAllViews = new ArrayList<>();
    protected List<Integer> mLineHeight = new ArrayList<>();
    protected List<Integer> mLineWidth = new ArrayList<>();
    private int mGravity;
    private List<View> lineViews = new ArrayList<>();

    private int maxLine;
    private View showMoreView;

    private int showMoreViewWidth;
    private int showMoreViewHeight;
    int maxDrawChildIndex; // 最后一个绘制的View的下标

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        mGravity = ta.getInt(R.styleable.TagFlowLayout_tag_gravity, LEFT);
        maxLine = ta.getInt(R.styleable.TagFlowLayout_max_line, 4);

        int showMoreViewId = ta.getResourceId(R.styleable.TagFlowLayout_show_more_view, R.layout.item_layout_objpicker_showmore);
        showMoreView = LayoutInflater.from(context).inflate(showMoreViewId,null);
        addView(showMoreView);
        int layoutDirection = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault());
        if (layoutDirection == LayoutDirection.RTL) {
            if (mGravity == LEFT) {
                mGravity = RIGHT;
            } else {
                mGravity = LEFT;
            }
        }
        ta.recycle();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        showMoreView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

        showMoreViewWidth = showMoreView.getMeasuredWidth();

        showMoreViewHeight = showMoreView.getMeasuredHeight();

        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;
        /**
         * 记录每一行的宽度，width不断取最大宽度
         */
        int lineWidth = 0;
        /**
         * 每一行的高度，累加至height
         */
        int lineHeight = 0;

        int currentLine = 1;

        int cCount = getChildCount();
        // 遍历每个子元素 (i = 0: showMoreView )
        for (int i = 1; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                if (i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
                continue;
            }
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到child的lp
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            // 当前子空间实际占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;

            // 当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;

            if (currentLine <= maxLine) {
                /**
                 * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
                 */
                if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                    width = Math.max(width, lineWidth);// 取最大的
                    lineWidth = childWidth; // 重新开启新行，开始记录
                    // 叠加当前高度
                    height += lineHeight;
                    // 开启记录下一行的高度
                    lineHeight = childHeight;
                    currentLine++;

                    // 到达最大显示行数，把i之后的view都拿出来，加上showMoreView的宽度，看是否能放得下
                    if (currentLine == maxLine) {
                        int currentLastRowWidth = 0;
                        int currentLastRowHeight = 0;
                        for (int index = i; index < getChildCount(); index++) {
                            View childView = getChildAt(index);
                            boolean flag = canPutViewInLastRow(childView, currentLastRowWidth, widthMeasureSpec, heightMeasureSpec);
                            if (!flag) {
                                width = Math.max(currentLastRowWidth, width);
                                height += currentLastRowHeight;

                                // 去掉多余的子view
                                if (getChildCount() > maxDrawChildIndex) {
                                    try {
                                        removeViews(maxDrawChildIndex+1, getChildCount()-maxDrawChildIndex-1);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                setMeasuredDimension(
                                        modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                                        modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom());
                                return;
                            }

                            int childViewWidth = childView.getMeasuredWidth() +
                                    ((MarginLayoutParams) childView.getLayoutParams()).leftMargin +
                                    ((MarginLayoutParams) childView.getLayoutParams()).rightMargin;

                            int childViewHeight =  child.getMeasuredHeight() +
                                    ((MarginLayoutParams) childView.getLayoutParams()).topMargin +
                                    ((MarginLayoutParams) childView.getLayoutParams()).bottomMargin;

                            currentLastRowWidth += childViewWidth;

                            currentLastRowHeight = Math.max(currentLastRowHeight, childViewHeight);
                            maxDrawChildIndex = index;
                        }
                    }
                } else {
                    // 否则累加值lineWidth,lineHeight取最大高度
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight, childHeight);
                }
                // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
                if (i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                    maxDrawChildIndex = i;
                }
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );

    }

    /**
     * 测量子View的宽高
     * @param child
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * @return
     */
    private PointF measureChildWH(View child, int widthMeasureSpec, int heightMeasureSpec) {
        // 测量每一个child的宽和高
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        // 得到child的lp
        MarginLayoutParams lp = (MarginLayoutParams) child
                .getLayoutParams();

        // 当前子空间实际占据的宽度
        int childWidth = child.getMeasuredWidth() + lp.leftMargin
                + lp.rightMargin;

        // 当前子空间实际占据的高度
        int childHeight = child.getMeasuredHeight() + lp.topMargin
                + lp.bottomMargin;

        return new PointF(childWidth, childHeight);
    }


    private boolean canPutViewInLastRow(View child, int lineWidth, int widthMeasureSpec, int heightMeasureSpec) {
        PointF pointF = measureChildWH(child, widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childWidth = (int) pointF.x;
        MarginLayoutParams lp = (MarginLayoutParams) showMoreView.getLayoutParams();
        if (lineWidth + childWidth + showMoreViewHeight + lp.leftMargin + lp.rightMargin >
                sizeWidth - getPaddingLeft() - getPaddingRight()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        mLineWidth.clear();
        lineViews.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        // 只有showMoreView
        if (getChildCount() == 1) return;

        // (i = 0: showMoreView )
        for (int i = 1; i <= maxDrawChildIndex; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) continue;
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果已经需要换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                mLineWidth.add(lineWidth);

                // 重置行宽
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                lineViews = new ArrayList<>();
            }
            /**
             * 如果不需要换行，则累加
             */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);

        }
        // 记录最后一行
        mLineHeight.add(lineHeight);
        mLineWidth.add(lineWidth);
        mAllViews.add(lineViews);

        int left = getPaddingLeft();
        int top = getPaddingTop();

        // 得到总行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            // 每一行所有的views
            lineViews = mAllViews.get(i);
            // 当前行的最大高度
            lineHeight = mLineHeight.get(i);

            // set gravity
            int currentLineWidth = this.mLineWidth.get(i);
            switch (this.mGravity) {
                case LEFT:
                    left = getPaddingLeft();
                    break;
                case CENTER:
                    left = (width - currentLineWidth) / 2 + getPaddingLeft();
                    break;
                case RIGHT:
                    //  适配了rtl，需要补偿一个padding值
                    left = width - (currentLineWidth + getPaddingLeft()) - getPaddingRight();
                    //  适配了rtl，需要把lineViews里面的数组倒序排
                    Collections.reverse(lineViews);
                    break;
            }

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin
                        + lp.rightMargin;
                // 最后位置添加一个showMoreView
                if (i == maxLine - 1 && j == lineViews.size() - 1) {
                    int moreViewLeft = left + ((MarginLayoutParams) showMoreView.getLayoutParams()).leftMargin;
                    int moreViewTop = top + ((MarginLayoutParams) showMoreView.getLayoutParams()).topMargin;
                    int moreViewRight = moreViewLeft + showMoreView.getMeasuredWidth();
                    int moreViewBottom = moreViewTop + showMoreView.getMeasuredHeight();
                    showMoreView.layout(moreViewLeft, moreViewTop, moreViewRight, moreViewBottom);
                }
            }
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public void addView(View child) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        super.addView(child,lp);
    }

    public View getShowMoreView() {
        return showMoreView;
    }
}