package com.example.bookshelf.fakeData

import com.example.bookshelf.data.NetworkJazzBooksRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkJazzbookRepositoryTest {

    @Test
    fun networkJazzbookRepository_getJazzBook_verifyJazzBookList() =
        runTest {
            val repository = NetworkJazzBooksRepository(FakeJazzbookApiService())
            assertEquals(FakeDataSource.jazzBookList, repository.getJazzBooks())
        }

    @Test
    fun networkJazzbookRepository_getJazzBookData_verifyJazzBookData() =
        runTest {
            val repository = NetworkJazzBooksRepository(FakeJazzbookApiService())
            assertEquals(FakeDataSource.jazzBookData.bookDetails, repository.getJazzBookData("notEmpty"))
            assertEquals(FakeDataSource.emptyJazzBookData.bookDetails, repository.getJazzBookData("empty"))
        }
}