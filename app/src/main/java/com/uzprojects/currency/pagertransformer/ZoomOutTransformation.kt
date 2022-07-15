package com.uzprojects.currency.pagertransformer

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class ZoomOutTransformation : ViewPager.PageTransformer {
    private val MIN_SCALE = 0.65f
    private val MIN_ALPHA = 0.3f

    override fun transformPage(page: View, position: Float) {
        when {
            position < -1 -> {
                page.alpha = 0F
            }
            position <= 1 -> {
                page.scaleX = MIN_SCALE.coerceAtLeast(1 - abs(position))
                page.scaleY = MIN_SCALE.coerceAtLeast(1 - abs(position))
                page.alpha = MIN_ALPHA.coerceAtLeast(1 - abs(position))
            }
            else -> {
                page.alpha = 0F
            }
        }
    }
}