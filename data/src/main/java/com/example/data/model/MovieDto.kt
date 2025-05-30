package com.example.data.model

import com.example.domain.model.Movie

data class MovieDto(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val release_date: String,
    val overview: String,
    val vote_average: Double,
    val backdrop_path: String?
) {
    fun toMovie(isFavorite: Boolean = false): Movie {
        return Movie(
            id = id,
            title = title,
            overview = overview,
            posterPath = poster_path,
            backdropPath = backdrop_path,
            releaseDate = release_date,
            voteAverage = vote_average,
            isFavorite = isFavorite
        )
    }
} 