package com.sevvalgonul.mobilvize

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "3be8af6ebf124ffe81d90f514e59856c"
private val BASE_URL = "https://api.rawg.io/api/"
interface GamesApiService {
    @GET("games")
    fun getGames(
        @Query("key") key: String = API_KEY,
        @Query("page") page: Int = 1,  // That will later be helpfull to paginate our request because we don't get all the games at once(that would be too much data at once)
        @Query("page_size") pageSize: Int = 10
    ) : Call<GamesResponse>

    @GET("games/{id}")
    fun getGamesDetail(
        @Path("id") id: Int,
        @Query("key") key: String = API_KEY,
    ) : Call<DetailResponse>

    @GET("games")
    fun search(
        @Query("search") searchedText: String,
        @Query("key") key: String = API_KEY
    ) : Call<GamesResponse>

    companion object {
        private var apiService: GamesApiService? = null
        fun getInstance() : GamesApiService {
            if(apiService == null) {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

                val retrofit = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(BASE_URL)
                    .build()

                apiService = retrofit.create(GamesApiService::class.java)
            }
            return apiService!!
        }
    }
}