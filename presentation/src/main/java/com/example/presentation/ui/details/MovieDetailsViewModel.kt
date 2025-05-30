package com.example.presentation.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Movie
import com.example.domain.usecase.GetMovieDetailsUseCase
import com.example.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _state = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState> = _state.asStateFlow()

    init {
        loadMovieDetails()
    }

    fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.LoadMovieDetails -> loadMovieDetails()
            is MovieDetailsEvent.OnFavoriteClick -> handleFavoriteClick()
            is MovieDetailsEvent.Retry -> loadMovieDetails()
        }
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val movieDetails = getMovieDetailsUseCase(movieId)
                _state.update { 
                    it.copy(
                        movieDetails = movieDetails,
                        isLoading = false
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

    private fun handleFavoriteClick() {
        val currentMovie = _state.value.movieDetails ?: return
        viewModelScope.launch {
            try {
                val movie = Movie(
                    id = currentMovie.id,
                    title = currentMovie.title,
                    overview = currentMovie.overview,
                    posterPath = currentMovie.posterPath,
                    backdropPath = currentMovie.backdropPath,
                    releaseDate = currentMovie.releaseDate,
                    voteAverage = currentMovie.voteAverage,
                    isFavorite = currentMovie.isFavorite
                )
                toggleFavoriteUseCase(movie)
                _state.update { 
                    it.copy(
                        movieDetails = it.movieDetails?.copy(
                            isFavorite = !it.movieDetails.isFavorite
                        )
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