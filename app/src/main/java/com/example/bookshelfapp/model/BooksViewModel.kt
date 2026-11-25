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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    // Estado reactivo con StateFlow (como en NavigationApp)
    private val _uiState = MutableStateFlow<BooksUiState>(BooksUiState.Loading)
    val uiState: StateFlow<BooksUiState> = _uiState.asStateFlow()

    init {
        getBooks()
    }

    fun getBooks(query: String = DEFAULT_QUERY) {
        viewModelScope.launch {
            _uiState.value = BooksUiState.Loading
            _uiState.value = try {
                BooksUiState.Success(booksRepository.getBooks(query))
            } catch (e: IOException) {
                BooksUiState.Error      // Sin conexión, timeout...
            } catch (e: HttpException) {
                BooksUiState.Error      // El servidor respondió con error (404, 500...)
            }
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "fiction"
    }
}