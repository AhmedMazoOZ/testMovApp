package com.example.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Movie
import com.example.domain.usecase.GetPopularMoviesUseCase
import com.example.domain.usecase.ToggleFavoriteUseCase
import com.example.movieapp.ui.home.HomeEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect: SharedFlow<HomeEffect> = _effect.asSharedFlow()

    init {
        loadMovies()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadMovies -> loadMovies()
            is HomeEvent.LoadMoreMovies -> loadMoreMovies()
            is HomeEvent.OnMovieClick -> handleMovieClick(event.movie)
            is HomeEvent.OnFavoriteClick -> handleFavoriteClick(event.movie)
            is HomeEvent.Retry -> loadMovies()
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val movies = getPopularMoviesUseCase(1)
                _state.update { 
                    it.copy(
                        movies = movies,
                        isLoading = false,
                        currentPage = 1,
                        isLastPage = movies.isEmpty()
                    )
                }
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An error occurred"
                    )
                }
            }
        }
    }

    private fun loadMoreMovies() {
        if (_state.value.isLoading || _state.value.isLastPage) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val currentPage = _state.value.currentPage
                val newMovies = getPopularMoviesUseCase(currentPage + 1)
                _state.update { 
                    it.copy(
                        movies = it.movies + newMovies,
                        isLoading = false,
                        currentPage = currentPage + 1,
                        isLastPage = newMovies.isEmpty()
                    )
                }
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An error occurred"
                    )
                }
            }
        }
    }

    private fun handleMovieClick(movie: Movie) {
        // Navigate to movie details
    }

    private fun handleFavoriteClick(movie: Movie) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(movie)
                _state.update { currentState ->
                    currentState.copy(
                        movies = currentState.movies.map { 
                            if (it.id == movie.id) it.copy(isFavorite = !it.isFavorite) else it 
                        }
                    )
                }
            } catch (e: Exception) {
                _state.update { 
                    it.copy(error = e.message ?: "Failed to update favorite status")
                }
            }
        }
    }
} 