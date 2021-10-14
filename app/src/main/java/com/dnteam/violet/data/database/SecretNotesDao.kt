package com.dnteam.violet.data.database

import androidx.room.*
import com.dnteam.violet.data.models.Note

@Dao
interface SecretNotesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNote(note: Note): Long

    @Query("SELECT * FROM note WHERE title = :title")
    suspend fun getNote(title: String): Note

    @Query("SELECT title from note")
    suspend fun getAllKeys(): List<String>

    @Query(value = "UPDATE note SET title =:newTitle , content = :content WHERE title = :oldTitle ")
    suspend fun updateNote(oldTitle: String, newTitle: String, content: String): Int

    @Query("DELETE FROM  note WHERE title = :title")
    suspend fun deleteNote(title: String)

}