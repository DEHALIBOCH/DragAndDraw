package com.demoapp.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

private const val TAG = "BoxDrawingView"

class BoxDrawingView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var currentBox: Box? = null
    private val boxen = mutableListOf<Box>()

    private val boxPaint = Paint().apply {
        color = 0x22ff0000
    }
    private val backgroundPaint = Paint().apply {
        color = 0xfff8e0
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPaint(backgroundPaint)
        boxen.forEach { box ->
            canvas?.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
        }
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        val action = when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                updateCurrentBox(current)
                "ACTION_MOVE"
            }

            MotionEvent.ACTION_CANCEL -> {
                currentBox = null
                "ACTION_CANCEL"
            }

            MotionEvent.ACTION_DOWN -> {
                currentBox = Box(current).also {
                    boxen.add(it)
                }
                "ACTION_DOWN"
            }

            MotionEvent.ACTION_UP -> {
                updateCurrentBox(current)
                currentBox = null
                "ACTION_UP"
            }

            else -> ""
        }

        Log.i(TAG, "$action at x = ${current.x}, y = ${current.y}")

        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }
    }
}