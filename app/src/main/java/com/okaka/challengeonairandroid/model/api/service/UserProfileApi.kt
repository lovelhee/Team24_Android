package com.okaka.challengeonairandroid.model.api.service

import com.okaka.challengeonairandroid.model.api.response.ApiResponse
import com.okaka.challengeonairandroid.model.api.response.UserProfileResponse
import com.okaka.challengeonairandroid.model.api.response.UserProfileSpecificResponse
import com.okaka.challengeonairandroid.model.api.response.UserProfileUpdateRequest
import com.okaka.challengeonairandroid.model.api.response.UserProfileUpdateResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserProfileApi {

    // 1. 회원 정보 조회
    @GET("api/userprofile")
    suspend fun getUserProfile(
        @Header("Authorization") accessToken: String
    ): ApiResponse<UserProfileResponse>

    // 2. 회원 정보 수정
    @POST("api/userprofile")
    suspend fun updateUserProfile(
        @Header("Authorization") accessToken: String,
        @Body userProfileUpdateRequest: UserProfileUpdateRequest
    ): ApiResponse<UserProfileUpdateResponse>

    @GET("api/userprofile/host/{uuid}")
    suspend fun getSpecificUserProfile(
        @Header("Authorization") accessToken: String,
        @Path("uuid") userId: String
    ): ApiResponse<UserProfileSpecificResponse>
}