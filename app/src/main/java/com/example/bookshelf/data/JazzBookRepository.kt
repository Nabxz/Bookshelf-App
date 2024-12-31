package com.example.bookshelf.data

import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.JazzBook
import com.example.bookshelf.model.JazzBookSearchQuery
import com.example.bookshelf.network.JazzBooksApi

interface JazzBookRepository {
    suspend fun getJazzBooks(): List<JazzBook>
    suspend fun getJazzBookData(bookID: String): BookDetails
}

class NetworkJazzBooksRepository(
    private val jazzBookApi: JazzBooksApi
): JazzBookRepository {

    override suspend fun getJazzBooks(): List<JazzBook> = jazzBookApi.getJazzBooks().items

    override suspend fun getJazzBookData(bookID: String): BookDetails = jazzBookApi.getJazzBookData(bookID).bookDetails
}