package com.okaka.challengeonairandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okaka.challengeonairandroid.model.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    private val _challengeDetailUiState = MutableStateFlow(ChallengeDetailUiState())
    val challengeDetailUiState = _challengeDetailUiState.asStateFlow()

    data class ChallengeDetailUiState(
        val isLoading: Boolean = false,
        val error: String? = null,
        // Challenge 상세 정보
        val challengeId: Long = 0,
        val challengeName: String = "",
        val challengeBody: String = "",
        val point: Int = 0,
        val challengeDate: String = "",
        val startTime: String = "",
        val endTime: String = "",
        val imageUrl: String = "",
        val minParticipantNum: Int = 0,
        val maxParticipantNum: Int = 0,
        val currentParticipantNum: Int = 0,
        val hostId: String = "",
        val categoryId: Int = 0
    )

    fun getChallengeDetails(accessToken: String, challengeId: Long, date: String) {
        viewModelScope.launch {
            _challengeDetailUiState.update { it.copy(isLoading = true) }
            try {
                val response = challengeRepository.getChallengeDetails(challengeId)
                if (response!= null) {
                    _challengeDetailUiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            challengeId = response.challengeId,
                            challengeName = response.challengeName,
                            challengeBody = response.challengeBody,
                            point = response.point,
                            challengeDate = response.challengeDate,
                            startTime = response.startTime,
                            endTime = response.endTime,
                            imageUrl = response.imageUrl,
                            minParticipantNum = response.minParticipantNum,
                            maxParticipantNum = response.maxParticipantNum,
                            currentParticipantNum = response.currentParticipantNum,
                            hostId = response.hostId,
                            categoryId = response.categoryId
                        )
                    }
                } else {
                    _challengeDetailUiState.update {
                        it.copy(
                            isLoading = false,
                            error = "챌린지 정보를 불러오는데 실패했습니다."
                        )
                    }
                }
            } catch (e: Exception) {
                _challengeDetailUiState.update {
                    it.copy(
                        isLoading = false,
                        error = "오류가 발생했습니다: ${e.message}"
                    )
                }
            }
        }
    }
}