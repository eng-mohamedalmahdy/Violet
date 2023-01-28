package com.lightfeather.violet.data.di

import android.content.Context
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.lightfeather.violet.data.database.NotesDatabase
import com.lightfeather.violet.data.database.SecretNotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@HiltAndroidApp
class VioletApplication : LocalizationApplication() {
    override fun getDefaultLanguage(base: Context): Locale = Locale.getDefault()
}


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase =
        NotesDatabase.getDatabase(context)!!

    @Singleton
    @Provides
    fun provideNotesDao(notesDatabase: NotesDatabase): SecretNotesDao = notesDatabase.notesDao()
}

