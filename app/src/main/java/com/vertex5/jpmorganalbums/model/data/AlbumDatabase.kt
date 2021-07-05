package com.vertex5.jpmorganalbums.model.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Album::class], version = 1)
abstract class AlbumDatabase: RoomDatabase() {

    abstract fun getDao():AlbumDAO


    companion object {
        @Volatile
        private var INSTANCE: AlbumDatabase? = null
        fun getDatabase(context: Context, ): AlbumDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlbumDatabase::class.java,
                    "album_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}