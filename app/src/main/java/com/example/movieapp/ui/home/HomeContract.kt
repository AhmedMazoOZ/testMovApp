package com.example.movieapp.ui.home

import com.example.movieapp.data.model.Movie

sealed class HomeIntent {
    data object LoadMovies : HomeIntent()
    data object LoadMoreMovies : HomeIntent()
    data object ToggleLayout : HomeIntent()
    data class MovieClicked(val movie: Movie) : HomeIntent()
    data class ToggleFavorite(val movie: Movie) : HomeIntent()
}

data class HomeState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val isGridLayout: Boolean = false,
    val currentPage: Int = 1,
    val hasMorePages: Boolean = true,
    val error: String? = null
)

sealed class HomeEffect {
    data class ShowError(val message: String) : HomeEffect()
    data class NavigateToMovieDetails(val movieId: Int) : HomeEffect()
} 