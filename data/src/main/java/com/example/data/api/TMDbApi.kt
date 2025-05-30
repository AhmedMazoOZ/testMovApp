package com.example.data.api

import com.example.data.model.MovieDetailsDto

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApi {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): MovieResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailsDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponseDto
}

data class MovieResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)

data class MovieDto(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val release_date: String,
    val overview: String,
    val vote_average: Double,
    val backdrop_path: String?
)

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
)

data class GenreDto(
    val id: Int,
    val name: String
) 