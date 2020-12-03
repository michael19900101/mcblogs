package com.aotuman.studydemo.ellispetextview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.aotuman.studydemo.R
import java.util.*
import kotlin.math.roundToInt

class EllipsingTextView : AppCompatTextView {
    private val ellipsizeListeners: MutableList<EllipsizeListener> = ArrayList()
    private val blankText = " "
    private var showMoreText = "共101项"
    var isEllipsized = false
        private set
    private var isStale = true
    private var programmaticChange = false
    private var fullText: String? = null
    private var lineSpacingMultiPier = 1.0f
    private var lineAdditionalVerticalPadding = 0.0f
    private var showMoreListener: ShowMoreListener? = null
    private var showMore = false
    private var originMaxLines = 4

    constructor(context: Context) : super(context) {
        ELLIPSIS = context.getString(R.string.ellipsis)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        ELLIPSIS = context.getString(R.string.ellipsis)
        initAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        ELLIPSIS = context.getString(R.string.ellipsis)
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.EllipsingTextView)
        originMaxLines = typedArray.getInt(R.styleable.EllipsingTextView_max_line, 4)
        typedArray.recycle()
    }

    fun addEllipsizeListener(listener: EllipsizeListener?) {
        if (listener == null) {
            throw NullPointerException()
        }
        ellipsizeListeners.add(listener)
    }

    fun removeEllipsizeListener(listener: EllipsizeListener?) {
        ellipsizeListeners.remove(listener)
    }

    fun setShowMoreListener(showMoreListener: ShowMoreListener){
        this.showMoreListener = showMoreListener
    }

    override fun setLineSpacing(add: Float, mult: Float) {
        lineAdditionalVerticalPadding = add
        lineSpacingMultiPier = mult
        super.setLineSpacing(add, mult)
    }

    override fun onTextChanged(text: CharSequence, start: Int, before: Int, after: Int) {
        super.onTextChanged(text, start, before, after)
        if (!programmaticChange) {
            fullText = text.toString()
            isStale = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (isStale) {
            resetText()
        }
        super.onDraw(canvas)
    }

    private fun resetText() {
        val maxLines = maxLines
        var sss = SpannableString(fullText)
        var workingText = fullText
        var ellipsized = false
        if (maxLines != -1 && !showMore) {
            val layout = createWorkingLayout(workingText)
            val originalLineCount = layout.lineCount
            if (originalLineCount > maxLines) {
                workingText =
                    fullText!!.substring(0, layout.getLineEnd(maxLines - 1)).trim { it <= ' ' }
                while (createWorkingLayout(workingText + ELLIPSIS + blankText + showMoreText).lineCount > maxLines) {
                    val lastSpace = workingText!!.lastIndexOf(' ')
                    workingText = if (lastSpace == -1) {
                        workingText.substring(0, workingText.length - 1)
                    } else {
                        workingText.substring(0, lastSpace)
                    }
                }
                workingText = workingText + ELLIPSIS + blankText + showMoreText
                sss = SpannableString(workingText)
                sss.setSpan(
                    ShowMoreClickText(), workingText.length - showMoreText.length,
                    workingText.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                movementMethod = LinkMovementMethod.getInstance() // 不设置 没有点击事件
                highlightColor = Color.TRANSPARENT //设置点击后的颜色为透明
                ellipsized = true
            }
        }
        if (showMore) {
            programmaticChange = true
            text = try {
                workingText
            } finally {
                programmaticChange = false
            }
        } else {
            if (workingText != text) {
                programmaticChange = true
                text = try {
                    sss
                } finally {
                    programmaticChange = false
                }
            }
        }

        isStale = false
        showMore = false
        if (ellipsized != isEllipsized) {
            isEllipsized = ellipsized
            for (listener in ellipsizeListeners) {
                listener.ellipsizeStateChanged(ellipsized)
            }
        }
    }

    private fun createWorkingLayout(workingText: String?): Layout {
        val staticLayout: StaticLayout
        staticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val layoutBuilder = StaticLayout.Builder.obtain(
                workingText, 0, workingText!!.length, paint, width.toFloat().roundToInt()
            )
            layoutBuilder.build()
        } else {
            StaticLayout(
                workingText,
                paint,
                width - paddingLeft - paddingRight,
                Layout.Alignment.ALIGN_NORMAL,
                lineSpacingMultiPier,
                lineAdditionalVerticalPadding,
                false
            )
        }
        return staticLayout
    }

    interface EllipsizeListener {
        fun ellipsizeStateChanged(ellipsized: Boolean)
    }

    interface ShowMoreListener {
        fun onClick()
    }

    companion object {
        private lateinit var ELLIPSIS: String
    }

    inner class ShowMoreClickText: ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            //设置文本的颜色
            ds.color = Color.BLUE
            //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            showMoreListener?.onClick()
        }
    }

    fun showFullText(fullText: String) {
        this.fullText = fullText
        isStale = true
        showMore = true
        maxLines = Int.MAX_VALUE
        invalidate()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        if (!showMore) {
            maxLines = originMaxLines
        }
        super.setText(text, type)
    }

    fun setShowMoreText(showMoreText: String) {
        this.showMoreText = showMoreText
    }

}