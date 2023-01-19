package com.sevvalgonul.mobilvize

// A class that represents kotlin objects for the response of the API we use
data class GamesResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<ResultGame>
)