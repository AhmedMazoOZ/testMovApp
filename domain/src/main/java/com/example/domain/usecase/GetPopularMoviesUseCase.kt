package com.example.domain.usecase

import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int): List<Movie> {
        return repository.getPopularMovies(page)
    }
} 