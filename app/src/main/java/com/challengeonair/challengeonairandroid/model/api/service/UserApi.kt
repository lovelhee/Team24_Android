package com.example.challengeonairandroid.model.api.service

import com.example.challengeonairandroid.model.api.response.LogInResponse
import com.example.challengeonairandroid.model.api.response.LogoutResponse
import com.example.challengeonairandroid.model.api.response.ReIssueTokenResponse
import com.example.challengeonairandroid.model.api.response.UserDeletionResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {
    // 1. 회원 가입
    @POST("oauth2/authorization/naver")
    suspend fun login(
    ): Response<LogInResponse>

    // 2. 로그아웃
    @POST("api/users/logout")
    suspend fun logout(
        @Header("Authorization") accessToken: String
    ): Response<LogoutResponse>

    // 3. 회원 삭제
    @DELETE("api/users")
    suspend fun deleteUser(
        @Header("Authorization") accessToken: String
    ): Response<UserDeletionResponse>

    // 4. 토큰 재발행
    @POST("api/auth/reissue")
    suspend fun reIssueToken(
        @Header("reIssueToken") reIssueToken: String
    ): Response<ReIssueTokenResponse>
}