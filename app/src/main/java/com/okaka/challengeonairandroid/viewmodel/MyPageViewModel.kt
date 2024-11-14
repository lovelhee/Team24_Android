package com.okaka.challengeonairandroid.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okaka.challengeonairandroid.model.api.response.ChallengeResponse
import com.okaka.challengeonairandroid.model.api.response.HistoryResponse
import com.okaka.challengeonairandroid.model.api.response.UserProfileResponse
import com.okaka.challengeonairandroid.model.data.dummyChallengeResponses
import com.okaka.challengeonairandroid.model.data.entity.Challenge
import com.okaka.challengeonairandroid.model.data.entity.History
import com.okaka.challengeonairandroid.model.data.entity.UserProfile
import com.okaka.challengeonairandroid.model.repository.HistoryRepository
import com.okaka.challengeonairandroid.model.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//TODO: 에러 처리
//TODO: accessToken을 뷰모델 생성자를 통해 주입
@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {
    private val _histories = MutableStateFlow<List<History>>(emptyList())
    val histories: StateFlow<List<History>> = _histories.asStateFlow()

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    private val _challenges = MutableStateFlow<List<Challenge>>(emptyList())
    val challenges: StateFlow<List<Challenge>> = _challenges.asStateFlow()

    private val accessToken = "accessToken"

    private val _successChallengeNum = MutableStateFlow<Int>(0)
    val successChallengeNum: StateFlow<Int> = _successChallengeNum.asStateFlow()

    private val _tryChallengeNum = MutableStateFlow<Int>(0)
    val tryChallengeNum: StateFlow<Int> = _tryChallengeNum.asStateFlow()

    private val _myCreatedChallengeNum = MutableStateFlow<Int>(0)
    val myCreatedChallengeNum: StateFlow<Int> = _myCreatedChallengeNum.asStateFlow()

    private val _successMarked = MutableStateFlow<Int>(View.VISIBLE)
    val successMarked: StateFlow<Int> = _successMarked.asStateFlow()

    private val _failMarked = MutableStateFlow<Int>(View.VISIBLE)
    val failMarked: StateFlow<Int> = _failMarked.asStateFlow()

    private val _myCreatedMarked = MutableStateFlow<Int>(View.VISIBLE)
    val myCreatedMarked: StateFlow<Int> = _myCreatedMarked.asStateFlow()

    init {
        loadAllHistories()
        loadUserData()
        loadWaitingChallenges()
    }

    private fun loadAllHistories() {
        viewModelScope.launch {
            try {
                val response = historyRepository.getAllHistories()
                response?.let { historyListResponse ->
                    updateHistories(historyListResponse.histories)
                }
            } catch (e: Exception) {
                Log.e("MyPageViewModel", "Error loading histories", e)
                // TODO: Error handling
            }
        }
    }

    private fun updateHistories(histories: List<HistoryResponse>) {
        _histories.value = histories.map { historyResponse ->
            History(
                challenge = historyResponse.challenge,
                isSucceeded = historyResponse.isSucceeded,
                isHost = historyResponse.isHost,
                point = historyResponse.point
            )
        }

        val successCount = histories.count { it.isSucceeded }
        _successChallengeNum.value = successCount

        val tryCount = histories.size
        _tryChallengeNum.value = tryCount

        val myCreateCount = histories.count { it.isHost }
        _myCreatedChallengeNum.value = myCreateCount

        if (histories.isNotEmpty()) {
            val hostFlag = histories[0].isHost
            _myCreatedMarked.value = if (hostFlag) View.VISIBLE else View.GONE

            val successFlag = histories[0].isSucceeded
            _successMarked.value = if (successFlag) View.VISIBLE else View.GONE
            _failMarked.value = if (successFlag) View.GONE else View.VISIBLE
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                val response = userProfileRepository.getUserProfile()
                response?.let { userProfileResponse ->
                    updateUserProfile(userProfileResponse)
                }
            } catch (e: Exception) {
                Log.e("MyPageViewModel", "Error loading user profile", e)
                // TODO: Error handling
            }
        }
    }

    private fun updateUserProfile(response: UserProfileResponse) {
        _userProfile.value = UserProfile(
            userNickName = response.userNickName,
            imageUrl = response.imageUrl,
            point = response.point
        )
    }

    private fun loadWaitingChallenges() {
        viewModelScope.launch {
            try {
                // TODO: Implement API call for waiting challenges once available
                // For now, keeping the challenges empty
                _challenges.value = emptyList()
            } catch (e: Exception) {
                Log.e("MyPageViewModel", "Error loading waiting challenges", e)
                // TODO: Error handling
            }
            val challengeResponses = dummyChallengeResponses // TODO: API 만들어지면 교체
//            val challengeResponse = myPageRepository.
            updateChallenges(challengeResponses)
        }
    }

    private fun updateChallenges(challengeResponses: List<ChallengeResponse>) {
        _challenges.value = challengeResponses.map { response ->
            Challenge(
                categoryId = response.categoryId,
                challengeName = response.challengeName,
                challengeBody = response.challengeBody,
                challengeDate = response.challengeDate,
                maxParticipantNum = response.maxParticipantNum,
                minParticipantNum = response.minParticipantNum,
                currentParticipantNum = response.currentParticipantNum,
                point = response.point,
                startTime = response.startTime,
                endTime = response.endTime,
                imageUrl = response.imageUrl,
                hostId = response.hostId,
                challengeId = 1
            )
        }
    }
}

