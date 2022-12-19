package com.sevvalgonul.mobilvize.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sevvalgonul.mobilvize.ResultGame

@Database(entities = [ResultGame::class], version = 1)
abstract class FavouriteDatabase : RoomDatabase(){
    abstract fun getFavDao() : FavDao

    companion object {
        @Volatile  // Other threads can see it when a thread change this instance
        private var instance : FavouriteDatabase? = null  // This is going to be singleton, we will have a single instance of the db
        private val LOCK = Any()

        // invoke called whenever we create an instance of FavouriteDatabase class like write sth like FavouriteDatabase()
        // in invoke we are going to return current instance and if it's null, in synchronized block we call createDatabase func and also set the instance the result of the func
        operator fun invoke(context : Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it}  // if instance null set the instance to
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavouriteDatabase::class.java,
                "favourites_db.db"
            ).build()

    }

    /*

companion object {
        @Volatile
        private var instance: FavouriteDatabase? = null

        fun getInstance(context: Context): ArticleDatabase? {
            if (instance == null) {
                synchronized(FavouriteDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavouriteDatabase::class.java, "favourites_db.db"
                    ).build()
                }
            }
            return instance
        }
    }


     */
}