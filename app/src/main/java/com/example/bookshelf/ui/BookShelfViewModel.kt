package com.example.bookshelf.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.bookshelf.JazzApplication
import com.example.bookshelf.data.JazzBookRepository
import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.JazzBook
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface FetchJazzBooksState {
    data class Success(val jazzBookList: List<JazzBook>) : FetchJazzBooksState
    object Error : FetchJazzBooksState
    object Loading: FetchJazzBooksState
}

sealed interface FetchJazzBookDataState {
    data class Success(val jazzBookData: BookDetails) : FetchJazzBookDataState
    object NoBookSelected: FetchJazzBookDataState
    object Error : FetchJazzBookDataState
    object Loading: FetchJazzBookDataState
}

class BookShelfViewModel(private val jazzBookRepository: JazzBookRepository): ViewModel() {

    var fetchJazzBooksState: FetchJazzBooksState by mutableStateOf(FetchJazzBooksState.Loading)
        private set

    var fetchJazzBookDataState: FetchJazzBookDataState by mutableStateOf(FetchJazzBookDataState.NoBookSelected)
        private set

    init {
        getJazzBooks()
    }

    fun getJazzBooks() {

        viewModelScope.launch {
            fetchJazzBooksState = FetchJazzBooksState.Loading
            fetchJazzBooksState = try {
                FetchJazzBooksState.Success(jazzBookRepository.getJazzBooks())
            } catch (e: IOException) {
                FetchJazzBooksState.Error
            } catch (e: HttpException) {
                FetchJazzBooksState.Error
            }

        }
    }

    fun getJazzBookData(bookID: String = "") {

        if (bookID.isBlank()) {
            fetchJazzBookDataState = FetchJazzBookDataState.Error
        } else {

            viewModelScope.launch {
                fetchJazzBookDataState = FetchJazzBookDataState.Loading
                fetchJazzBookDataState = try {
                    FetchJazzBookDataState.Success(jazzBookRepository.getJazzBookData(bookID))
                } catch (e: IOException) {
                    FetchJazzBookDataState.Error
                } catch (e: HttpException) {
                    FetchJazzBookDataState.Error
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as JazzApplication)
                val jazzBookRepository = application.container.jazzBookRepository
                BookShelfViewModel(jazzBookRepository = jazzBookRepository)
            }
        }
    }
}

