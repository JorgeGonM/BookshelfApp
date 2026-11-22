package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.ui.BooksUiState
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

/** Decide qué dibujar según el estado que le pasa el ViewModel */
@Composable
fun HomeScreen(
    uiState: BooksUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (uiState) {
        is BooksUiState.Loading -> LoadingScreen(modifier.padding(contentPadding))
        is BooksUiState.Success -> BooksGrid(uiState.books, modifier, contentPadding)
        is BooksUiState.Error -> ErrorScreen(onRetry, modifier.padding(contentPadding))
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
        ) {
        CircularProgressIndicator()
    }
}
@Composable
fun ErrorScreen(onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No se puedieron cargar los libros")
        Button(onClick = onRetry, modifier = Modifier.padding(top = 16.dp)){
            Text("Reiniciar")
        }
    }
}


@Composable
fun BooksGrid(
    books: List<Book>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),   // Se adapta al ancho de pantalla
        contentPadding = PaddingValues(
            start = 8.dp, end = 8.dp,
            top = contentPadding.calculateTopPadding() + 8.dp,
            bottom = contentPadding.calculateBottomPadding() + 8.dp
        ),
        modifier = modifier.fillMaxSize()
    ) {
        items(items = books, key = { it.id }) { book ->
            BookCard(book)
        }

    }

}


@Composable
fun BookCard(book: Book, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(4.dp)) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                error = painterResource(android.R.drawable.ic_menu_report_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.66f)                    // Proporción típica de portada
            )
            Text(
                text = book.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


// Datos de ejemplo: no tocan la red
private val previewBooks = List(6) { index ->
    Book(
        id = "/works/OL${index}W",
        title = "Libro de ejemplo número ${index + 1}",
        thumbnail = "https://covers.openlibrary.org/b/id/258027-M.jpg"
    )
}

@Preview(showBackground = true)
@Composable
fun BooksGridPreview() {
    BookshelfAppTheme {
        HomeScreen(
            uiState = BooksUiState.Success(previewBooks),
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    BookshelfAppTheme {
        HomeScreen(uiState = BooksUiState.Loading, onRetry = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    BookshelfAppTheme {
        HomeScreen(uiState = BooksUiState.Error, onRetry = {})
    }
}
