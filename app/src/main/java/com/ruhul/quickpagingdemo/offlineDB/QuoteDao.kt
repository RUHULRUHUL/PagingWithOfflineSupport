package com.ruhul.quickpagingdemo.offlineDB

import androidx.paging.PagingSource
import com.ruhul.quickpagingdemo.models.Result
import androidx.room.*

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(lists: List<Result>)

    @Query("delete from Quotes")
    suspend fun deleteQuotes()

    @Query("SELECT * FROM Quotes")
    fun getQuotes(): PagingSource<Int, Result>

}