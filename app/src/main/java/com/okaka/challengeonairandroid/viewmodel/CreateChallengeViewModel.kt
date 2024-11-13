package com.okaka.challengeonairandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okaka.challengeonairandroid.model.api.response.ChallengeCreationRequest
import com.okaka.challengeonairandroid.model.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    private val _challengeCreationStatus = MutableStateFlow<Boolean>(false)
    val challengeCreationStatus: StateFlow<Boolean> = _challengeCreationStatus.asStateFlow()

    private val _challengeData = MutableStateFlow<ChallengeCreationRequest?>(null)
    val challengeData: StateFlow<ChallengeCreationRequest?> = _challengeData.asStateFlow()

    fun resetStatus() {
        _challengeCreationStatus.value = false
    }

    fun createChallenge() {
        _challengeData.value = ChallengeCreationRequest(
            categoryId = categoryId.value,
            challengeName = challengeName.value,
            challengeBody = challengeBody.value,
            challengeDate = challengeDate.value,
            maxParticipantNum = maxParticipantNum.value,
            minParticipantNum = minParticipantNum.value,
            startTime = startTime.value,
            endTime = endTime.value,
            point = point.value.toInt(),
            imageExtension = challengeImage.value ?: "", // TODO: 이미지 선택 X 시, 기본 이미지 url 연결
            hostId = "1L" // TODO: 실제 사용자 정보로 설정
        )
        sendChallengeDataToServer()
    }

    fun sendChallengeDataToServer() {
        viewModelScope.launch {
            val response = challengeRepository.createChallenge(_challengeData.value!!)
            // TODO : 서버로 데이터 전송 및 저장 확인 후 상태 갱신하도록 변경
            _challengeCreationStatus.value = true
        }
    }

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

    private val _isDateSet = MutableStateFlow(false)
    val isDateSet: StateFlow<Boolean> = _isDateSet

    private val _isStartTimeSet = MutableStateFlow(false)
    val isStartTimeSet: StateFlow<Boolean> = _isStartTimeSet

    private val _isEndTimeSet = MutableStateFlow(false)
    val isEndTimeSet: StateFlow<Boolean> = _isEndTimeSet

    private val _isPointSet = MutableStateFlow(false)
    val isPointSet: StateFlow<Boolean> = _isPointSet

    fun setDate(date: String) {
        _isDateSet.value = true
    }

    fun setStartTime(time: String) {
        _isStartTimeSet.value = true
    }

    fun setEndTime(time: String) {
        _isEndTimeSet.value = true
    }

    fun setPoint(point: Int) {
        _isPointSet.value = true
    }

    init {
        initializeChallengeData()
    }

    private fun initializeChallengeData() {
        val defaultResponse = ChallengeCreationRequest(
            categoryId = 0,
            challengeName = "",
            challengeBody = "",
            point = 0,
            challengeDate = "",
            startTime = "",
            endTime = "",
            imageExtension = "", // TODO: 이미지 선택 X 시, 기본 이미지 url 연결
            minParticipantNum = 2,
            maxParticipantNum = 4,
            hostId = "1L" // TODO: 실제 사용자 정보로 설정
        )
        updateChallenges(defaultResponse)
    }

    private fun updateChallenges(request: ChallengeCreationRequest) {
        _categoryId.value = request.categoryId
        _challengeName.value = request.challengeName
        _challengeBody.value = request.challengeBody ?: ""
        _point.value = request.point.toString()
        _challengeDate.value = request.challengeDate
        _startTime.value = request.startTime
        _endTime.value = request.endTime
        _minParticipantNum.value = request.minParticipantNum
        _maxParticipantNum.value = request.maxParticipantNum
        _challengeImage.value = request.imageExtension
    }

    fun isStep1Valid(): Boolean {
        return _categoryId.value != -1 &&
                _challengeName.value.isNotBlank() &&
                _challengeBody.value.isNotBlank() &&
                _minParticipantNum.value >= 2 &&
                _maxParticipantNum.value <= 4
    }

    fun isStep2Valid(): Boolean {
        return _challengeDate.value.isNotBlank() &&
                _startTime.value.isNotBlank() &&
                _endTime.value.isNotBlank() &&
                _point.value.toInt() > 0
    }
}