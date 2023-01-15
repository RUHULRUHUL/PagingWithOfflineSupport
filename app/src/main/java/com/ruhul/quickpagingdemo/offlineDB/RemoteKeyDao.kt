package com.ruhul.quickpagingdemo.offlineDB
import androidx.room.*
import com.ruhul.quickpagingdemo.models.QuoteRemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRemoteKey(remoteKeys:List<QuoteRemoteKey>)

    @Query("select * from QuoteRemoteKey where id =:id")
    fun getQuotesKey(id: String): QuoteRemoteKey

    @Query("delete from QuoteRemoteKey")
    fun deleteAllStudent()


}