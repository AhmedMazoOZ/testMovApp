package com.example.movieapp.data.model

import com.example.movieapp.data.api.Movie as ApiMovie
import com.example.movieapp.data.local.FavoriteMovie

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val overview: String,
    val voteAverage: Double,
    val isFavorite: Boolean = false
) {
    companion object {
        fun fromApi(apiMovie: ApiMovie, isFavorite: Boolean = false) = Movie(
            id = apiMovie.id,
            title = apiMovie.title,
            posterPath = apiMovie.poster_path,
            backdropPath = apiMovie.backdrop_path,
            releaseDate = apiMovie.release_date,
            overview = apiMovie.overview,
            voteAverage = apiMovie.vote_average,
            isFavorite = isFavorite
        )

        fun fromFavorite(favoriteMovie: FavoriteMovie) = Movie(
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

    fun toFavorite() = FavoriteMovie(
        id = id,
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        overview = overview,
        voteAverage = voteAverage
    )
} 