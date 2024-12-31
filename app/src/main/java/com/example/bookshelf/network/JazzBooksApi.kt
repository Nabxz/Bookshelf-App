package com.example.bookshelf.network

import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.FetchBookDetails
import com.example.bookshelf.model.JazzBook
import com.example.bookshelf.model.JazzBookSearchQuery
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JazzBooksApi {
    @GET("volumes?q=jazz+books")
    suspend fun getJazzBooks(): JazzBookSearchQuery

    @GET("volumes/{bookId}")
    suspend fun getJazzBookData(@Path("bookId") bookId: String): FetchBookDetails

}