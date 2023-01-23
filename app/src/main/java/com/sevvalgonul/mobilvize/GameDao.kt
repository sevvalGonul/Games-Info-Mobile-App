package com.sevvalgonul.mobilvize


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameDao {

    @Insert
    fun insert(game: Game)

    @Update
    fun update(game: Game)

    @Delete
    fun delete(game: Game)

    @Query("delete from favGameTable")
    fun deleteAllGames()

    @Query("select * from favGameTable")
    fun getAllGames(): LiveData<List<Game>>
}
