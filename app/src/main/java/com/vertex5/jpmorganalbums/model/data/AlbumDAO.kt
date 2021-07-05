package com.vertex5.jpmorganalbums.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface AlbumDAO {

    @Query("SELECT * FROM albums")
    fun getAlbums(): Flow<List<Album>>

    @Query("SELECT * FROM albums ORDER BY title ASC")
    fun getAlbumsAscendingOrderTitle():Flow<List<Album>>

    @Query("SELECT * FROM albums ORDER BY title DESC")
    fun getAlbumsDescendingOrderTitle():Flow<List<Album>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: Album)

    @Query("DELETE FROM albums")
    suspend fun deleteAll()
}