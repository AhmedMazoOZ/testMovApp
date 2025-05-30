package com.example.data.di

import com.example.data.api.TMDbApi
import com.example.data.local.FavoriteMovieDao

import com.example.data.repository.MovieRepositoryImpl
import com.example.domain.repository.MovieRepository
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