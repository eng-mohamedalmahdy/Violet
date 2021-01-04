package com.dnteam.purble.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note")
data class Note(
        @PrimaryKey
        @ColumnInfo(name = "title")
        var noteTitle: String


        ,@ColumnInfo(name = "content")
        var noteContent: String):Serializable
