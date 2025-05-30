package com.example.presentation.ui.home

import com.example.domain.model.Movie

data class HomeState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val isLastPage: Boolean = false
) 