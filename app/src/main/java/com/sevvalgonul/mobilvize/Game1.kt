package com.sevvalgonul.mobilvize

import android.os.Parcelable

import kotlinx.parcelize.Parcelize


@Parcelize
data class Game1(var image: Int, var name: String, var rate: Int, var genre: String
                , var isFavourite: Boolean = false) : Parcelable {



}
