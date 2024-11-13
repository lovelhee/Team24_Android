package com.okaka.challengeonairandroid.model.api.service

import com.okaka.challengeonairandroid.model.api.response.ApiResponse
import com.okaka.challengeonairandroid.model.api.response.LogInResponse
import com.okaka.challengeonairandroid.model.api.response.LogoutResponse
import com.okaka.challengeonairandroid.model.api.response.ReIssueTokenResponse
import com.okaka.challengeonairandroid.model.api.response.UserDeletionResponse
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {
    // 1. 회원 가입
    @POST("oauth2/authorization/kakao")
    suspend fun login(
    ): ApiResponse<LogInResponse>

    // 2. 로그아웃
    @POST("api/users/logout")
    suspend fun logout(
        @Header("Authorization") accessToken: String
    ): ApiResponse<LogoutResponse>

    // 3. 회원 삭제
    @DELETE("api/users")
    suspend fun deleteUser(
        @Header("Authorization") accessToken: String
    ): ApiResponse<UserDeletionResponse>

    // 4. 토큰 재발행
    @POST("api/reissue")
    suspend fun reIssueToken(
        @Header("Cookie") refreshToken: String
    ): ApiResponse<ReIssueTokenResponse>
}