package com.sevvalgonul.mobilvize

import android.app.Application
import android.os.Environment
import java.io.File
import java.util.*

class FavoriteModel {

    companion object {

        private var repository: GameRepository? = null
        private lateinit var myApp: Application

        //private lateinit var gameList: List<ResultGame>
        var favList: ArrayList<ResultGame> = arrayListOf<ResultGame>()
        private var myFile : File? = null
        var favGameIdList : ArrayList<Int> = arrayListOf<Int>()


        fun getFavoritesFile() : File {
            var name = "/Documents/Favoriler.txt"
            println("File Adi = $name")

            if (myFile == null) {
                println("myFile == null")
                myFile = File(Environment.getExternalStorageDirectory().absolutePath + name)
                val myFilePath = myFile!!.absolutePath.toString()
                println("myFile.FullPath = $myFilePath")
                myFile!!.createNewFile()
            }
            return myFile!!
        }

        fun isFavoritedGames(gameId: Int): Boolean {
            // Favori dosyasindaki butun numaralari oku.
            val gameIdList : ArrayList<Int>  = getFavoriIdList()
            var gameExist : Int? = gameIdList.find { oyunId -> oyunId.equals(gameId) }
            // var gameExist : Int? = GameModel.getGameWitdID(gameId)
            println("gameExist = " + gameExist)
            println()
            // Numaralari gameId ile karsilastir, var ise true don.
            if (gameExist == null) {
                return false
            }
            return true
        }

        fun getFavoriIdList(): ArrayList<Int> {
            if (favGameIdList.isEmpty()) {
                var favFile = getFavoritesFile()

                val scan = Scanner(favFile)
                println("scan.delimiter=$(scan.delimiter())")
                while (scan.hasNextLine()) {
                    val line = scan.nextLine()
                    var lista = ArrayList<String>()
                    lista = line.split(";") as ArrayList<String>
                    if(lista.size > 1){
                        lista.removeAt(lista.size-1)
                        //println("listaSize=" + lista.size + " --> " + lista.get(0) + " and " + lista.get(1) + " and " + lista.get(2))
                        favGameIdList = lista.map { it.toInt() } as ArrayList<Int>
                    }

                }
            }

            return favGameIdList
        }

        fun addFavoritedList(gameId: Int) {
            if (!isFavoritedGames(gameId)){
                getFavoriIdList().add(gameId)
            }
        }

        fun getFavoritedList(): List<ResultGame> {
            //favlistteki id olan oyunları gamelşstte bulup saklayacak
            // ve rv adaptörüne gönderebildiğimiz bir favlist return edicek
            //val resultGame : ResultGame


            favList.clear()

            val tempGameList = GameModel.getGameList()
            println("tempGameList.size=" + tempGameList.size + "favList.size=" + favList.size )
            for (eachFavId in favGameIdList){
                for (eachGame in tempGameList) {

                    // println("eachGame.id= " + eachGame.id + " eachFavId= " + eachFavId)

                    if ( eachFavId == eachGame.id ){
                        favList.add(eachGame)
                    }
                }
            }

            return favList.toList()
        }

        fun writeFavoritedIdListToFile() {
            var favString : String = getFavoriIdList().joinToString(separator = ";")
            favString += ";"
            println("writeFavoritedIdListToFile=" + favString)
            getFavoritesFile().writeText(favString)
        }

        fun deleteFavoritedList(gameId: Int) {

            getFavoriIdList().remove(gameId)
        }

        fun deleteFavoritedListByIndex(position: Int) {
            val favID = favList.get(position).id
            println("position at ="+ position + " silindi favList.id-name=" + favID + "-" + favList.get(position).name)
            //getFavoriIdList().removeAt(position)
            getFavoriIdList().remove(favID)
        }

        fun getInstance(app: Application) {
            if (repository == null) {
                repository = GameRepository(app)
                getFavoriIdList()
            }
        }

        fun insertFavoriGame(game: Game?) {
            if (game != null) {
                repository?.insert(game)
            }
        }
    }
}