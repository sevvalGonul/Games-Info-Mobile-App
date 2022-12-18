package com.sevvalgonul.mobilvize.api

import com.sevvalgonul.mobilvize.GamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "3be8af6ebf124ffe81d90f514e59856c"

interface GamesAPIService {
    // These are network call functions, so we want to execute them asnychronously.
    // In order to do that we use Coroutines
    // Make these functions able to be us in coroutine we need to make them suspend functions

    @GET("games")
    suspend fun getAllGames(  // Query annotation is for request parameters. They are part of the request
        @Query("key") key: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10
    ) : Response<GamesResponse>  // ??? Sadece GamesResponse da olabilir

    @GET("games")
    suspend fun searchForGames(
        @Query("key") key: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("search") searchStr : String? = "gtav"  // Tipi nullable String olmayabilir, default value'yu sonradan silebilirsin
    ) : Response<GamesResponse>  // ??? Sadece GamesResponse da olabilir
}