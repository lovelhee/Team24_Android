package com.example.challengeonairandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.challengeonairandroid.model.api.response.ChallengeResponse
import com.example.challengeonairandroid.model.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    // Step 1
    private val _categoryId = MutableStateFlow<Int>(0)
    val categoryId: StateFlow<Int> = _categoryId.asStateFlow()

    fun updateCategoryId(id: Int) {
        _categoryId.value = id
        Log.d("CreateChallengeViewModel", "Category ID updated to $id")
    }

    private val _challengeName = MutableStateFlow<String>("")
    val challengeName: StateFlow<String> = _challengeName.asStateFlow()

    fun updateChallengeName(name: String) {
        _challengeName.value = name
        Log.d("CreateChallengeViewModel", "Challenge Name updated to $name")
    }

    private val _challengeBody = MutableStateFlow<String>("")
    val challengeBody: StateFlow<String> = _challengeBody.asStateFlow()

    fun updateChallengeBody(body: String) {
        _challengeBody.value = body
        Log.d("CreateChallengeViewModel", "Challenge Body updated to $body")
    }

    private val _minParticipantNum = MutableStateFlow<Int>(2)
    val minParticipantNum: StateFlow<Int> = _minParticipantNum.asStateFlow()

    fun updateMinParticipantNum(minNum: Int) {
        _minParticipantNum.value = minNum
        Log.d("CreateChallengeViewModel", "Min Participant Number updated to $minNum")
    }

    private val _maxParticipantNum = MutableStateFlow<Int>(4)
    val maxParticipantNum: StateFlow<Int> = _maxParticipantNum.asStateFlow()

    fun updateMaxParticipantNum(maxNum: Int) {
        _maxParticipantNum.value = maxNum
        Log.d("CreateChallengeViewModel", "Max Participant Number updated to $maxNum")
    }

    private val _challengeImage = MutableStateFlow<String?>("")
    val challengeImage: StateFlow<String?> = _challengeImage.asStateFlow()

    fun updateChallengeImage(imageUri: String) {
        _challengeImage.value = imageUri
        Log.d("CreateChallengeViewModel", "Challenge Image URI updated to $imageUri")
    }

    // Step 2
    private val _challengeDate = MutableStateFlow<String>("")
    val challengeDate: StateFlow<String> = _challengeDate.asStateFlow()

    fun updateChallengeDate(date: String) {
        _challengeDate.value = date
    }

    private val _startTime = MutableStateFlow<String>("")
    val startTime: StateFlow<String> = _startTime.asStateFlow()

    fun updateStartTime(startTime: String) {
        _startTime.value = startTime
    }

    private val _endTime = MutableStateFlow<String>("")
    val endTime: StateFlow<String> = _endTime.asStateFlow()

    fun updateEndTime(endTime: String) {
        _endTime.value = endTime
    }

    private val _point = MutableStateFlow<String>("")
    val point: StateFlow<String> = _point.asStateFlow()

    fun updatePoint(point: String) {
        _point.value = point
    }

    init {
        initializeChallengeData()
    }

    private fun initializeChallengeData() {

        val defaultResponse = ChallengeResponse(
            categoryId = 0,
            challengeName = "",
            challengeBody = "",
            point = 0,
            challengeDate = "",
            startTime = "",
            endTime = "",
            imageUrl = "",
            minParticipantNum = 2,
            maxParticipantNum = 4,
            currentParticipantNum = 1,
            hostId = 1L // TODO: 실제 사용자 정보로 설정
        )
        updateChallenges(defaultResponse)
    }

    private fun updateChallenges(response: ChallengeResponse) {
        _categoryId.value = response.categoryId
        _challengeName.value = response.challengeName
        _challengeBody.value = response.challengeBody
        _point.value = response.point.toString()
        _challengeDate.value = response.challengeDate
        _startTime.value = response.startTime
        _endTime.value = response.endTime
        _minParticipantNum.value = response.minParticipantNum
        _maxParticipantNum.value = response.maxParticipantNum
        _challengeImage.value = response.imageUrl
    }
}