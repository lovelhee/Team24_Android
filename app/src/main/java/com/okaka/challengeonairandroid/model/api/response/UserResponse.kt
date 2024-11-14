package com.okaka.challengeonairandroid.model.api.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

// 1. 회원 가입, 로그인
@Serializable
class LogInResponse()

// 2. 로그 아웃
@Serializable
class LogoutResponse()

// 3. 토큰 재발급
@Serializable
data class ReIssueTokenResponse(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)

// 4. 회원 삭제
@Serializable
data class UserDeletionResponse(
    @SerializedName("userId") val userId: String
)