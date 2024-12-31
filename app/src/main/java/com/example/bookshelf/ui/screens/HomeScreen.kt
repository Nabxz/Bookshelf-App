package com.example.bookshelf.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.BookThumbnail
import com.example.bookshelf.model.JazzBook
import com.example.bookshelf.model.VolumeInfo
import com.example.bookshelf.ui.FetchJazzBooksState

@Composable
fun HomeScreen(
    fetchJazzBooksState: FetchJazzBooksState,
    onBookPressed: (String) -> Unit,
    retryActon: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    when(fetchJazzBooksState) {
        is FetchJazzBooksState.Success -> DisplayJazzBooks(
            books = fetchJazzBooksState.jazzBookList,
            onBookPressed = onBookPressed,
            modifier = modifier,
            contentPadding = contentPadding
        )
        FetchJazzBooksState.Loading -> DisplayLoadingScreen()
        FetchJazzBooksState.Error -> DisplayErrorScreen(retryActon = retryActon)
    }

}


@Composable
fun DisplayJazzBooks(
    books: List<JazzBook>,
    modifier: Modifier = Modifier,
    onBookPressed: (String) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 6.dp, vertical = 6.dp)
    ) {
        items(items = books, key = { books -> books.bookID }) { book ->

            BookCard(book, onBookPressed)
        }
    }
}

@Composable
fun BookCard(
    book: JazzBook,
    onBookPressed: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .aspectRatio(1f / 2f)
            .padding(4.dp)

    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(getBookThumbnail(book))
                .crossfade(true).build(),
            error = painterResource(R.drawable.broken_image),
            placeholder = painterResource(R.drawable.loading),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().clickable { onBookPressed(book.bookID) },
            contentScale = ContentScale.Crop,
        )
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    Scaffold { innerPadding ->

        HomeScreen(
            fetchJazzBooksState = FetchJazzBooksState.Success(listOf(JazzBook("2", VolumeInfo(
                BookThumbnail("a", "b")
            )))),
            onBookPressed = {},
            retryActon = {},
            contentPadding = innerPadding
        )
    }

}