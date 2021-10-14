package com.dnteam.violet.data.di

import android.app.Application
import android.content.Context
import com.dnteam.violet.data.database.NotesDatabase
import com.dnteam.violet.data.database.SecretNotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
class VioletApplication : Application()


@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    @Singleton
    @Provides
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase =
        NotesDatabase.getDatabase(context)!!

    @Singleton
    @Provides
    fun provideNotesDao(notesDatabase: NotesDatabase): SecretNotesDao = notesDatabase.notesDao()
}

