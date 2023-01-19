package com.ruhul.quickpagingdemo.offlineDB

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): QuoteDB {
        return Room.databaseBuilder(context, QuoteDB::class.java, "QuoteDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}