package com.okaka.challengeonairandroid.model.repository

import android.util.Log
import com.okaka.challengeonairandroid.model.api.response.AllChallengesResponse
import com.okaka.challengeonairandroid.model.api.service.ChallengeApi
import com.okaka.challengeonairandroid.model.api.response.ChallengeCreationRequest
import com.okaka.challengeonairandroid.model.api.response.ChallengeCreationResponse
import com.okaka.challengeonairandroid.model.api.response.ChallengeDeletionResponse
import com.okaka.challengeonairandroid.model.api.response.ChallengeReservationResponse
import com.okaka.challengeonairandroid.model.api.response.ChallengeResponse
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Singleton
class ChallengeRepository @Inject constructor(
    private val challengeApi: ChallengeApi
) {
    suspend fun getAllChallenges(): AllChallengesResponse? = withContext(Dispatchers.IO) {
        try {
            val response = challengeApi.getAllChallenges("")  // 임시로 빈 토큰

            if (response.isSuccessful()) {
                response.data
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
            val response = challengeApi.getChallengeDetails("", challengeId, date)

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

    suspend fun createChallenge(challenge: ChallengeCreationRequest): ChallengeCreationResponse? = withContext(Dispatchers.IO) {
        try {
            val response = challengeApi.createChallenge("", challenge)

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
            val response = challengeApi.deleteChallenge("", challengeId)

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
            val response = challengeApi.reserveChallenge("", challengeId)

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