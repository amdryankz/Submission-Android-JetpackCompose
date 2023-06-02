package com.example.submission.di

import com.example.submission.data.AnimeRepository

object Injection {
    fun provideRepository(): AnimeRepository {
        return AnimeRepository.getInstance()
    }
}