package com.sevvalgonul.mobilvize.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Tihs is a Retrofit singleton class that enables us to make requests from everywhere in our code
class RetrofitInstance {

    companion object {
        private val retrofit by lazy {  // lazy means that we want to initialize only once what we will put in curly brackets
            val logging = HttpLoggingInterceptor()  // for logging the retrofit responses, we added dependency for that in gradle
            // we will attach these to our retrofit object in order to see which requests we are making and what the responses are
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging).build()

            // Return retrofit object:
            Retrofit.Builder()
                .baseUrl("https://api.rawg.io/api/")
                .addConverterFactory(GsonConverterFactory.create())  // For JSON conversion to Kotlin Object we use Gson
                .client(client)
                .build()

        }

        val api by lazy {  // this is the actual api object that we will be use to make actual network requests
            retrofit.create(GamesAPIService::class.java)
        }
    }
}