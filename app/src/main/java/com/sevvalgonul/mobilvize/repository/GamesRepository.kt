package com.sevvalgonul.mobilvize.repository

import com.sevvalgonul.mobilvize.api.RetrofitInstance
import com.sevvalgonul.mobilvize.db.FavouriteDatabase

// In MVVM the purpose of the repository is get to data from our database and our remote data source so from retrofit from API
class GamesRepository(val db : FavouriteDatabase) {
    suspend fun getAllGames(page : Int) =
        RetrofitInstance.api.getAllGames(page = page)
}