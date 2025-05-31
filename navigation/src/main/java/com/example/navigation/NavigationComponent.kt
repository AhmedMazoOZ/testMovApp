package com.example.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.example.navigation.api.NavigationApi
import com.example.navigation.api.R


class NavigationComponent(private val navController: NavController) : NavigationApi {

    override fun navigateToHome() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.homeFragment, true)
            .build()
        navController.navigate(R.id.homeFragment, null, navOptions)
    }

    override fun navigateToMovieDetails(movieId: Int) {
        val action = com.example.presentation.ui.home.HomeFragmentDirections
            .actionHomeFragmentToMovieDetailsFragment(movieId)
        navController.navigate(action)
    }

    override fun navigateBack() {
        navController.popBackStack()
    }
} 