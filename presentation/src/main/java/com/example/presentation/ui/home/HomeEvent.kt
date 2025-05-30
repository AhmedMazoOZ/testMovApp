package com.example.presentation.ui.home

import com.example.domain.model.Movie

sealed class HomeEvent {
    object LoadMovies : HomeEvent()
    object LoadMoreMovies : HomeEvent()
    data class OnMovieClick(val movie: Movie) : HomeEvent()
    data class OnFavoriteClick(val movie: Movie) : HomeEvent()
    object Retry : HomeEvent()
} 