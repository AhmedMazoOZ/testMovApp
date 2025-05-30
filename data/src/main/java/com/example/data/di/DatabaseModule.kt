package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.FavoriteMovieDao
import com.example.movieapp.data.local.MovieDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieDao(database: MovieDatabase): FavoriteMovieDao {
        return database.favoriteMovieDao()
    }
} 