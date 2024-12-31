package com.example.bookshelf.ui.screens

import android.text.TextUtils.replace
import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.JazzBook

fun getBookThumbnail(book: JazzBook): String? {
    return book.volumeInfo.thumbnails?.thumbnail?.replace("http", "https")
}

fun getBookThumbnail(book: BookDetails): String? {
    return book.thumbnail?.thumbnail?.replace("http", "https")
}

fun getBookAuthors(jazzBookData: BookDetails): String {
    return jazzBookData.authors.joinToString(separator = ", ")
}

fun getBookCategories(jazzBookData: BookDetails): String {
    return jazzBookData.categories.joinToString(separator = " | ")
}