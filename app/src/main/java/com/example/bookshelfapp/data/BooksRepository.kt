package com.example.bookshelfapp.data

import com.example.bookshelfapp.model.Book
import com.example.bookshelfapp.network.BooksApiService

/**
 * Contrato del repositorio: lo que la app necesita de la capa de datos.
 * El ViewModel solo conoce esta interfaz, no de dónde vienen los datos.
 */
interface BooksRepository {
    suspend fun getBooks(query: String): List<Book>
}

/**
 * Implementación real: obtiene los libros de Open Library con Retrofit.
 * Recibe el servicio por constructor (inyección de dependencias).
 */
class NetworkBooksRepository(
    private val booksApiService: BooksApiService
) : BooksRepository {

    override suspend fun getBooks(query: String): List<Book> {
        return booksApiService.searchBooks(query)
            .docs
            .orEmpty()                                   // Si no hay resultados, lista vacía
            .mapNotNull { doc ->                         // Traduce BookDoc → Book
                val key = doc.key ?: return@mapNotNull null
                val coverId = doc.coverId ?: return@mapNotNull null   // Sin portada: se descarta
                Book(
                    id = key,
                    title = doc.title ?: "Sin título",
                    thumbnail = "https://covers.openlibrary.org/b/id/$coverId-M.jpg"
                )
            }
            .distinctBy { it.id }                        // Evita claves repetidas en la cuadrícula
    }
}