package com.nantesmatthew.core.ext

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.reStoreState() {
    val recyclerViewState = this.layoutManager?.onSaveInstanceState()
    this.layoutManager?.onRestoreInstanceState(recyclerViewState)
}