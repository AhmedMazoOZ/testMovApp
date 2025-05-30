package com.example.data.repository

import com.example.data.api.TMDbApi
import com.example.data.local.FavoriteMovie
import com.example.data.local.FavoriteMovieDao
import com.example.domain.model.Movie
import com.example.domain.model.MovieDetails
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: TMDbApi,
    private val apiKey: String,
    private val favoriteMovieDao: FavoriteMovieDao
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val response = api.getMovies(apiKey, page)
        return response.results.map { movieDto ->
            val isFavorite = favoriteMovieDao.isFavorite(movieDto.id)
            Movie(
                id = movieDto.id,
                title = movieDto.title,
                posterPath = movieDto.poster_path,
                backdropPath = movieDto.backdrop_path,
                releaseDate = movieDto.release_date,
                overview = movieDto.overview,
                voteAverage = movieDto.vote_average,
                isFavorite = isFavorite
            )
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        val movieDetailsDto = api.getMovieDetails(movieId, apiKey)
        val isFavorite = favoriteMovieDao.isFavorite(movieId)
        return MovieDetails(
            id = movieDetailsDto.id,
            title = movieDetailsDto.title,
            posterPath = movieDetailsDto.poster_path,
            backdropPath = movieDetailsDto.backdrop_path,
            releaseDate = movieDetailsDto.release_date,
            overview = movieDetailsDto.overview,
            voteAverage = movieDetailsDto.vote_average,
            runtime = movieDetailsDto.runtime!!,
            genres = movieDetailsDto.genres.map { it.name },
            isFavorite = isFavorite
        )
    }

    override suspend fun searchMovies(query: String, page: Int): List<Movie> {
        val response = api.searchMovies(apiKey, query, page)
        return response.results.map { movieDto ->
            val isFavorite = favoriteMovieDao.isFavorite(movieDto.id)
            Movie(
                id = movieDto.id,
                title = movieDto.title,
                posterPath = movieDto.poster_path,
                backdropPath = movieDto.backdrop_path,
                releaseDate = movieDto.release_date,
                overview = movieDto.overview,
                voteAverage = movieDto.vote_average,
                isFavorite = isFavorite
            )
        }
    }

    override suspend fun toggleFavorite(movie: Movie) {
        if (movie.isFavorite) {
            favoriteMovieDao.deleteMovie(movie.toFavoriteMovie())
        } else {
            favoriteMovieDao.insertMovie(movie.toFavoriteMovie())
        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMovieDao.getAllFavoriteMovies().map { favoriteMovies ->
            favoriteMovies.map { favoriteMovie ->
                Movie(
                    id = favoriteMovie.id,
                    title = favoriteMovie.title,
                    posterPath = favoriteMovie.posterPath,
                    backdropPath = favoriteMovie.backdropPath,
                    releaseDate = favoriteMovie.releaseDate,
                    overview = favoriteMovie.overview,
                    voteAverage = favoriteMovie.voteAverage,
                    isFavorite = true
                )
            }
        }
    }

    override suspend fun isFavorite(movieId: Int): Boolean {
        return favoriteMovieDao.isFavorite(movieId)
    }

    private fun Movie.toFavoriteMovie(): FavoriteMovie {
        return FavoriteMovie(
            id = id,
            title = title,
            overview = overview,
            posterPath = posterPath,
            backdropPath = backdropPath,
            releaseDate = releaseDate,
            voteAverage = voteAverage
        )
    }
} 