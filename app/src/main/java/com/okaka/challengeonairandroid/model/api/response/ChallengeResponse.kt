package com.okaka.challengeonairandroid.model.api.response

import com.google.gson.annotations.SerializedName

// 1. 챌린지 단건 조회
data class ChallengeResponse(
    @SerializedName("challengeId") val challengeId: Long,
    @SerializedName("challengeName") val challengeName: String,
    @SerializedName("challengeBody") val challengeBody: String,
    @SerializedName("point") val point: Int,
    @SerializedName("challengeDate") val challengeDate: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("imageExtension") val imageExtension: String,
    @SerializedName("minParticipantNum") val minParticipantNum: Int,
    @SerializedName("maxParticipantNum") val maxParticipantNum: Int,
    @SerializedName("currentParticipantNum") val currentParticipantNum: Int,
    @SerializedName("hostId") val hostId: String,
    @SerializedName("categoryId") val categoryId: Int
)

// 2. 챌린지 전체 조회
data class AllChallengesResponse(
    @SerializedName("challenges") val challenges: List<ChallengeResponse>
)

// 3. 챌린지 생성
data class ChallengeCreationRequest(
    @SerializedName("hostId") val hostId: String,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("challengeName") val challengeName: String,
    @SerializedName("challengeBody") val challengeBody: String? = null,
    @SerializedName("point") val point: Int,
    @SerializedName("challengeDate") val challengeDate: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("imageExtension") val imageExtension: String,
    @SerializedName("maxParticipantNum") val maxParticipantNum: Int,
    @SerializedName("minParticipantNum") val minParticipantNum: Int
)

data class ChallengeCreationResponse(
    @SerializedName("challengeId") val challengeId: Long
)

// 4. 챌린지 삭제
data class ChallengeDeletionResponse(
    @SerializedName("challengeId") val challengeId: Long
)

// 5. 챌린지 예약
data class ChallengeReservationResponse(
    @SerializedName("challengeId") val challengeId: Long,
    @SerializedName("userId") val userId: Long
)

// 6. 대기 중인 챌린지 조회
data class WaitingChallengeResponse(
    @SerializedName("challengeId") val challengeId: Long,
    @SerializedName("challengeName") val challengeName: String,
    @SerializedName("challengeBody") val challengeBody: String,
    @SerializedName("point") val point: Int,
    @SerializedName("challengeDate") val challengeDate: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("imageExtension") val imageExtension: String,
    @SerializedName("minParticipantNum") val minParticipantNum: Int,
    @SerializedName("maxParticipantNum") val maxParticipantNum: Int,
    @SerializedName("currentParticipantNum") val currentParticipantNum: Int,
    @SerializedName("hostId") val hostId: String,
    @SerializedName("categoryId") val categoryId: Int
)

// TODO: 7. 챌린지 마무리 (API 완성 후 추가)