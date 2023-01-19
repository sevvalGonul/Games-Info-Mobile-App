package com.sevvalgonul.mobilvize

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Be able to save a game in our database we need to anotate this class with @Entity
@Parcelize
@Entity(tableName = "favourites")  // This class is a table in our database. ResultGame is a table, val's are our columns and each row of table will represent an entry, aka a favgame
data class ResultGame(  // Each game in the GameResponse, notice that it doesn't contain genres
    @SerializedName("id")
    val uid: Int,  // ???
    val name: String,
    //val added: Int,
    //val added_by_status: AddedByStatus,
    val background_image: String,
    //val esrb_rating: EsrbRating,
    val metacritic: Int,
    //val platforms: List<Platform>,
    //val playtime: Int,
    //val rating: Int,
    //val rating_top: Int,
    //val ratings: Ratings,
    //val ratings_count: Int,
    //val released: String,
    //val reviews_text_count: String,
    //val slug: String,
    //val suggestions_count: Int,
    //val tba: Boolean,
    //val updated: String
    //val isFavourited : Boolean = false  // ??? Burada apiden gelen response'a eşleşmeyecek ekstra bir özellik tanımladık. Bu probleme yol açar mı
    @PrimaryKey(autoGenerate = true)  // ???
    var favId : Int? = null  // If the game is not favourited favId will be null
) : Parcelable