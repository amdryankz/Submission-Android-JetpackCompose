package com.example.submission.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission.data.AnimeRepository
import com.example.submission.model.Anime
import com.example.submission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: AnimeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Anime>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Anime>>
        get() = _uiState

    fun getAnimeById(animeId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getAnimeById(animeId))
        }
    }

    fun addToFavorite(anime: Anime) {
        repository.addToFavorite(anime)
    }
}