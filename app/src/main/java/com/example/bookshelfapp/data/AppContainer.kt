package com.example.bookshelfapp.data

import com.example.bookshelfapp.network.BooksApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Contrato del contenedor: qué dependencias ofrece al resto de la app.
 */
interface AppContainer {
    val booksRepository: BooksRepository
}

/**
 * Implementación por defecto: crea Retrofit, el servicio y el repositorio real.
 */
class DefaultAppContainer : AppContainer {

    // Debe terminar en "/" o Retrofit lanza una excepción al crearlo
    private val baseUrl = "https://openlibrary.org/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())   // JSON → data classes
        .build()

    private val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)          // Retrofit genera la implementación
    }

    override val booksRepository: BooksRepository by lazy {
        NetworkBooksRepository(retrofitService)
    }
}