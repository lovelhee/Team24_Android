package com.okaka.challengeonairandroid.model.data

object FakeLocalData {
    private var userId = "1234L"
    private var userName = "김민혁"
    private var userImgUrl = "https://picsum.photos/id/237/200/300"

    fun getUserId(): String = userId
    fun getUserName(): String = userName
    fun getUserImgUrl(): String = userImgUrl
}