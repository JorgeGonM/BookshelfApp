package com.example.bookshelfapp.network

import com.example.bookshelfapp.data.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Describe los endpoints de Open Library que usa la app.
 * Retrofit genera la implementación a partir de esta interfaz.
 */
interface BooksApiService{

    /**
    * GET https://openlibrary.org/search.json?q=...&fields=...&limit=...
    */

    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("fields") fields: String = "key,title,cover_i",
        @Query("limit") limit: Int = 20
    ): SearchResponse
}
