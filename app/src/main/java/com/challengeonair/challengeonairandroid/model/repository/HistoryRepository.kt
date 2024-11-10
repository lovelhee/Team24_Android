package com.example.challengeonairandroid.model.repository

import android.util.Log
import com.example.challengeonairandroid.model.api.response.AllHistoriesResponse
import com.example.challengeonairandroid.model.api.response.HistoryResponse
import com.example.challengeonairandroid.model.api.service.HistoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject constructor(
    private val historyApi: HistoryApi
) {
    suspend fun getHistoryById(historyId: Long): HistoryResponse? = withContext(Dispatchers.IO) {
        try {
            val response = historyApi.getHistoryById("", historyId)  // 실제로는 토큰이 주입됨

            if (response.isSuccessful()) {
                response.data
            } else {
                Log.e("HistoryRepository", "getHistoryById API Error: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("HistoryRepository", "getHistoryById Exception: ${e.message}")
            null
        }
    }

    suspend fun getAllHistories(): AllHistoriesResponse? = withContext(Dispatchers.IO) {
        try {
            val response = historyApi.getAllHistories("")  // 실제로는 토큰이 주입됨

            if (response.isSuccessful()) {
                response.data
            } else {
                Log.e("HistoryRepository", "getAllHistories API Error: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("HistoryRepository", "getAllHistories Exception: ${e.message}")
            null
        }
    }
}