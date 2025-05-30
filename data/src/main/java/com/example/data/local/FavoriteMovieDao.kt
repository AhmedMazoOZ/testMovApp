package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: FavoriteMovie)

    @Delete
    suspend fun deleteMovie(movie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): Flow<List<FavoriteMovie>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :movieId)")
    suspend fun isFavorite(movieId: Int): Boolean
} 