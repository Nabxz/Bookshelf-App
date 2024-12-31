package com.example.bookshelf

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.H
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.ui.BookShelfViewModel
import com.example.bookshelf.ui.screens.BookDetailsScreen
import com.example.bookshelf.ui.screens.BookshelfScreens
import com.example.bookshelf.ui.screens.HomeScreen
import com.example.compose.BookshelfTheme


enum class ScreenSizes {
    COMPACT,
    EXPANDED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact) {

    // Determine Phone/Tablet Mode
    val screenSize: ScreenSizes
    when(windowSize) {
        WindowWidthSizeClass.Compact -> {
            screenSize = ScreenSizes.COMPACT
        } else -> {
        screenSize = ScreenSizes.EXPANDED
        }
    }

    // ViewModel
    val bookShelfViewModel: BookShelfViewModel = viewModel(factory = BookShelfViewModel.Factory)

    // NavController
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BookshelfScreens.valueOf(
        backStackEntry?.destination?.route ?: BookshelfScreens.HOME.name
    )
    // Context
    val context = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.then (
            if(screenSize == ScreenSizes.COMPACT) {
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            } else { Modifier.padding() }
        ),
        topBar = {
            topAppBar(
                currentScreen = currentScreen,
                scrollBehavior = scrollBehavior,
                screenSize = screenSize,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateBack = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        Surface {


            NavHost(
                navController = navController,
                startDestination = BookshelfScreens.HOME.name,
                modifier = Modifier.padding(innerPadding)
            ) {

                composable(route = BookshelfScreens.HOME.name){

                    // Phone
                    if(screenSize == ScreenSizes.COMPACT) {
                        HomeScreen(
                            fetchJazzBooksState = bookShelfViewModel.fetchJazzBooksState,
                            onBookPressed = {
                                bookShelfViewModel.getJazzBookData(it)
                                navController.navigate(BookshelfScreens.BOOKDETAILS.name)
                            },
                            retryActon = bookShelfViewModel::getJazzBooks,
                            contentPadding = innerPadding
                        )

                    // Tablet
                    } else {

                        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                            Column(Modifier.weight(0.5f)) {
                                HomeScreen(
                                    fetchJazzBooksState = bookShelfViewModel.fetchJazzBooksState,
                                    onBookPressed = {
                                        bookShelfViewModel.getJazzBookData(it)
                                    },
                                    retryActon = bookShelfViewModel::getJazzBooks,
                                    contentPadding = innerPadding
                                )
                            }

                            Column(Modifier.weight(0.4f)) {
                                BookDetailsScreen(
                                    fetchJazzBookDataState = bookShelfViewModel.fetchJazzBookDataState,
                                    retryAction = bookShelfViewModel::getJazzBookData,
                                    context = context
                                )
                            }
                        }

                    }

                }

                composable(route = BookshelfScreens.BOOKDETAILS.name) {
                    BookDetailsScreen(
                        fetchJazzBookDataState = bookShelfViewModel.fetchJazzBookDataState,
                        retryAction = bookShelfViewModel::getJazzBookData,
                        context = context
                    )
                }
            }



        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(
    currentScreen: BookshelfScreens,
    scrollBehavior: TopAppBarScrollBehavior,
    screenSize: ScreenSizes,
    canNavigateBack: Boolean,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if(screenSize == ScreenSizes.COMPACT) {

        val topAppBarScrollBehavior = if (currentScreen != BookshelfScreens.BOOKDETAILS) scrollBehavior else null

        CenterAlignedTopAppBar(
            scrollBehavior = topAppBarScrollBehavior ,
            title = {
                if(!canNavigateBack) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                    )
                }
            },
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            navigationIcon = {
                if(canNavigateBack) {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        )
    }

}

@Preview
@Composable
fun PreviewBookshelfApp() {
    BookshelfTheme {
        BookshelfApp(WindowWidthSizeClass.Compact)
    }
}