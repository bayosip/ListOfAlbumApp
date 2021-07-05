package com.vertex5.jpmorganalbums.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vertex5.jpmorganalbums.App
import com.vertex5.jpmorganalbums.model.Repository
import com.vertex5.jpmorganalbums.model.data.Album
import kotlinx.coroutines.launch

class ListViewModel( app: Application): AndroidViewModel(app)  {

    val unfilteredAlbums: LiveData<List<Album>>
    private val repo: Repository

    init {
        repo = (app as App).repository
        unfilteredAlbums = repo.getAlbumData()
    }

    fun insert(album: Album) = viewModelScope.launch {

        repo.insert(album)
    }

    fun getUnfilteredAlbumListFromDatabase(){
        viewModelScope.launch {
            repo.getAllAlbums()
        }
    }

    fun getAlbumDataFromNetworkService(){
        viewModelScope.launch {
            repo.getAlbumDataFromNetwork()
        }
    }

    fun getAlbumListFromDbInSpecifiedOrder(order:OrderType){
        when(order){
            OrderType.ASC -> {
                viewModelScope.launch {
                    repo.sortByTitleAscending()
                }
            }
            OrderType.DESC->{
                viewModelScope.launch {
                    repo.sortByTitleDescending()
                }
            }
        }
    }

    enum class OrderType{
        ASC, DESC
    }
}