package com.sevvalgonul.mobilvize

import com.squareup.moshi.Json

data class ResultGame (
    //@Json(name = "id")
    val id: Int,
    val name: String,
    val background_image: String,
    val metacritic: Int?,
    val genres : List<GenreNames>,
)