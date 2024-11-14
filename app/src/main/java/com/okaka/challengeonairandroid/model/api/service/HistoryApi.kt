package com.okaka.challengeonairandroid.model.api.service

import com.okaka.challengeonairandroid.model.api.response.ApiResponse
import com.okaka.challengeonairandroid.model.api.response.AllHistoriesResponse
import com.okaka.challengeonairandroid.model.api.response.HistoryResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HistoryApi {

    // 1. 회원 챌린지 History 단건 조회
    @GET("api/history/{historyId}")
    suspend fun getHistoryById(
        @Header("Authorization") accessToken: String,
        @Path("historyId") historyId: Long
    ): ApiResponse<HistoryResponse>

    // 2. 회원 챌린지 History 전체 조회
    @GET("api/histories")
    suspend fun getAllHistories(
        @Header("Authorization") accessToken: String
    ): ApiResponse<AllHistoriesResponse>
}
