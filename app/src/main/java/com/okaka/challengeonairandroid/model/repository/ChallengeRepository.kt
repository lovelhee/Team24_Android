package com.okaka.challengeonairandroid.model.repository

import android.util.Log
import com.okaka.challengeonairandroid.model.api.service.ChallengeApi
import com.okaka.challengeonairandroid.model.api.response.ChallengeCreationResponse
import com.okaka.challengeonairandroid.model.api.response.ChallengeDeletionResponse
import com.okaka.challengeonairandroid.model.api.response.ChallengeReservationResponse
import com.okaka.challengeonairandroid.model.api.response.ChallengeResponse
import com.okaka.challengeonairandroid.model.api.response.toChallenge
import com.okaka.challengeonairandroid.model.data.auth.TokenManager
import com.okaka.challengeonairandroid.model.data.entity.Challenge
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Singleton
class ChallengeRepository @Inject constructor(
    private val challengeApi: ChallengeApi,
    private val tokenManager: TokenManager
) {

    private suspend fun getAuthorizationHeader(): String {
        val accessToken = tokenManager.getAccessToken()
        Log.d("ChallengeRepository", "${accessToken}")
        return "Authorization : Bearer $accessToken"
    }

    suspend fun getAllChallenges(): List<Challenge>? = withContext(Dispatchers.IO) {
        try {
            val response = challengeApi.getAllChallenges(getAuthorizationHeader())
            if (response.isSuccessful()) {
                Log.d("ChallengeRepository", "${response}")
                response.data?.map { it.toChallenge() } ?: emptyList()
            } else {
                Log.e("ChallengeRepository", "getAllChallenges API Error: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("ChallengeRepository", "getAllChallenges Exception: ${e.message}")
            null
        }
    }

    suspend fun getChallengeDetails(challengeId: Long): ChallengeResponse? = withContext(Dispatchers.IO) {
        try {
            val date = getCurrentDateTime()
            val response = challengeApi.getChallengeDetails(getAuthorizationHeader(), challengeId, date)

            if (response.isSuccessful()) {
                response.data
            } else {
                Log.e("ChallengeRepository", "getChallengeDetails API Error: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("ChallengeRepository", "getChallengeDetails Exception: ${e.message}")
            null
        }
    }

    suspend fun createChallenge(imagePart: MultipartBody.Part, challenge: Challenge): ChallengeCreationResponse? = withContext(Dispatchers.IO) {
        try {

            val response = challengeApi.createChallenge(getAuthorizationHeader(), imagePart, challenge)

            if (response.isSuccessful()) {
                response.data
            } else {
                Log.e("ChallengeRepository", "createChallenge API Error: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("ChallengeRepository", "createChallenge Exception: ${e.message}")
            null
        }
    }

    suspend fun deleteChallenge(challengeId: Long): ChallengeDeletionResponse? = withContext(Dispatchers.IO) {
        try {
            val response = challengeApi.deleteChallenge(getAuthorizationHeader(), challengeId)

            if (response.isSuccessful()) {
                response.data
            } else {
                Log.e("ChallengeRepository", "deleteChallenge API Error: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("ChallengeRepository", "deleteChallenge Exception: ${e.message}")
            null
        }
    }

    suspend fun reserveChallenge(challengeId: Long): ChallengeReservationResponse? = withContext(Dispatchers.IO) {
        try {
            val response = challengeApi.reserveChallenge(getAuthorizationHeader(), challengeId)

            if (response.isSuccessful()) {
                response.data
            } else {
                Log.e("ChallengeRepository", "reserveChallenge API Error: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("ChallengeRepository", "reserveChallenge Exception: ${e.message}")
            null
        }
    }

    private fun getCurrentDateTime(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm"))
    }
}