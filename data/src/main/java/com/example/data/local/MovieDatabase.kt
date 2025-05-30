package com.example.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.FavoriteMovie
import com.example.data.local.FavoriteMovieDao

@Database(entities = [FavoriteMovie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
} 