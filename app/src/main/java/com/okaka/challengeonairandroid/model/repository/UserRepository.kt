package com.okaka.challengeonairandroid.model.repository

import com.okaka.challengeonairandroid.model.api.response.LogInResponse
import com.okaka.challengeonairandroid.model.api.response.LogoutResponse
import com.okaka.challengeonairandroid.model.api.response.ReIssueTokenResponse
import com.okaka.challengeonairandroid.model.api.response.UserDeletionResponse
import com.okaka.challengeonairandroid.model.api.service.UserApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun login(): Result<LogInResponse> {
        return try {
            val response = userApi.login()
            if (response.isSuccessful()) {
                response.data?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("Registration failed: ${response.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(accessToken: String): Result<LogoutResponse> {
        return try {
            val response = userApi.logout(accessToken)
            if (response.isSuccessful()) {
                response.data?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("Logout failed: ${response.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteUser(accessToken: String): Result<UserDeletionResponse> {
        return try {
            val response = userApi.deleteUser(accessToken)
            if (response.isSuccessful()) {
                response.data?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("User deletion failed: ${response.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun reIssueToken(reIssueToken: String): Result<ReIssueTokenResponse> {
        return try {
            val response = userApi.reIssueToken(reIssueToken)
            if (response.isSuccessful()) {
                response.data?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("Token reissue failed: ${response.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}