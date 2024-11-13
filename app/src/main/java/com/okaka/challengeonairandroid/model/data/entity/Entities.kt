package com.okaka.challengeonairandroid.model.data.entity

data class History(
    val challenge: Challenge,
    val isSucceeded: Boolean,
    val isHost: Boolean,
    val point: Int
)

data class User(
    val userId: String,
    val userNickName: String,
    val imageUrl: String,
    val point: Int
)

data class UserProfile(
    val userNickName: String,
    val imageUrl: String,
    val point: Int
)

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
    val hostId: String,
    val categoryId: Int
)

data class Alarm(
    val id: Int = 0,
    val message: String,
    var isConfirmed: Boolean = false
)