package com.example.movieapp.data.repository

import com.example.movieapp.data.api.MovieDetails
import com.example.movieapp.data.api.MovieResponse
import com.example.movieapp.data.api.TMDbApi
import com.example.movieapp.data.local.FavoriteMovieDao
import com.example.movieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface MovieRepository {
    suspend fun getMovies(page: Int): List<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun toggleFavorite(movie: Movie)
    fun getFavorites(): Flow<List<Movie>>
    suspend fun isFavorite(movieId: Int): Boolean
}

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: TMDbApi,
    private val apiKey: String,
    private val favoriteMovieDao: FavoriteMovieDao
) : MovieRepository {

    override suspend fun getMovies(page: Int): List<Movie> {
        val response = api.getMovies(apiKey = apiKey, page = page)
        return response.results.map { apiMovie ->
            val isFavorite = favoriteMovieDao.isFavorite(apiMovie.id)
            Movie.fromApi(apiMovie, isFavorite)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return api.getMovieDetails(movieId = movieId, apiKey = apiKey)
    }

    override suspend fun toggleFavorite(movie: Movie) {
        if (movie.isFavorite) {
            favoriteMovieDao.deleteFavorite(movie.toFavorite())
        } else {
            favoriteMovieDao.insertFavorite(movie.toFavorite())
        }
    }

    override fun getFavorites(): Flow<List<Movie>> {
        return favoriteMovieDao.getAllFavorites().map { favorites ->
            favorites.map { Movie.fromFavorite(it) }
        }
    }

    override suspend fun isFavorite(movieId: Int): Boolean {
        return favoriteMovieDao.isFavorite(movieId)
    }
} 