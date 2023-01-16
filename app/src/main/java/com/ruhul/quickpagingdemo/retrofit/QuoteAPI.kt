package com.ruhul.quickpagingdemo.retrofit

import com.ruhul.quickpagingdemo.models.QuoteList
import com.ruhul.quickpagingdemo.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteAPI {
    @GET(Constants.Quotes)
    suspend fun getQuotes(@Query("page") page: Int): QuoteList
}