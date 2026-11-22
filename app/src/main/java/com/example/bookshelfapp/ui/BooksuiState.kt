package com.example.bookshelfapp.ui

import com.example.bookshelfapp.model.Book

/**
 * Los tres estados posibles de la pantalla principal.
 */
sealed interface BooksUiState {
    /** Búsqueda correcta: lleva los libros a mostrar */
    data class Success(val books: List<Book>) : BooksUiState

    /** Petición en curso */
    object Loading : BooksUiState

    /** Fallo de red o del servidor */
    object Error : BooksUiState
}