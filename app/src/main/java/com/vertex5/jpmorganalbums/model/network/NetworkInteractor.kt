package com.vertex5.jpmorganalbums.model.network

import com.vertex5.jpmorganalbums.model.data.Album
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkInteractor {

    private val service: AlbumRetrivalService

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(AlbumRetrivalService::class.java)
    }

    suspend fun getAlbums():List<Album> {
        val call = service.retrieveAlbumsFromNetwork()
        return call
    }
}