package com.nantesmatthew.core.ext

import android.view.animation.Animation

fun Animation.animationListener(onStart: (() -> Unit)? = null, onEnd: (() -> Unit)? = null) {
    this.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
            onStart?.invoke()
        }

        override fun onAnimationEnd(p0: Animation?) {
            onEnd?.invoke()
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }
    })
}