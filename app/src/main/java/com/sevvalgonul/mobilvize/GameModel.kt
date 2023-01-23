package com.sevvalgonul.mobilvize

import java.util.ArrayList

class GameModel {

    companion object {

        private val clikedItemList: ArrayList<Int> = arrayListOf<Int>()
        private var searchList: List<ResultGame> = emptyList()
        private var allGameList: ArrayList<ResultGame> = arrayListOf<ResultGame>()


        fun addGameList(results: ArrayList<ResultGame>) {
            allGameList.addAll(results)
        }

        fun setGameList(results: ArrayList<ResultGame>) {
            allGameList = results
        }

        fun getGameList() : ArrayList<ResultGame> {
            return allGameList
        }

        fun getSearchList(): List<ResultGame> {
            return searchList
        }

        fun setSearchList(results: List<ResultGame>) {
            searchList = results
        }

        fun getClikedItemList(): ArrayList<Int> {
            return clikedItemList
        }

        fun addToClikedItemList(id: Int) {
            //listede daha önce var mı kontrolü
            if(!isClikedItemList(id)) {
                clikedItemList.add(id)
            }
        }

        fun isClikedItemList(id: Int): Boolean {
            //clikeditemlist.contains(id)
            println("clikedItemList.contains(id)=" + clikedItemList.contains(id))
            return clikedItemList.contains(id)

        }

        fun getGameWitdID(gameId: Int): ResultGame? {
            var game = allGameList.find { oyun -> oyun.id == gameId }
            println("getGameWitdID")
            return game
        }
    }



}
