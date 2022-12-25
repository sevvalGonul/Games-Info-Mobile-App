package com.sevvalgonul.mobilvize

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sevvalgonul.mobilvize.repository.GamesRepository

class GamesViewModelProviderFactory(val gamesRepository: GamesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GamesViewModel(gamesRepository) as T
    }
}