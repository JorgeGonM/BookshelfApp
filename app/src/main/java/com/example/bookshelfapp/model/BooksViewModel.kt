package com.example.bookshelfapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookshelfApplication
import com.example.bookshelfapp.data.BooksRepository
import com.example.bookshelfapp.ui.BooksUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    /** Estado actual; la UI lo observa y se redibuja cuando cambia */
    var uiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    init {
        getBooks()
    }

    fun getBooks(query: String = DEFAULT_QUERY) {
        viewModelScope.launch {
            uiState = BooksUiState.Loading
            uiState = try {
                BooksUiState.Success(booksRepository.getBooks(query))
            } catch (e: IOException) {
                BooksUiState.Error      // Sin conexión, timeout...
            } catch (e: HttpException) {
                BooksUiState.Error      // El servidor respondió con error (404, 500...)
            }
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "romance"

        /** Le enseña a Android cómo construir el ViewModel con su repositorio */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BookshelfApplication
                BooksViewModel(application.container.booksRepository)
            }
        }
    }
}