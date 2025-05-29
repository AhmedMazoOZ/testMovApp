package com.example.movieapp.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApi {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetails
}

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val release_date: String,
    val overview: String,
    val vote_average: Double,
    val backdrop_path: String?
)

data class MovieDetails(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String,
    val overview: String,
    val vote_average: Double,
    val runtime: Int?,
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
) 