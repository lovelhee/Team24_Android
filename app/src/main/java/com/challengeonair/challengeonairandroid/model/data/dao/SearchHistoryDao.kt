package com.challengeonair.challengeonairandroid.model.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challengeonair.challengeonairandroid.model.data.entity.SearchHistory

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistory)

    @Query("SELECT * FROM search_history ORDER BY timestamp DESC")
    suspend fun getAllSearchHistories(): List<SearchHistory>
}