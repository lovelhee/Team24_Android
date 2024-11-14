package com.okaka.challengeonairandroid.model.repository

import android.util.Log
import com.okaka.challengeonairandroid.model.api.response.UserProfileResponse
import com.okaka.challengeonairandroid.model.api.response.UserProfileUpdateRequest
import com.okaka.challengeonairandroid.model.api.response.UserProfileUpdateResponse
import com.okaka.challengeonairandroid.model.api.service.UserProfileApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileRepository @Inject constructor(
    private val userProfileApi: UserProfileApi  // HistoryApi -> UserProfileApi로 수정
) {
    suspend fun getUserProfile(): UserProfileResponse? = withContext(Dispatchers.IO) {
        try {
            val response = userProfileApi.getUserProfile("")  // 실제로는 토큰이 주입됨

            if (response.isSuccessful()) {
                response.data
            } else {
                Log.e("UserProfileRepository", "getUserProfile API Error: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("UserProfileRepository", "getUserProfile Exception: ${e.message}")
            null
        }
    }

    suspend fun updateUserProfile(request: UserProfileUpdateRequest): UserProfileUpdateResponse? = withContext(Dispatchers.IO) {
        try {
            val response = userProfileApi.updateUserProfile("", request)  // 실제로는 토큰이 주입됨

            if (response.isSuccessful()) {
                response.data
            } else {
                Log.e("UserProfileRepository", "updateUserProfile API Error: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("UserProfileRepository", "updateUserProfile Exception: ${e.message}")
            null
        }
    }

    suspend fun getSpecificUserProfile(userId: String): UserProfileSpecificResponse? = withContext(Dispatchers.IO) {
        try {
            val response = userProfileApi.getSpecificUserProfile("", userId)  // 실제로는 토큰이 주입됨

            if (response.isSuccessful()) {
                response.data
            } else {
                Log.e("UserProfileRepository", "getSpecificUserProfile API Error: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("UserProfileRepository", "getSpecificUserProfile Exception: ${e.message}")
            null
        }
    }
}