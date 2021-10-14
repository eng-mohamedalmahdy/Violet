package com.dnteam.violet.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey
    @ColumnInfo(name = "title")
    var noteTitle: String,

    @ColumnInfo(name = "content")
    var noteContent: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) =
        with(parcel) {
            writeString(noteTitle)
            writeString(noteContent)
        }


    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel) = Note(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Note>(size)
    }

}
