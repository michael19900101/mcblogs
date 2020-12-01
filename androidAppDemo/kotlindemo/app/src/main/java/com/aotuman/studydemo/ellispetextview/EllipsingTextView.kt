package com.aotuman.studydemo.ellispetextview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.text.Layout
import android.text.SpannableString
import android.text.Spanned
import android.text.StaticLayout
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.aotuman.studydemo.R
import java.util.*
import kotlin.math.roundToInt

class EllipsingTextView : AppCompatTextView {
    private val ellipsizeListeners: MutableList<EllipsizeListener> = ArrayList()
    private val blankText = " "
    private val endText = "共101项"
    var isEllipsized = false
        private set
    private var isStale = true
    private var programmaticChange = false
    private var fullText: String? = null
    private var lineSpacingMultiPier = 1.0f
    private var lineAdditionalVerticalPadding = 0.0f

    constructor(context: Context) : super(context) {
        ELLIPSIS = context.getString(R.string.ellipsis)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        ELLIPSIS = context.getString(R.string.ellipsis)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        ELLIPSIS = context.getString(R.string.ellipsis)
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
        if (maxLines != -1) {
            val layout = createWorkingLayout(workingText)
            val originalLineCount = layout.lineCount
            if (originalLineCount > maxLines) {
                workingText =
                    fullText!!.substring(0, layout.getLineEnd(maxLines - 1)).trim { it <= ' ' }
                while (createWorkingLayout(workingText + ELLIPSIS + blankText + endText).lineCount > maxLines) {
                    val lastSpace = workingText!!.lastIndexOf(' ')
                    workingText = if (lastSpace == -1) {
                        workingText.substring(0, workingText.length - 1)
                    } else {
                        workingText.substring(0, lastSpace)
                    }
                }
                workingText = workingText + ELLIPSIS + blankText + endText
                sss = SpannableString(workingText)
                sss.setSpan(
                    MyClickText(context), workingText.length - endText.length,
                    workingText.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                movementMethod = LinkMovementMethod.getInstance() // 不设置 没有点击事件
                highlightColor = Color.TRANSPARENT //设置点击后的颜色为透明
                ellipsized = true
            }
        }
        if (workingText != text) {
            programmaticChange = true
            text = try {
                sss
            } finally {
                programmaticChange = false
            }
        }
        isStale = false
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

    companion object {
        private lateinit var ELLIPSIS: String
    }
}