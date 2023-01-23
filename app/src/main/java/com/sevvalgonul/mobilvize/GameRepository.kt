package com.sevvalgonul.mobilvize


import android.app.Application
import androidx.lifecycle.LiveData


class GameRepository(application: Application) {

    private var favGameDao: GameDao
    private var allFavGames: LiveData<List<Game>>

    private val database = FavGameDatabase.getInstance(application)

    init {
        favGameDao = database.favGameDao()
        allFavGames = favGameDao.getAllGames()
    }

    fun insert(game: Game) {
/*
        SubScribeFun.subscribeOnBackground {
            favGameDao.insert(game)
        }
 */
        favGameDao.insert(game)
    }

    fun update(game: Game) {
        /*
        SubScribeFun.subscribeOnBackground {
            favGameDao.update(game)
        }
         */
        favGameDao.update(game)
    }


    fun delete(game: Game) {
        /*
        SubScribeFun.subscribeOnBackground {
            favGameDao.delete(game)
        }
         */
        favGameDao.delete(game)
    }

    fun deleteAllGames() {
        /*
        SubScribeFun.subscribeOnBackground {
            favGameDao.deleteAllGames()
        }
         */
        favGameDao.deleteAllGames()
    }

    fun getAllGames(): LiveData<List<Game>> {
        return allFavGames
    }

}