package com.sevvalgonul.mobilvize

import java.util.ArrayList

class GameModel {

    companion object {

        private var searchList: List<ResultGame> = emptyList()
        private var allGameList: ArrayList<ResultGame> = arrayListOf<ResultGame>()
        //private var tempGameList: List<ResultGame> = emptyList()


        fun addGameList(results: ArrayList<ResultGame>) {
            allGameList.addAll(results)
        }

        fun setGameList(results: ArrayList<ResultGame>) {
            allGameList = results
        }

        fun getGameList() : ArrayList<ResultGame> {
            return allGameList
        }

        /*fun addTempGameList(results: List<ResultGame>) {
            tempGameList.addAll(results)
        }

        fun setTempGameList(results: List<ResultGame>) {
            tempGameList = results
        }

        fun getTempGameList() : List<ResultGame> {
            return tempGameList
        }*/

        fun getSearchList(): List<ResultGame> {
            return searchList
        }

        fun setSearchList(results: List<ResultGame>) {
            searchList = results
        }


    }



}
