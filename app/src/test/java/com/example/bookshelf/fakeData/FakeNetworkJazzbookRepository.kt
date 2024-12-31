package com.example.bookshelf.fakeData

import com.example.bookshelf.data.JazzBookRepository
import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.JazzBook

class FakeNetworkJazzbookRepository : JazzBookRepository {

    override suspend fun getJazzBooks(): List<JazzBook> {
        return FakeDataSource.jazzBookList
    }

    override suspend fun getJazzBookData(bookID: String): BookDetails {
        if(bookID.equals("notEmpty", ignoreCase = true)) {
            return FakeDataSource.jazzBookData.bookDetails
        }

        return FakeDataSource.emptyJazzBookData.bookDetails // Test when an emptybook is passed back
    }
}