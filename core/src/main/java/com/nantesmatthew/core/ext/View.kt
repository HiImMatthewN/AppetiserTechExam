package com.nantesmatthew.core.ext

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.annotation.AnimatorRes
import androidx.core.animation.addListener
import androidx.core.view.isVisible


fun View.expand() {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val initialHeight = 0
    val targetHeight = measuredHeight


    layoutParams.height = 0
    isVisible = true

    val valueAnimator = ValueAnimator.ofInt(initialHeight, targetHeight)
    valueAnimator.addUpdateListener { animation ->
        layoutParams.height = animation.animatedValue as Int
        requestLayout()
    }
    valueAnimator.addListener(onEnd = {
        layoutParams.height = targetHeight

    })
    valueAnimator.duration = 300
    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.start()
}

fun View.collapse() {
    val initialHeight = measuredHeight
    val targetHeight = 0

    val valueAnimator = ValueAnimator.ofInt(initialHeight, targetHeight)
    valueAnimator.addUpdateListener { animation ->
        layoutParams.height = animation.animatedValue as Int
        requestLayout()
    }
    valueAnimator.addListener(onEnd = {
        layoutParams.height = targetHeight
        this.isVisible = false

    })
    valueAnimator.duration = 300
    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.start()

}



fun View.touchListener(onTouchDown: () -> Unit, onTouchUp: () -> Unit) {
    this.setOnTouchListener { view, motionEvent ->
        if (motionEvent.action == MotionEvent.ACTION_DOWN) {
            onTouchDown()
        }
        if (motionEvent.action == MotionEvent.ACTION_UP) {
            onTouchUp()

        }
        true
    }
}


fun View.animate(@AnimatorRes id: Int): Animator {
    val animator = AnimatorInflater.loadAnimator(context, id)
    animator.setTarget(this)
    animator.start()
    return animator
}
