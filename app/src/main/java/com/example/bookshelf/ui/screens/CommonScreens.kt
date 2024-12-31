package com.example.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelf.R

@Composable
fun DisplayErrorScreen(
    retryActon: () -> Unit,
    modifier: Modifier = Modifier.size(140.dp)
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.connection_error),
            contentDescription = stringResource(R.string.connection_error)
        )

        Button(
            onClick = retryActon,
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
fun DisplayLoadingScreen(modifier: Modifier = Modifier.size(140.dp)) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Preview
@Composable
fun PreviewDisplayErrorScreen() {
    DisplayErrorScreen({})
}

@Preview
@Composable
fun PreviewLoadingScreen() {
    DisplayLoadingScreen()
}