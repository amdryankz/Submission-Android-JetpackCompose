package com.example.submission.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission.data.AnimeRepository
import com.example.submission.model.Anime
import com.example.submission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: AnimeRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Anime>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Anime>>>
        get() = _uiState

    private var currentKeyword = ""

    fun getAllAnime(keyword: String = "") {
        viewModelScope.launch {
            repository.getAllAnime(keyword)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun searchAnime(keyword: String) {
        currentKeyword = keyword
        getAllAnime(keyword)
    }
}