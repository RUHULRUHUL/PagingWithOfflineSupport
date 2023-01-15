package com.ruhul.quickpagingdemo.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ruhul.quickpagingdemo.models.QuoteRemoteKey
import com.ruhul.quickpagingdemo.offlineDB.QuoteDB
import com.ruhul.quickpagingdemo.retrofit.QuoteAPI
import com.ruhul.quickpagingdemo.models.Result

@OptIn(ExperimentalPagingApi::class)
class QuoteRemoteMediator(
    private val quoteAPI: QuoteAPI,
    private val quoteDB: QuoteDB,
) : RemoteMediator<Int, Result>() {

    private val quoteDao = quoteDB.quoteDao()
    private val remoteKeyDao = quoteDB.remoteKeyDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nexKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.preKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nexKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = quoteAPI.getQuotes(currentPage)
            val endOfPaginationReached = response.totalPages == currentPage

            val prePage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            quoteDB.withTransaction {
                quoteDao.insertQuotes(response.results)
                val keys = response.results.map {
                    QuoteRemoteKey(
                        id = it._id,
                        preKey = prePage,
                        nexKey = nextPage
                    )
                }
                remoteKeyDao.insertAllRemoteKey(keys)
            }
            MediatorResult.Success(endOfPaginationReached)

        } catch (e: Exception) {
            MediatorResult.Error(e)

        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Result>
    ): QuoteRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?._id?.let { id ->
                remoteKeyDao.getQuotesKey(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Result>
    ): QuoteRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { quote ->
                remoteKeyDao.getQuotesKey(id = quote._id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Result>
    ): QuoteRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { quote ->
                remoteKeyDao.getQuotesKey(id = quote._id)
            }
    }


}