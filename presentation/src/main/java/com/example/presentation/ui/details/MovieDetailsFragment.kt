package com.example.movieapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.movieapp.R
import com.example.movieapp.data.api.MovieDetails
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observeState()
        observeEffects()
        loadMovieDetails()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadMovieDetails() {
        viewModel.handleIntent(MovieDetailsIntent.LoadMovieDetails(args.movieId))
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.progressBar.isVisible = state.isLoading
                    state.movieDetails?.let { movieDetails ->
                        updateUI(movieDetails, state.isFavorite)
                    }
                }
            }
        }
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is MovieDetailsEffect.ShowError -> {
                            Toast.makeText(requireContext(), effect.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun updateUI(movieDetails: MovieDetails, isFavorite: Boolean) {
        with(binding) {
            toolbar.title = movieDetails.title
            backdropImage.load("https://image.tmdb.org/t/p/w500${movieDetails.backdrop_path}")
            posterImage.load("https://image.tmdb.org/t/p/w500${movieDetails.poster_path}")
            titleText.text = movieDetails.title
            releaseDateText.text = movieDetails.release_date
            ratingText.text = getString(R.string.rating_format, movieDetails.vote_average)
            overviewText.text = movieDetails.overview
            runtimeText.text = getString(R.string.runtime_format, movieDetails.runtime)
            genresText.text = movieDetails.genres.joinToString(", ") { it.name }

            favoriteButton.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_border
            )
            favoriteButton.setOnClickListener {
                viewModel.handleIntent(
                    MovieDetailsIntent.ToggleFavorite(
                        Movie(
                            id = movieDetails.id,
                            title = movieDetails.title,
                            posterPath = movieDetails.poster_path,
                            backdropPath = movieDetails.backdrop_path,
                            releaseDate = movieDetails.release_date,
                            overview = movieDetails.overview,
                            voteAverage = movieDetails.vote_average,
                            isFavorite = isFavorite
                        )
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 