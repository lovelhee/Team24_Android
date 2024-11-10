package com.challengeonair.challengeonairandroid.model.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val userId: Long,
    val userNickName: String,
    val imageUrl: String?,
    val point: Int
)