package com.sevvalgonul.mobilvize



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Game::class], version = 1)
abstract class FavGameDatabase : RoomDatabase() {

    abstract fun favGameDao(): GameDao

    companion object {
        private var instance: FavGameDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): FavGameDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, FavGameDatabase::class.java,
                    "favGameTable")
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //populateDatabase(instance!!)
            }
        }
/*
        private fun populateDatabase(db: FavGameDatabase) {
            val noteDao = db.noteDao()
            subscribeOnBackground {
                noteDao.insert(Note("title 1", "desc 1", 1))
                noteDao.insert(Note("title 2", "desc 2", 2))
                noteDao.insert(Note("title 3", "desc 3", 3))

            }
        }*/
    }



}