package com.example.domain.usecase

import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        repository.toggleFavorite(movie)
    }
} 