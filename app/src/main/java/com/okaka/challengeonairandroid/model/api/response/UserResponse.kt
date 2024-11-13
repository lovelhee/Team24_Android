package com.okaka.challengeonairandroid.model.api.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class LogInResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Nothing? = null
)

data class LogoutResponse(
    @SerializedName("msg") val message: String
)

data class UserDeletionResponse(
    @SerializedName("userId") val userId: String
)

@Serializable
data class ReIssueTokenResponse(
    @SerializedName("Authorization") val accessToken: String, // 새로운 access Token
    @SerializedName("reIssueToken") val reIssueToken: String // 새로운 reissue 토큰
)