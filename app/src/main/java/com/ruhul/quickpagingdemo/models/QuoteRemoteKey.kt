package com.ruhul.quickpagingdemo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuoteRemoteKey")
data class QuoteRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,

    val preKey: Int?,
    val nexKey: Int?,
)