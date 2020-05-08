package com.aotuman.studydemo.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IntDef
import com.aotuman.studydemo.R
import com.aotuman.studydemo.utils.Utils
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * 基类布局(包含标题，错误提示，底部分割线)
 */
abstract class FormBaseViewGroup : ViewGroup {
    var contentView: View? = null
    lateinit var errorLineView: View
    lateinit var bottomLineView: View
    lateinit var titleView: FormBaseLabelView
    lateinit var errorTextView: TextView
    private var errorTextViewWidth = 0
    private var errorTextViewHeight = 0
    private val errorLineViewWidth = ERROR_LINE_WIDTH
    private var bottomLineViewHeight = 0
    private var titleViewHeight = 0
    private var titleViewWidth = 0
    private var contentViewWidth = 0
    private var contentViewHeight = 0
    private var viewGroupPaddingLeft = Utils.dp2px(12f)
    private var viewGroupPaddingRight = Utils.dp2px(12f)
    private val viewGroupPaddingTop = Utils.dp2px(12f)
    // 现在paddingtop 和 errortextheight 高度一样，内容以及达到竖直方向居中效果，paddingBottom暂时不用设置
    private val viewGroupPaddingBottom = 0
    private var orientation = 0
    private var mode = 0
    private var titleFlex = 0.33f

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        const val PLAIN = 0
        const val CARD = 1
        const val FREE = 2
        const val GRID = 3
        const val COMPACT = 4
        const val BASIC = 5
        private const val ERROR_TEXT_SIZE = 9
        private val ERROR_LINE_WIDTH = Utils.dp2px(5f)
        private val BOTTOM_LINE_HEIGHT = Utils.dp2px(0.5f)
        private val ERROR_TEXT_HEIGHT = Utils.dp2px(12f)
        private const val BOTTOM_LINE_COLOR = "#DEDFE0"
        private const val FREE_MODE_BG_COLOR = "#EEEEEE"
    }

    @IntDef(HORIZONTAL, VERTICAL)
    @Retention(RetentionPolicy.SOURCE)
    annotation class OrientationMode

    @IntDef(
        PLAIN,
        CARD,
        FREE,
        GRID,
        COMPACT,
        BASIC
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class Mode {}

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        initView(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(context)
    }

    private fun initView(context: Context) {
        errorLineView = View(context)
        errorLineView.setBackgroundColor(Color.RED)
        addView(errorLineView)

        errorTextView = TextView(context)
        errorTextView.textSize = ERROR_TEXT_SIZE.toFloat()
        errorTextView.setTextColor(Color.RED)
        errorTextView.gravity = Gravity.RIGHT
        addView(errorTextView)

        bottomLineView = View(context)
        bottomLineView.setBackgroundColor(Color.parseColor(BOTTOM_LINE_COLOR))
        addView(bottomLineView)

        titleView = FormBaseLabelView(context)
        titleView.layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        addView(titleView)

        mode = setMode()
        orientation = setOrientation()
        contentView = initContentView(context)
        // 给contentView设置边距
        contentView?.setPadding(Utils.dp2px(10f), 0, 0, 0)
        contentView?.let {  addView(contentView) }
        setAttribute(mode)
    }

    @Mode
    protected abstract fun setMode(): Int

    @OrientationMode
    protected abstract fun setOrientation(): Int
    fun setOrientation(@OrientationMode orientation: Int) {
        if (this.orientation != orientation) {
            this.orientation = orientation
            requestLayout()
        }
    }

    fun setMode(@Mode mode: Int) {
        if (this.mode != mode) {
            this.mode = mode
            setAttribute(mode)
            requestLayout()
        }
    }

    protected abstract fun initContentView(context: Context?): View?

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        bottomLineViewHeight = if (bottomLineView.visibility == View.VISIBLE)  BOTTOM_LINE_HEIGHT else 0
        // 1.根据水平方向或者竖直方向测量子View
        if (orientation == HORIZONTAL) {
            titleView.setMaxLine(Int.MAX_VALUE)
            if (errorTextView.visibility == View.VISIBLE) {
                errorTextViewWidth = parentWidth - viewGroupPaddingLeft - errorLineViewWidth - viewGroupPaddingRight
                errorTextViewHeight = ERROR_TEXT_HEIGHT
            } else {
                errorTextViewWidth = 0
                errorTextViewHeight = ERROR_TEXT_HEIGHT
            }
            // 根据titleflex算出 titleView 和contentView各自的宽度
            val remainWidth = parentWidth - viewGroupPaddingLeft - viewGroupPaddingRight - errorLineViewWidth
            titleViewWidth = (remainWidth * titleFlex).toInt()
            contentViewWidth = remainWidth - titleViewWidth
            measureChild(
                errorLineView,
                MeasureSpec.makeMeasureSpec(errorLineViewWidth, MeasureSpec.EXACTLY),
                heightMeasureSpec
            )
            measureChild(
                titleView,
                MeasureSpec.makeMeasureSpec(titleViewWidth, MeasureSpec.EXACTLY),
                heightMeasureSpec
            )
            measureChild(
                errorTextView,
                MeasureSpec.makeMeasureSpec(errorTextViewWidth, MeasureSpec.EXACTLY),
                heightMeasureSpec
            )
            contentView?.let {
                measureChild(
                    it,
                    MeasureSpec.makeMeasureSpec(contentViewWidth, MeasureSpec.EXACTLY),
                    heightMeasureSpec
                )
            }
        } else {
            if (errorTextView.visibility == View.VISIBLE) {
                errorTextViewWidth = parentWidth - viewGroupPaddingLeft - errorLineViewWidth - viewGroupPaddingRight
                errorTextViewHeight = ERROR_TEXT_HEIGHT
            } else {
                errorTextViewWidth = 0
                errorTextViewHeight = ERROR_TEXT_HEIGHT
            }
            contentViewWidth = parentWidth - errorLineViewWidth - viewGroupPaddingLeft - viewGroupPaddingRight
            titleViewWidth = parentWidth - errorLineViewWidth - viewGroupPaddingLeft - viewGroupPaddingRight
            measureChild(
                errorLineView,
                MeasureSpec.makeMeasureSpec(errorLineViewWidth, MeasureSpec.EXACTLY),
                heightMeasureSpec
            )
            measureChild(
                titleView,
                MeasureSpec.makeMeasureSpec(titleViewWidth, MeasureSpec.EXACTLY),
                heightMeasureSpec
            )
            measureChild(
                errorTextView,
                MeasureSpec.makeMeasureSpec(errorTextViewWidth, MeasureSpec.EXACTLY),
                heightMeasureSpec
            )
            contentView?.let {
                measureChild(
                    it,
                    MeasureSpec.makeMeasureSpec(contentViewWidth, MeasureSpec.EXACTLY),
                    heightMeasureSpec
                )
            }
        }

        // 2.子View获取测量后的宽高
        if (titleView.visibility == View.VISIBLE) {
            titleViewHeight = titleView.measuredHeight
        } else {
            titleViewWidth = 0
            titleViewHeight = 0
        }
        if (contentView != null) {
            contentViewHeight = contentView!!.measuredHeight
        } else {
            contentViewWidth = 0
            contentViewHeight = 0
        }
        errorTextViewWidth = if (errorTextView.visibility == View.VISIBLE)  errorTextView.measuredWidth else 0

        // GRID模式错误提示文字高度不能固定，其他模式都是固定错误文字高度
        if (mode == GRID) {
            errorTextViewHeight = if (errorTextView.visibility == View.VISIBLE)  errorTextView.measuredHeight else 0
        }

        // 3.viewgroup计算子view的总高度
        val totalHeight = if (orientation == HORIZONTAL) {
            val maxHeight = titleViewHeight.coerceAtLeast(contentViewHeight)
            (maxHeight + bottomLineViewHeight + errorTextViewHeight
                    + viewGroupPaddingTop + viewGroupPaddingBottom)
        } else {
            (titleViewHeight + contentViewHeight + bottomLineViewHeight + errorTextViewHeight
                    + viewGroupPaddingTop + viewGroupPaddingBottom)
        }
        // 4.给viewgroup设置宽高
        setMeasuredDimension(
            View.getDefaultSize(
                suggestedMinimumWidth,
                widthMeasureSpec
            ), totalHeight
        )
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        if (orientation == VERTICAL) {
            errorLineView.layout(
                l + viewGroupPaddingLeft,
                t,
                l + viewGroupPaddingLeft + errorLineViewWidth,
                b - bottomLineViewHeight
            )
            bottomLineView.layout(
                l,
                b - bottomLineViewHeight - viewGroupPaddingBottom,
                r,
                b - viewGroupPaddingBottom
            )
            titleView.layout(
                l + viewGroupPaddingLeft + errorLineViewWidth,
                t + viewGroupPaddingTop,
                l + viewGroupPaddingLeft + errorLineViewWidth + titleViewWidth,
                t + viewGroupPaddingTop + titleViewHeight
            )
            errorTextView.layout(
                r - viewGroupPaddingRight - errorTextViewWidth,
                b - bottomLineViewHeight - viewGroupPaddingBottom - errorTextViewHeight,
                r - viewGroupPaddingRight,
                b - bottomLineViewHeight - viewGroupPaddingBottom
            )
            contentView?.layout(
                l + viewGroupPaddingLeft + errorLineViewWidth,
                t + viewGroupPaddingTop + titleViewHeight,
                r - viewGroupPaddingRight,
                b - bottomLineViewHeight - viewGroupPaddingBottom - errorTextViewHeight
            )
        } else {
            // 根据titleflex算出 titleView 和contentView各自的宽度
            val remainWidth = r - l - viewGroupPaddingLeft - viewGroupPaddingRight - errorLineViewWidth
            val titleViewWidth = (remainWidth * titleFlex).toInt()

            // titleView 竖直方向居中显示
            val maxHeight = titleViewHeight.coerceAtLeast(contentViewHeight)
            errorLineView.layout(
                l + viewGroupPaddingLeft,
                t,
                l + viewGroupPaddingLeft + errorLineViewWidth,
                b - bottomLineViewHeight
            )
            bottomLineView.layout(
                l,
                b - bottomLineViewHeight - viewGroupPaddingBottom,
                r,
                b - viewGroupPaddingBottom
            )
            titleView.layout(
                l + viewGroupPaddingLeft + errorLineViewWidth,
                t + viewGroupPaddingTop + (maxHeight - titleViewHeight) / 2,
                l + viewGroupPaddingLeft + errorLineViewWidth + titleViewWidth,
                t + viewGroupPaddingTop + +(maxHeight - titleViewHeight) / 2 + titleViewHeight
            )
            errorTextView.layout(
                r - viewGroupPaddingRight - errorTextViewWidth,
                b - bottomLineViewHeight - viewGroupPaddingBottom - errorTextViewHeight,
                r - viewGroupPaddingRight,
                b - bottomLineViewHeight - viewGroupPaddingBottom
            )
            contentView?.layout(
                l + viewGroupPaddingLeft + errorLineViewWidth + titleViewWidth,
                t + viewGroupPaddingTop,
                r - viewGroupPaddingRight,
                b - bottomLineViewHeight - viewGroupPaddingBottom - errorTextViewHeight
            )
        }
    }

    private fun setAttribute(@Mode mode: Int) {
        when (mode) {
            FREE -> {
                viewGroupPaddingLeft = 0
                viewGroupPaddingRight = Utils.dp2px(12f)
                setBackgroundColor(Color.parseColor(FREE_MODE_BG_COLOR))
                contentView?.setBackgroundResource(R.drawable.uimode_bg)
                bottomLineView.visibility = View.GONE
            }
            BASIC -> {
                viewGroupPaddingLeft = 0
                viewGroupPaddingRight = 0
                titleFlex = 0f
                orientation = HORIZONTAL
                setBackgroundColor(Color.WHITE)
                titleView.visibility = View.GONE
                errorLineView.visibility = View.GONE
                errorTextView.visibility = View.GONE
                bottomLineView.visibility = View.GONE
            }
            GRID -> {
                viewGroupPaddingLeft = 0
                viewGroupPaddingRight = 0
                titleFlex = 0f
                orientation = HORIZONTAL
                setBackgroundColor(Color.WHITE)
                titleView.visibility = View.GONE
                errorLineView.visibility = View.GONE
                bottomLineView.visibility = View.GONE
            }
            else -> {
                viewGroupPaddingLeft = 0
                viewGroupPaddingRight = Utils.dp2px(12f)
                setBackgroundColor(Color.WHITE)
            }
        }
    }
}