package com.challengeonair.challengeonairandroid.model.data

data class Alarm(
    val id: Int,
    val message: String,
    var isConfirmed: Boolean = false
)
