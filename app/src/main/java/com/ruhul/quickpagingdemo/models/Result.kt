package com.ruhul.quickpagingdemo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Quotes")
data class Result(
    @PrimaryKey(autoGenerate = false)
    val _id: String,

    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int
)