package com.example.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.presentation.R
import com.example.presentation.databinding.FragmentHomeBinding
import com.example.presentation.ui.common.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val movieAdapter = MovieAdapter(
        onMovieClick = { movie -> viewModel.onEvent(HomeEvent.OnMovieClick(movie)) },
        onFavoriteClick = { movie -> viewModel.onEvent(HomeEvent.OnFavoriteClick(movie)) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeState()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.onEvent(HomeEvent.LoadMoreMovies)
                    }
                }
            })
        }
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

    private fun updateUi(state: HomeState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.errorMessage.visibility = if (state.error != null) View.VISIBLE else View.GONE
        binding.errorMessage.text = state.error
        binding.retryButton.visibility = if (state.error != null) View.VISIBLE else View.GONE
        binding.retryButton.setOnClickListener { viewModel.onEvent(HomeEvent.Retry) }

        movieAdapter.submitList(state.movies)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 