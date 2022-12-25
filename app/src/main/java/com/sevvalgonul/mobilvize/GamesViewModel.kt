package com.sevvalgonul.mobilvize

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevvalgonul.mobilvize.repository.GamesRepository
import com.sevvalgonul.mobilvize.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class GamesViewModel(val gamesRepository : GamesRepository) : ViewModel() {
    val games : MutableLiveData<Resource<GamesResponse>> = MutableLiveData()  // a live data object
    var gamesPage = 1

    init {
        getAllGames()
    }

    fun getAllGames() = viewModelScope.launch {
        // We are starting a coroutine in this func in order to call suspend function from repository
        // In View Model the best way to do is viewModelScope
        // viewModelScope make sures that this coroutine is alive as long as our view model is alive
        games.postValue(Resource.Loading())
        val response = gamesRepository.getAllGames(gamesPage)  // actual network request
        // Handle the response!:
        games.postValue(handleGamesResponse(response))
    }

    private fun handleGamesResponse(response : Response<GamesResponse>) : Resource<GamesResponse>{
        if(response.isSuccessful) {
            response.body()?.let { resultResponce ->
                // If response's body not null:
                return Resource.Success(resultResponce)
            }
        }
        return Resource.Error(response.message())
    }
}