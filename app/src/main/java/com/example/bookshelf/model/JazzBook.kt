package com.example.bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JazzBookSearchQuery(
    val items: List<JazzBook>
)

@Serializable
data class JazzBook(
    @SerialName("id") val bookID: String,
    @SerialName("volumeInfo") val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    @SerialName("imageLinks") val thumbnails: BookThumbnail? = null
)

@Serializable
data class FetchBookDetails(
    @SerialName("volumeInfo") val bookDetails: BookDetails
)

@Serializable
data class BookDetails(
    val title: String = "Unknown",
    @SerialName("imageLinks") val thumbnail: BookThumbnail? = null,
    val authors: List<String> = listOf("Unknown Author"),
    val pageCount: Int = 0,
    val categories: List<String> = listOf("Miscellaneous"),
    val publisher: String = "Unknown",
    val publishedDate: String = "Unknown",
    val description: String = "No description",
)


@Serializable
data class BookThumbnail(
    val smallThumbnail: String,
    val thumbnail: String
)
