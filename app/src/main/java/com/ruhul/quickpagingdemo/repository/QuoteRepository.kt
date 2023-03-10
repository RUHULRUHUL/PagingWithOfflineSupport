package com.ruhul.quickpagingdemo.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ruhul.quickpagingdemo.models.QuoteList
import com.ruhul.quickpagingdemo.offlineDB.QuoteDB
import com.ruhul.quickpagingdemo.paging.QuoteRemoteMediator
import com.ruhul.quickpagingdemo.retrofit.QuoteAPI
import javax.inject.Inject

@ExperimentalPagingApi
class QuoteRepository @Inject constructor(
    private val quoteAPI: QuoteAPI,
    private val quoteDB: QuoteDB
) {
    fun getQuotes() = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 20,
        ),
        remoteMediator = QuoteRemoteMediator(quoteAPI, quoteDB),
        pagingSourceFactory = { quoteDB.quoteDao().getQuotes() }
    ).liveData

}