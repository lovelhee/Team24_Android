package com.challengeonair.challengeonairandroid.model.data

data class History(
    val challengeName: String,
    val challengeStartTime: String,
    val challengeEndTime: String,
    val historyDate: String,
    val isSucceeded: Boolean,
    val isHost: Boolean
)

data class Category(
    val categoryId: Long,
    val categoryName: String,
    val categoryDescription: String
) // 카테고리 아이디를 받으면 enum으로

data class Challenge(
    val challengeId: Long,
    val challengeName: String,
    val challengeBody: String,
    val point: Int,
    val challengeDate: String,
    val startTime: String,
    val endTime: String,
    val imageExtension: String,
    val minParticipantNum: Int,
    val maxParticipantNum: Int,
    val currentParticipantNum: Int,
    val hostId: Long,
    val categoryId: Int
)

data class Participant(
    val participantId: Long,
    val userId: Long,
    val challengeId: Long
)