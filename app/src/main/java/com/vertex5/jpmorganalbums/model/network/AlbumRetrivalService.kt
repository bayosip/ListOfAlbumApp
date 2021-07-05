package com.vertex5.jpmorganalbums.model.network

import com.vertex5.jpmorganalbums.model.data.Album
import retrofit2.Call
import retrofit2.http.GET

interface AlbumRetrivalService {

    @GET("/albums")
    suspend fun retrieveAlbumsFromNetwork(): List<Album>
}