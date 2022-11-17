package com.nantesmatthew.movie.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.nantesmatthew.movie.R

class PlayToCloseMorphView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0

) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var playIcon: Drawable? = null
    private var closeIcon: Drawable? = null
    private var playToClose: AnimatedVectorDrawableCompat? = null
    private var closeToPlay: AnimatedVectorDrawableCompat? = null

    companion object {
        private const val TAG = "PlayToCloseMorphView"
    }

    init {
        playIcon = ContextCompat.getDrawable(context, R.drawable.ic_play)
        closeIcon = ContextCompat.getDrawable(context, R.drawable.ic_close_36)
        playToClose = AnimatedVectorDrawableCompat.create(context, R.drawable.avd_play_to_close)
        closeToPlay = AnimatedVectorDrawableCompat.create(context, R.drawable.avd_close_to_play)
        setImageDrawable(playIcon)
    }


    fun playAnimation() {
        morph()
    }

    fun showStart() {
        setImageDrawable(playIcon)

    }

    fun showEnd() {
        setImageDrawable(closeIcon)
    }

    private fun morph() {

        val drawable =
            if (drawable == playToClose || drawable == closeIcon) closeToPlay else playToClose
        setImageDrawable(drawable)
        drawable?.start()
    }

}