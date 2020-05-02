package com.example.roomtesting.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "note")
data class Note (

    @ColumnInfo(name = "title") val title: String,

    @ColumnInfo(name = "body") val body: String,

    @ColumnInfo(name = "noteDate") val _date : String,

    @ColumnInfo(name = "codecolor") val codecolor: Int

) : Serializable{
    @PrimaryKey(autoGenerate = true) var id : Int = 0


}