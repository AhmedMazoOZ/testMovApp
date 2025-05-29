package com.example.movieapp.ui.shared

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SharedViewModel : ViewModel() {

    private val _isGridLayout = MutableStateFlow(false)
    val isGridLayout: StateFlow<Boolean> = _isGridLayout.asStateFlow()

    fun toggleLayout() {
        _isGridLayout.update { !it }
    }
} 