package com.example.bookshelf.fakeData

import com.example.bookshelf.model.FetchBookDetails
import com.example.bookshelf.model.JazzBookSearchQuery
import com.example.bookshelf.network.JazzBooksApi

class FakeJazzbookApiService: JazzBooksApi  {

    override suspend fun getJazzBooks(): JazzBookSearchQuery {
        return FakeDataSource.fetchJazzBook
    }

    override suspend fun getJazzBookData(bookId: String): FetchBookDetails {
        if(bookId.equals("notEmpty", ignoreCase = true)) {
            return FakeDataSource.jazzBookData
        }

        return FakeDataSource.emptyJazzBookData // Test when an emptybook is passed back
    }

}