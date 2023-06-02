package com.example.submission.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission.data.AnimeRepository
import com.example.submission.model.Anime
import com.example.submission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: AnimeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Anime>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Anime>>>
        get() = _uiState

    fun getAddedAnimeFavorite() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedAnimeFavorite().collect { listAnime ->
                _uiState.value = UiState.Success(listAnime)
            }
        }
    }

    fun updateAnimeFavorite(animeId: Long) {
        viewModelScope.launch {
            repository.updateAnimeFavorite(animeId)
        }
    }

    init {
        viewModelScope.launch {
            repository.getAddedAnimeFavorite().collect { animeList ->
                _uiState.value = UiState.Success(animeList)
            }
        }
    }
}