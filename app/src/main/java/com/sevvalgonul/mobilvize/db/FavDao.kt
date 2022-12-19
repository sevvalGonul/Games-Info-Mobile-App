package com.sevvalgonul.mobilvize.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sevvalgonul.mobilvize.ResultGame

@Dao
interface FavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(favGame : ResultGame) : Long // The id that was inserted is returned

    @Query("SELECT * FROM favourites")  // This is going to return all games in database which are favourited games
    fun getAllFavourites() : LiveData<List<ResultGame>>  // ??? MutableList olması gerekmez mi

    @Delete
    suspend fun deleteFavourite(favGame: ResultGame)
}