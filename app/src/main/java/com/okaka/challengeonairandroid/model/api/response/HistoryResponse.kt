package com.okaka.challengeonairandroid.model.api.response

import com.challengeonair.challengeonairandroid.model.data.entity.Challenge
import com.google.gson.annotations.SerializedName

// 1. 회원 챌린지 History 단건 조회
data class HistoryResponse(
    @SerializedName("challenge") val challenge: Challenge,
    @SerializedName("isSucceeded") val isSucceeded: Boolean,
    @SerializedName("isHost") val isHost: Boolean,
    @SerializedName("point") val point: Int
)

// 2. 회원 챌린지 History 전체 조회
data class AllHistoriesResponse(
    @SerializedName("histories") val histories: List<HistoryResponse>
)