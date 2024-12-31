package com.example.bookshelf.data

import com.example.bookshelf.network.JazzBooksApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val jazzBookRepository: JazzBookRepository
}

class DefaultAppContainer: AppContainer {

    val json = Json {
        ignoreUnknownKeys = true // Ignore unknown keys in JSON
    }

    private val baseUrl = "https://www.googleapis.com/books/v1/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: JazzBooksApi by lazy {
        retrofit.create(JazzBooksApi::class.java)
    }

    override val jazzBookRepository: JazzBookRepository by lazy {
        NetworkJazzBooksRepository(retrofitService)
    }


}