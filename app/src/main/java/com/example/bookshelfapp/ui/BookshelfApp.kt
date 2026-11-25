package com.example.bookshelfapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelfapp.model.BooksViewModel
import com.example.bookshelfapp.ui.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text("Bookshelf") }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()) {
            // Instanciación directa
            val booksViewModel: BooksViewModel = viewModel()
            // Recolección del StateFlow
            val uiState by booksViewModel.uiState.collectAsStateWithLifecycle()
            HomeScreen(
                uiState = uiState,
                onRetry = { booksViewModel.getBooks() },
                contentPadding = innerPadding
            )
        }
    }
}