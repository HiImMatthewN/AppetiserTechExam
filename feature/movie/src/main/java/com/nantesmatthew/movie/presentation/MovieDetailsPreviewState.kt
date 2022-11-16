package com.nantesmatthew.movie.presentation

sealed class MovieDetailsPreviewState {
    class Playing(val previewUrl: String?) : MovieDetailsPreviewState()
    object NotPlaying : MovieDetailsPreviewState()

}