package com.example.presentation.ui.details

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
import com.example.presentation.databinding.FragmentMovieDetailsBinding
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
        setupClickListeners()
        observeState()
        observeEffects()
        loadMovieDetails()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupClickListeners() {
        binding.favoriteButton.setOnClickListener {
            viewModel.onEvent(MovieDetailsEvent.OnFavoriteClick)
        }
        binding.retryButton.setOnClickListener {
            viewModel.onEvent(MovieDetailsEvent.Retry)
        }
    }

    private fun loadMovieDetails() {
        viewModel.handleIntent(MovieDetailsIntent.LoadMovieDetails(args.movieId))
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    updateUi(state)
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

    private fun updateUi(state: MovieDetailsState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.errorMessage.visibility = if (state.error != null) View.VISIBLE else View.GONE
        binding.errorMessage.text = state.error
        binding.retryButton.visibility = if (state.error != null) View.VISIBLE else View.GONE
        binding.content.visibility = if (state.movieDetails != null) View.VISIBLE else View.GONE

        state.movieDetails?.let { movie ->
            binding.title.text = movie.title
            binding.overview.text = movie.overview
            binding.releaseDate.text = movie.releaseDate
            binding.rating.text = movie.voteAverage.toString()
            binding.runtime.text = "${movie.runtime} min"
            binding.genres.text = movie.genres.joinToString(", ")
            binding.favoriteButton.isSelected = movie.isFavorite

            // Load images using your preferred image loading library
            // Glide.with(this).load(movie.posterPath).into(binding.posterImage)
            // Glide.with(this).load(movie.backdropPath).into(binding.backdropImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 