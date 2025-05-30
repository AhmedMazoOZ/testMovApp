package com.example.movieapp.data.model

import com.example.data.local.FavoriteMovie
import com.example.movieapp.data.model.Movie as ApiMovie


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
        fun fromApi(apiMovie: ApiMovie, isFavorite: Boolean = false) = ApiMovie(
            id = apiMovie.id,
            title = apiMovie.title,
            posterPath = apiMovie.posterPath,
            backdropPath = apiMovie.backdropPath,
            releaseDate = apiMovie.releaseDate,
            overview = apiMovie.overview,
            voteAverage = apiMovie.voteAverage,
            isFavorite = isFavorite
        )

        fun fromFavorite(favoriteMovie: FavoriteMovie) = ApiMovie(
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