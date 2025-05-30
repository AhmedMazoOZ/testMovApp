package com.example.data.model

import com.example.domain.model.MovieDetails

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String,
    val overview: String,
    val vote_average: Double,
    val runtime: Int?,
    val genres: List<GenreDto>
) {
    fun toMovieDetails(isFavorite: Boolean = false): MovieDetails {
        return MovieDetails(
            id = id,
            title = title,
            overview = overview,
            posterPath = poster_path,
            backdropPath = backdrop_path,
            releaseDate = release_date,
            voteAverage = vote_average,
            runtime = runtime ?: 0,
            genres = genres.map { it.name },
            isFavorite = isFavorite
        )
    }
}

data class GenreDto(
    val id: Int,
    val name: String
) 