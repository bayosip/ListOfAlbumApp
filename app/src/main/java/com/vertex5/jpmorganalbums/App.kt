package com.vertex5.jpmorganalbums

import android.app.Application
import com.vertex5.jpmorganalbums.model.Repository
import com.vertex5.jpmorganalbums.model.data.AlbumDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App:Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AlbumDatabase.getDatabase(this)}
    val repository by lazy { Repository.getInstance(database.getDao(), applicationScope) }
}