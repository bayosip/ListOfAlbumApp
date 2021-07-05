package com.vertex5.jpmorganalbums.model

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.vertex5.jpmorganalbums.model.data.Album
import com.vertex5.jpmorganalbums.model.data.AlbumDAO
import com.vertex5.jpmorganalbums.model.network.AlbumRetrivalService
import com.vertex5.jpmorganalbums.model.network.NetworkInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository private constructor(val dao:AlbumDAO, val scope: CoroutineScope) {


    private val albumList = mutableListOf<Album>()

    private val albumData = MutableLiveData<List<Album>>()

    companion object {
        // Singleton instantiation you already know and love
        @Volatile private var instance: Repository? = null
        private const val TAG = "Repository"

        fun getInstance(dao:AlbumDAO, scope: CoroutineScope):Repository {
            val temp = instance
            if (temp!=null)return temp
            synchronized(this) {
                instance = Repository(dao, scope)
                Log.d(TAG, "getInstance: $instance")
                return instance!!
            }
        }
    }

    private val albumNetwork = NetworkInteractor()

    //2
    private val callback = object : Callback<List<Album>> {
        override fun onFailure(call: Call<List<Album>>?, t: Throwable?) {
            Log.e("MainActivity", "Problem calling Github API {${t?.message}}")
        }

        override fun onResponse(call: Call<List<Album>>?, response: Response<List<Album>>?) {
            response?.isSuccessful.let {
                val resultList = response?.body()?: emptyList()
                if(resultList.isNotEmpty()) {
                    albumList.clear()
                    albumList.addAll(resultList)
                    albumData.value = albumList
                    for (album in resultList){
                        scope.launch(Dispatchers.IO) {
                            insert(album)
                        }
                    }
                }
            }
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(album: Album) {
        dao.insert(album)
    }

    fun getAlbumData() = albumData

    suspend fun getAllAlbums(){
        albumList.clear()
        albumList.addAll(dao.getAlbums().first())
        albumData.value = albumList
    }

    suspend fun getAlbumDataFromNetwork(){
        val resultList = albumNetwork.getRepositories()
        albumList.clear()
        albumList.addAll(resultList)
        albumData.value = albumList
        for (album in resultList){
            scope.launch(Dispatchers.IO) {
                insert(album)
            }
        }
    }

    suspend fun sortByTitleDescending(){
//        albumData.value = albumList.sortedByDescending {
//            it.title
//        }
        albumList.clear()
        albumList.addAll(dao.getAlbumsDescendingOrderTitle().first())
        albumData.value = albumList
    }

    suspend fun sortByTitleAscending(){
//       albumData.value = albumList.sortedBy { it.title }
        albumList.clear()
        albumList.addAll(dao.getAlbumsAscendingOrderTitle().first())
        albumData.value = albumList
    }

    suspend fun deleteAllInDb() {
        dao.deleteAll()
    }


}