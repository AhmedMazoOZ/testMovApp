package com.example.domain.repository

import com.example.domain.model.Movie
import com.example.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun searchMovies(query: String, page: Int): List<Movie>
    suspend fun toggleFavorite(movie: Movie)
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun isFavorite(movieId: Int): Boolean
} 