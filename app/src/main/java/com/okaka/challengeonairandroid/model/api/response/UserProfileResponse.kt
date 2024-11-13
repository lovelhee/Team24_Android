package com.okaka.challengeonairandroid.model.api.response

import com.google.gson.annotations.SerializedName

// 1. 회원 정보 조회
data class UserProfileResponse(
    @SerializedName("userNickName") val userNickName: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("point") val point: Int
)

// 2. 회원 정보 수정
data class UserProfileUpdateRequest(
    @SerializedName("userNickName") val userNickName: String? = null,
    @SerializedName("imageUrl") val imageUrl: String? = null
)

data class UserProfileUpdateResponse(
    @SerializedName("userNickName") val userNickName: String,
    @SerializedName("imageUrl") val imageUrl: String
)