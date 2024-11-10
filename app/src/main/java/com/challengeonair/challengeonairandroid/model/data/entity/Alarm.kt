package com.challengeonair.challengeonairandroid.model.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val message: String,
    var isConfirmed: Boolean = false
)