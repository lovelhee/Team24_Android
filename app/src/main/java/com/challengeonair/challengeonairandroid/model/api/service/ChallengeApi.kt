package com.challengeonair.challengeonairandroid.model.api.service

import com.challengeonair.challengeonairandroid.model.api.response.ChallengeCategoryResponse
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeCreationRequest
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeCreationResponse
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeDeletionResponse
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeReservationResponse
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeResponse
import retrofit2.Response
import retrofit2.http.*

interface ChallengeApi {

    @GET("api/challenges/{challenge_id}")
    suspend fun getChallengeDetails(
        @Path("challenge_id") challengeId: Long,
        @Body date: String
    ): Response<ChallengeResponse>
    // http 요청을 API 명세에 맞게 보내고 DTO에 맞는 객체가 담긴 Response 객체를 서버로부터 받아옴.

    @GET("api/challenges/{category_id}")
    suspend fun getChallengesByCategory(
        @Path("category_id") categoryId: Int,
        @Body date: String
    ): Response<ChallengeCategoryResponse>

    @POST("api/challenges")
    suspend fun createChallenge(
        @Body challengeCreationRequest: ChallengeCreationRequest
    ): Response<ChallengeCreationResponse>

    @DELETE("api/challenges/{challenge_id}")
    suspend fun deleteChallenge(
        @Path("challenge_id") challengeId: Long
    ): Response<ChallengeDeletionResponse>

    @POST("api/challenges/reservation/{challenge_id}")
    suspend fun reserveChallenge(
        @Path("challenge_id") challengeId: Long
    ): Response<ChallengeReservationResponse>
}