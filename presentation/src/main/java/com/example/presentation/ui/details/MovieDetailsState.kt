package com.example.presentation.ui.details

import com.example.domain.model.MovieDetails

data class MovieDetailsState(
    val movieDetails: MovieDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) 