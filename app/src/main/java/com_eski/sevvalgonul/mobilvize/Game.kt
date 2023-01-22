package com.sevvalgonul.mobilvize

import android.os.Parcelable

import kotlinx.parcelize.Parcelize


@Parcelize
data class Game(var image: Int, var name: String, var rate: Int, var genre: String
, var isFavourite: Boolean = false) : Parcelable {



}



