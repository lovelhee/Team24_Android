package com.challengeonair.challengeonairandroid.model.api.service

import com.challengeonair.challengeonairandroid.model.api.response.ChallengeCategoryResponse
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeCreationRequest
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeCreationResponse
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeDeletionResponse
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeReservationResponse
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeResponse
import com.challengeonair.challengeonairandroid.model.api.response.AllChallengesResponse
import com.challengeonair.challengeonairandroid.model.api.response.ApiResponse
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeCancellationResponse
import retrofit2.http.*

interface ChallengeApi {
    @GET("api/challenges")
    suspend fun getAllChallenges(
        @Header("Authorization") accessToken: String
    ): ApiResponse<AllChallengesResponse>

    @GET("api/challenges/{challengeId}")
    suspend fun getChallengeDetails(
        @Header("Authorization") accessToken: String,
        @Path("challengeId") challengeId: Long,
        @Body date: String
    ): ApiResponse<ChallengeResponse>

    @GET("api/challenges/category/{categoryId}")
    suspend fun getChallengesByCategory(
        @Header("Authorization") accessToken: String,
        @Path("categoryId") categoryId: Int,
        @Query("date") date: String
    ): ApiResponse<ChallengeCategoryResponse>

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