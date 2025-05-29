package com.example.movieapp.ui.details

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
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MovieDetailsEffect>()
    val effect: SharedFlow<MovieDetailsEffect> = _effect.asSharedFlow()

    fun handleIntent(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.LoadMovieDetails -> loadMovieDetails(intent.movieId)
            is MovieDetailsIntent.ToggleFavorite -> toggleFavorite(intent.movie)
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val movieDetails = movieRepository.getMovieDetails(movieId)
                val isFavorite = movieRepository.isFavorite(movieId)
                _state.update {
                    it.copy(
                        isLoading = false,
                        movieDetails = movieDetails,
                        isFavorite = isFavorite
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
                _effect.emit(MovieDetailsEffect.ShowError(e.message ?: "Unknown error occurred"))
            }
        }
    }

    private fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            try {
                movieRepository.toggleFavorite(movie)
                _state.update { it.copy(isFavorite = !it.isFavorite) }
            } catch (e: Exception) {
                _effect.emit(MovieDetailsEffect.ShowError(e.message ?: "Failed to update favorite status"))
            }
        }
    }
} 