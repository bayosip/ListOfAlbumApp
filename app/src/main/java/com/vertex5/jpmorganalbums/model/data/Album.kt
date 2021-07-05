package com.vertex5.jpmorganalbums.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id:Int,
    @ColumnInfo(name = "userId")
    @SerializedName("userId")
    val userId: Int,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title:String)
