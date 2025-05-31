package com.example.navigation.api

interface NavigationApi {
    fun navigateToHome()
    fun navigateToMovieDetails(movieId: Int)
    fun navigateBack()
} 