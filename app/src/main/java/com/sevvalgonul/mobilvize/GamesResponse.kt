package com.sevvalgonul.mobilvize

data class GamesResponse(  // Model classı
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<ResultGame>
)