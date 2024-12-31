package com.example.bookshelf.fakeData

import com.example.bookshelf.rules.TestDispatcherRule
import com.example.bookshelf.ui.BookShelfViewModel
import com.example.bookshelf.ui.FetchJazzBookDataState
import com.example.bookshelf.ui.FetchJazzBooksState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class JazzbookViewmodelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun jazzbookViewModel_getJazzBooks_verifyJazzbookUiStateSuccess() = runTest {
        val jazzBookViewModel = BookShelfViewModel(
            jazzBookRepository = FakeNetworkJazzbookRepository()
        )

        // Verify the state after fetching jazz books
        assertEquals(
            FetchJazzBooksState.Success(FakeDataSource.jazzBookList),
            jazzBookViewModel.fetchJazzBooksState
        )

        // Trigger fetching jazz book data with a valid ID
        jazzBookViewModel.getJazzBookData("notEmpty")

        // Verify the state after fetching jazz book data with a valid ID
        assertEquals(
            FetchJazzBookDataState.Success(FakeDataSource.jazzBookData.bookDetails),
            jazzBookViewModel.fetchJazzBookDataState
        )

        // Trigger fetching jazz book data with an empty ID
        jazzBookViewModel.getJazzBookData("empty")


        // Verify the state after fetching jazz book data with an empty ID
        assertEquals(
            FetchJazzBookDataState.Success(FakeDataSource.emptyJazzBookData.bookDetails),
            jazzBookViewModel.fetchJazzBookDataState
        )
    }


}