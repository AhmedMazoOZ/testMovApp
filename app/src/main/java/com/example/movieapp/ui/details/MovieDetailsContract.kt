package com.example.movieapp.ui.details

import com.example.movieapp.data.api.MovieDetails
import com.example.movieapp.data.model.Movie

sealed class MovieDetailsIntent {
    data class LoadMovieDetails(val movieId: Int) : MovieDetailsIntent()
    data class ToggleFavorite(val movie: Movie) : MovieDetailsIntent()
}

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val isFavorite: Boolean = false,
    val error: String? = null
)

sealed class MovieDetailsEffect {
    data class ShowError(val message: String) : MovieDetailsEffect()
} 