package com.example.bookshelfapp.data

import com.google.gson.annotations.SerializedName


/**
 * Respuesta de la búsqueda: GET search.json?q=...
 * 'docs' es nullable por precaución: Gson no respeta la null-safety de Kotlin.
 */
data class SearchResponse(
    val docs: List<BookDoc>?
)

/**
 * Cada resultado de la búsqueda (un libro).
 * Solo declaramos lo que usamos; Gson ignora el resto de campos.
 */
data class BookDoc(
    val key: String?,                                 // Ej: "/works/OL45804W"
    val title: String?,
    @SerializedName("cover_i") val coverId: Long?     // Nullable: hay libros sin portada
)