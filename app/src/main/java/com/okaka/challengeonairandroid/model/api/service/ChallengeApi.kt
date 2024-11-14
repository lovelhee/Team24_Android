package com.okaka.challengeonairandroid.model.api.service

import com.okaka.challengeonairandroid.model.api.response.ChallengeCreationRequest
import com.okaka.challengeonairandroid.model.api.response.ChallengeCreationResponse
import com.okaka.challengeonairandroid.model.api.response.ChallengeDeletionResponse
import com.okaka.challengeonairandroid.model.api.response.ChallengeReservationResponse
import com.okaka.challengeonairandroid.model.api.response.ChallengeResponse
import com.okaka.challengeonairandroid.model.api.response.ApiResponse
import com.okaka.challengeonairandroid.model.api.response.ChallengeCancellationResponse
import retrofit2.http.*

interface ChallengeApi {
    @GET("api/challenges")
    suspend fun getAllChallenges(
        @Header("Authorization") accessToken: String
    ): ApiResponse<List<ChallengeResponse>>

    @GET("api/challenges/{challengeId}")
    suspend fun getChallengeDetails(
        @Header("Authorization") accessToken: String,
        @Path("challengeId") challengeId: Long,
        @Body date: String
    ): ApiResponse<ChallengeResponse>

    @POST("api/challenges")
    suspend fun createChallenge(
        @Header("Authorization") accessToken: String,
        @Body request: ChallengeCreationRequest
    ): ApiResponse<ChallengeCreationResponse>

    @DELETE("api/challenges/{challengeId}")
    suspend fun deleteChallenge(
        @Header("Authorization") accessToken: String,
        @Path("challengeId") challengeId: Long
    ): ApiResponse<ChallengeDeletionResponse>

    @POST("api/challenges/reservation/{challengeId}")
    suspend fun reserveChallenge(
        @Header("Authorization") accessToken: String,
        @Path("challengeId") challengeId: Long
    ): ApiResponse<ChallengeReservationResponse>

    @POST("api/challenges/{challengeId}/cancel")
    suspend fun cancelChallenge(
        @Header("Authorization") accessToken: String,
        @Path("challengeId") challengeId: Long
    ): ApiResponse<ChallengeCancellationResponse>

}