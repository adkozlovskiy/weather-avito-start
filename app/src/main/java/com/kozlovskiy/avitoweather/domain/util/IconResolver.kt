package com.kozlovskiy.avitoweather.domain.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.kozlovskiy.avitoweather.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

fun interface IconResolver {
    fun resolve(iconStr: String): Drawable
}

class DefaultIconResolver @Inject constructor(
    @ApplicationContext
    private val appContext: Context,
) : IconResolver {
    override fun resolve(iconStr: String): Drawable {
        val resId = when (iconStr) {
            // Light icons
            "01d" -> R.drawable.d1
            "02d" -> R.drawable.d2
            "03d" -> R.drawable.d3
            "04d" -> R.drawable.d4
            "09d" -> R.drawable.d9
            "10d" -> R.drawable.d10
            "11d" -> R.drawable.d11
            "13d" -> R.drawable.d13
            "50d" -> R.drawable.d50

            // Night icons
            "01n" -> R.drawable.n1
            "02n" -> R.drawable.n2
            "03n" -> R.drawable.n3
            "04n" -> R.drawable.n4
            "09n" -> R.drawable.n9
            "10n" -> R.drawable.n10
            "11n" -> R.drawable.n11
            "13n" -> R.drawable.n13
            "50n" -> R.drawable.n50

            // No supported icon
            else -> throw IllegalArgumentException(" no resource for this icon $iconStr")
        }
        return AppCompatResources.getDrawable(appContext, resId)!!
    }
}