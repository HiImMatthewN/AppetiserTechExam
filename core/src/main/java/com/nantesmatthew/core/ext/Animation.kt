package com.nantesmatthew.core.ext

import android.view.animation.Animation

fun Animation.animationListener(onStart: (() -> Unit), onEnd: (() -> Unit)) {
    this.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {
            onStart()
        }

        override fun onAnimationEnd(p0: Animation?) {
            onEnd()
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }
    })
}