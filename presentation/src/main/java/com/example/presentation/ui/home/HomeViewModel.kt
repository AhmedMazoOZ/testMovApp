package com.example.movieapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.repository.MovieRepository
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
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect: SharedFlow<HomeEffect> = _effect.asSharedFlow()

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadMovies -> loadMovies()
            is HomeIntent.LoadMoreMovies -> loadMoreMovies()
            is HomeIntent.ToggleLayout -> toggleLayout()
            is HomeIntent.MovieClicked -> navigateToMovieDetails(intent.movie.id)
            is HomeIntent.ToggleFavorite -> toggleFavorite(intent.movie)
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                Log.d("HomeViewModel", "Loading movies...")
                val movies = repository.getMovies(page = 1)
                Log.d("HomeViewModel", "Loaded ${movies.size} movies")
                _state.update {
                    it.copy(
                        isLoading = false,
                        movies = movies,
                        currentPage = 1,
                        hasMorePages = true
                    )
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading movies", e)
                _state.update { it.copy(isLoading = false, error = e.message) }
                _effect.emit(HomeEffect.ShowError(e.message ?: "Unknown error occurred"))
            }
        }
    }

    private fun loadMoreMovies() {
        if (_state.value.isLoading || !_state.value.hasMorePages) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val nextPage = _state.value.currentPage + 1
                Log.d("HomeViewModel", "Loading more movies, page $nextPage")
                val newMovies = repository.getMovies(page = nextPage)
                Log.d("HomeViewModel", "Loaded ${newMovies.size} more movies")
                _state.update {
                    it.copy(
                        isLoading = false,
                        movies = it.movies + newMovies,
                        currentPage = nextPage,
                        hasMorePages = true
                    )
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading more movies", e)
                _state.update { it.copy(isLoading = false, error = e.message) }
                _effect.emit(HomeEffect.ShowError(e.message ?: "Unknown error occurred"))
            }
        }
    }

    private fun toggleLayout() {
        _state.update { it.copy(isGridLayout = !it.isGridLayout) }
    }

    private fun navigateToMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _effect.emit(HomeEffect.NavigateToMovieDetails(movieId))
        }
    }

    private fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(movie)
                _state.update { currentState ->
                    val updatedMovies = currentState.movies.map {
                        if (it.id == movie.id) it.copy(isFavorite = !it.isFavorite) else it
                    }
                    currentState.copy(movies = updatedMovies)
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error toggling favorite", e)
                _effect.emit(HomeEffect.ShowError(e.message ?: "Failed to update favorite status"))
            }
        }
    }
} 