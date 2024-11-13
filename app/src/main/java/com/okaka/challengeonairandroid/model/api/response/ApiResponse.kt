package com.okaka.challengeonairandroid.model.api.response

enum class ApiStatus {
    SUCCESS,
    ERROR,
    FAIL
}

// 공통 응답 형식
data class ApiResponse<T>(
    val status: ApiStatus,
    val code: Int,
    val message: String,
    val data: T?
) {
    fun isSuccessful() = status == ApiStatus.SUCCESS
    fun isError() = status == ApiStatus.ERROR
    fun isFail() = status == ApiStatus.FAIL
}

// API 결과를 처리하기 위한 sealed class
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String?) : Result<Nothing>()
    data class ValidationFail(val message: String?) : Result<Nothing>()
}

// 에러 메시지 관리
object ErrorUtils {
    fun getErrorMessage(code: Int): String {
        return when (code) {
            400 -> "잘못된 요청입니다"
            401 -> "인증이 필요합니다"
            403 -> "접근 권한이 없습니다"
            404 -> "리소스를 찾을 수 없습니다"
            409 -> "이미 존재하는 리소스입니다"
            500 -> "서버 내부 오류가 발생했습니다"
            // API 별도 에러 코드
            1000 -> "유효하지 않은 토큰입니다"
            1001 -> "만료된 토큰입니다"
            2000 -> "존재하지 않는 챌린지입니다"
            2001 -> "이미 참여 중인 챌린지입니다"
            2002 -> "참여 인원이 초과되었습니다"
            else -> "알 수 없는 오류가 발생했습니다"
        }
    }
}

// ApiResponse 확장 함수
fun <T> ApiResponse<T>.toResult(): Result<T> {
    return when (status) {
        ApiStatus.SUCCESS -> {
            data?.let {
                Result.Success(it)
            } ?: Result.Error("데이터가 없습니다")
        }
        ApiStatus.ERROR -> Result.Error(message ?: ErrorUtils.getErrorMessage(code))
        ApiStatus.FAIL -> Result.ValidationFail(message ?: ErrorUtils.getErrorMessage(code))
    }
}