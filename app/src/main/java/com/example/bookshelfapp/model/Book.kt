package com.example.bookshelfapp.model

/**
 * Modelo que usan el repositorio, el ViewModel y la UI.
 *
 * Es el equivalente a 'MarsPhoto' del proyecto de Mars Photos.
 */
data class Book(
    val id: String,          // Sirve de clave única en la cuadrícula (LazyVerticalGrid)
    val title: String,       // Texto bajo la portada
    val thumbnail: String    // URL de la portada, ya convertida a https
)