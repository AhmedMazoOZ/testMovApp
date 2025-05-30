package com.example.presentation.ui.details

sealed class MovieDetailsEvent {
    object LoadMovieDetails : MovieDetailsEvent()
    object OnFavoriteClick : MovieDetailsEvent()
    object Retry : MovieDetailsEvent()
} 