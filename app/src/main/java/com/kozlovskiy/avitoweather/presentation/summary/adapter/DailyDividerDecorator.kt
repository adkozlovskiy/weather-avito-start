package com.kozlovskiy.avitoweather.presentation.summary.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.kozlovskiy.avitoweather.R
import kotlin.math.roundToInt

class DailyDividerDecorator(private val divider: Drawable) : RecyclerView.ItemDecoration() {
    private val bounds = Rect()

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        c.save()
        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val bottom: Int = bounds.bottom + child.translationY.roundToInt()
            val top: Int = bottom - divider.intrinsicHeight
            divider.setBounds(child.left, top, child.right, bottom)
            divider.draw(c)
        }
        c.restore()
    }

    companion object {
        fun newInstance(context: Context): DailyDividerDecorator {
            return DailyDividerDecorator(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.daily_divider
                )!!
            )
        }
    }
}