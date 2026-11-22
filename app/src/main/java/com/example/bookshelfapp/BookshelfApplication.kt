package com.example.bookshelfapp

import android.app.Application
import com.example.bookshelfapp.data.AppContainer
import com.example.bookshelfapp.data.DefaultAppContainer

class BookshelfApplication : Application() {

    /** Contenedor accesible para el resto de las clases */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}