package com.example.movieapp.di

import com.example.movieapp.data.api.TMDbApi
import com.example.movieapp.data.local.FavoriteMovieDao
import com.example.movieapp.data.repository.MovieRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: TMDbApi,
        apiKey: String,
        favoriteMovieDao: FavoriteMovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(api, apiKey, favoriteMovieDao)
    }
} 