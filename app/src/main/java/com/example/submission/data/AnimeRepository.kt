package com.example.submission.data

import androidx.compose.runtime.mutableStateListOf
import com.example.submission.model.Anime
import com.example.submission.model.FakeAnimeDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AnimeRepository {

    private val anime = mutableListOf<Anime>()
    private val favoriteAnime = mutableStateListOf<Anime>()

    init {
        if (anime.isEmpty()) {
            FakeAnimeDataSource.dummyAnime.forEach {
                anime.add(it)
            }
        }
    }

    fun getAllAnime(query: String): Flow<List<Anime>> {
        return flow {
            val filteredAnime = anime.filter { it.title.contains(query, ignoreCase = true) }
            emit(filteredAnime)
        }
    }

    fun getAnimeById(animeId: Long): Anime {
        return anime.first {
            it.id == animeId
        }
    }

    fun getAddedAnimeFavorite(): Flow<List<Anime>> {
        return flow {
            emit(anime.filter { it.isFav })
        }
    }

    fun addToFavorite(anime: Anime) {
        anime.isFav = true
    }

    fun updateAnimeFavorite(animeId: Long) {
        val animeToUpdate = anime.find { it.id == animeId }
        animeToUpdate?.let {
            it.isFav = !it.isFav
            if (it.isFav) {
                favoriteAnime.add(it)
            } else {
                favoriteAnime.remove(it)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: AnimeRepository? = null

        fun getInstance(): AnimeRepository =
            instance ?: synchronized(this) {
                AnimeRepository().apply {
                    instance = this
                }
            }
    }
}