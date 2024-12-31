package com.example.bookshelf.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.IntentCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.ui.FetchJazzBookDataState
import com.example.compose.BookshelfTheme

@Composable
fun BookDetailsScreen(
    fetchJazzBookDataState: FetchJazzBookDataState,
    retryAction: () -> Unit,
    context: Context,
) {
    
    when(fetchJazzBookDataState) {
        is FetchJazzBookDataState.NoBookSelected -> DisplayNoBookSelected()
        is FetchJazzBookDataState.Success -> DisplayBookData(fetchJazzBookDataState.jazzBookData, context)
        is FetchJazzBookDataState.Error -> DisplayErrorScreen(retryAction)
        is FetchJazzBookDataState.Loading -> DisplayLoadingScreen()
    }
}

@Composable
fun DisplayNoBookSelected() {

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.please_select_a_book),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun DisplayBookData(jazzBookData: BookDetails, context: Context) {
    val scrollState = rememberScrollState()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Upper Section(Header, Description)
        Column(Modifier.weight(0.9f)) {
            // Heading
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(20.dp),

                ) {

                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(getBookThumbnail(jazzBookData))
                        .crossfade(true).build(),
                    error = painterResource(R.drawable.broken_image),
                    placeholder = painterResource(R.drawable.loading),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f / 2f)
                        .align(Alignment.Bottom),
                    contentScale = ContentScale.Crop,
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        // Title
                        Text(
                            text = jazzBookData.title,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 24.sp
                        )

                        // Author
                        Text(
                            text = stringResource(R.string.by) + getBookAuthors(jazzBookData),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp
                        )
                    }

                    // PageCount
                    Text(
                        text = stringResource(R.string.page_count) + jazzBookData.pageCount,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                    )

                }

            } // End of Heading

            // Book Details
            Column(Modifier.padding(20.dp)) {

                // Categories
                Text(
                    text = stringResource(R.string.categories),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Text(
                    text = getBookCategories(jazzBookData),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                )

                // Publisher & Published Date
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(stringResource(R.string.published_by))
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(jazzBookData.publisher)
                        }
                    },
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(stringResource(R.string.published_date))
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(jazzBookData.publishedDate)
                        }
                    },
                    fontSize = 16.sp,
                )

                // Description
                Text(
                    text = stringResource(R.string.description),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Text(
                    text = jazzBookData.description,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .heightIn(min = 100.dp, max = 250.dp)
                        .verticalScroll(scrollState)
                        .background(Color.LightGray)
                        .padding(16.dp)
                )

            } // End Of Book details

        } // End of upper section

        // Lower Section - Read Book & share
        Row(modifier = Modifier
            .padding(20.dp)
            .weight(0.1f)) {
            Button(
                onClick = {},
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Read Now")
            }

            IconButton(onClick = {

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, jazzBookData.title)
                    putExtra(Intent.EXTRA_TEXT, "Check Out this book on Google books: ${jazzBookData.title}")
                }

                context.startActivity(
                    Intent.createChooser(
                        intent,
                        context.getString(R.string.app_name)
                    )
                )

            }) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = stringResource(R.string.share_button)
                )
            }
        }
    }

}




@Preview
@Composable
fun PreviewDisplayBookData() {
    BookshelfTheme {
        Scaffold { innerPadding ->
            DisplayBookData(jazzBookData = BookDetails(), context = LocalContext.current)
        }
    }
}