package com.example.bookshelf.fakeData

import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.BookThumbnail
import com.example.bookshelf.model.FetchBookDetails
import com.example.bookshelf.model.JazzBook
import com.example.bookshelf.model.JazzBookSearchQuery
import com.example.bookshelf.model.VolumeInfo

object FakeDataSource {

    const val bookIDOne = "validBookID1"
    const val bookIDTwo = "validBookID2"
    const val bookThumbnailOne = "validBookThumbnail1"
    const val bookThumbnailTwo = "validBookThumbnail2"
    val dummyVolumeInfo = VolumeInfo(thumbnails = BookThumbnail(bookThumbnailOne, bookThumbnailTwo))

    const val bookTitle = "validBookTitle"
    val authors = listOf("validAuthor1", "validAuthor2")
    const val pageCount = 10
    const val publisher = "validPublisher"
    const val publishedDate = "validPublishedDate"
    const val description = "validDescription"

    val jazzBookList = listOf(
        JazzBook(
            bookID = bookIDOne,
            volumeInfo = dummyVolumeInfo
        ),
        JazzBook(
            bookID = bookIDTwo,
            volumeInfo = dummyVolumeInfo
        ),
    )

    val emptyBookDetails = BookDetails()
    val bookDetails = BookDetails(
        title = bookTitle,
        thumbnail = BookThumbnail(bookThumbnailOne, bookThumbnailTwo),
        authors = authors,
        pageCount = pageCount,
        publisher = publisher,
        publishedDate = publishedDate,
        description = description
    )

    // Data To Test
    val fetchJazzBook = JazzBookSearchQuery(jazzBookList)
    val emptyJazzBookData = FetchBookDetails(emptyBookDetails)
    val jazzBookData = FetchBookDetails(bookDetails)
}