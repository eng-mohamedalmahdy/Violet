package com.dnteam.violet.models

import android.os.Parcel
import android.os.Parcelable
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
        var noteContent: String):Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString()!!,
                parcel.readString()!!)

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(noteTitle)
                parcel.writeString(noteContent)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Note> {
                override fun createFromParcel(parcel: Parcel): Note {
                        return Note(parcel)
                }

                override fun newArray(size: Int): Array<Note?> {
                        return arrayOfNulls(size)
                }
        }
}
