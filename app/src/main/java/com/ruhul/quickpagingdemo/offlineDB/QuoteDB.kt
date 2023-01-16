package com.ruhul.quickpagingdemo.offlineDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ruhul.quickpagingdemo.models.QuoteRemoteKey
import com.ruhul.quickpagingdemo.models.Result

@Database(entities = [Result::class, QuoteRemoteKey::class], version = 1, exportSchema = false)
abstract class QuoteDB : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}