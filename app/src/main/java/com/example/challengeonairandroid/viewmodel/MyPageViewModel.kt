package com.example.challengeonairandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeonairandroid.model.data.Challenge
import com.example.challengeonairandroid.model.data.User
import com.example.challengeonairandroid.model.repository.ChallengeRepository
import com.example.challengeonairandroid.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _userId = MutableLiveData<Long>()
    val userId: LiveData<Long> = _userId

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userImgUrl = MutableLiveData<String>()
    val userImgUrl: LiveData<String> = _userImgUrl

    private val _userPoint = MutableLiveData<Int>()
    val userPoint: LiveData<Int> = _userPoint

    private val _tryChallenge = MutableLiveData<Int>()
    val tryChallenge: LiveData<Int> = _tryChallenge

    private val _successChallenge = MutableLiveData<Int>()
    val successChallenge: LiveData<Int> = _successChallenge

    private val _myCreatedChallenge = MutableLiveData<Int>()
    val myCreatedChallenge: LiveData<Int> = _myCreatedChallenge

    private val _numWaitingChallenge = MutableLiveData<Int>()
    val numWaitingChallenge: LiveData<Int> = _numWaitingChallenge

    private val _waitingChallenges = MutableLiveData<List<Challenge>>()
    val waitingChallenges: LiveData<List<Challenge>> = _waitingChallenges

    private val _waitingChallengeHostId = MutableLiveData<Long>()
    val waitingChallengeHostId: LiveData<Long> = _waitingChallengeHostId

    private val _waitingChallengeImgUrl = MutableLiveData<String>()
    val waitingChallengeImgUrl: LiveData<String> = _waitingChallengeImgUrl

    private val _waitingChallengeName = MutableLiveData<String>()
    val waitingChallengeName: LiveData<String> = _waitingChallengeName

    init {
        loadUserData()
        loadChallengeData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                val user = userRepository.getUserData()
                _user.value = user
                _userId.value = user.userId
                _userName.value = user.userName
                _userImgUrl.value = user.userImgUrl
                _userPoint.value = user.userPoint
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun loadChallengeData() {
        viewModelScope.launch {
            try {
                val challengeData = challengeRepository.getChallenges()
                _tryChallenge.value = challengeData.tryCount
                _successChallenge.value = challengeData.successCount
                _myCreatedChallenge.value = challengeData.createdCount
                _numWaitingChallenge.value = challengeData.waitingCount
                _waitingChallenges.value = challengeData.waitingChallenges

                challengeData.waitingChallenges.firstOrNull()?.let { challenge ->
                    _waitingChallengeHostId.value = challenge.hostId
                    _waitingChallengeImgUrl.value = challenge.imgUrl
                    _waitingChallengeName.value = challenge.name
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

    fun initData() {
        val user = User(
            userId = 1,
            userName = "홍길동",
            userImgUrl = "https://example.com/sample_user_profile.png",
            userPoint = 8000
        )

        val challenges = listOf(
            Challenge(
                challengeId = 1,
                challengeName = "챌린지 1",
                hostId = 12,
                challengePoint = 1000,
                challengeDate = "2024-10-01",
                startTime = "06:00",
                endTime = "07:00",
                maxParticipantNum = 10,
                minParticipantNum = 2,
                challengeImgUrl = "data:image/jpeg;base64,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"
            ),
            Challenge(
                challengeId = 2,
                challengeName = "챌린지 2",
                hostId = 1,
                challengePoint = 1000,
                challengeDate = "2024-10-01",
                startTime = "06:00",
                endTime = "07:00",
                maxParticipantNum = 10,
                minParticipantNum = 2,
                challengeImgUrl = "data:image/png;base64,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",
            ),
            Challenge(
                challengeId = 3,
                challengeName = "챌린지 3",
                hostId = 456,
                challengePoint = 1000,
                challengeDate = "2024-10-01",
                startTime = "06:00",
                endTime = "07:00",
                maxParticipantNum = 10,
                minParticipantNum = 2,
                challengeImgUrl = "data:image/png;base64,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",
            ),
            Challenge(
                challengeId = 4,
                challengeName = "챌린지 4",
                hostId = 1,
                challengePoint = 1000,
                challengeDate = "2024-10-01",
                startTime = "06:00",
                endTime = "07:00",
                maxParticipantNum = 10,
                minParticipantNum = 2,
                challengeImgUrl = "data:image/png;base64,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"
            )
        )

//        _userId.value = user.userId
//
//        _waitingChallenges.value = challenges
//        _tryChallenge.value = 5
//        _successChallenge.value = 4
//        _numWaitingChallenge.value = challenges.size
//
//        if (challenges.isNotEmpty()) {
//            _waitingChallengeImgUrl.value = challenges[0].challengeImgUrl
//            _waitingChallengeName.value = challenges[0].challengeName
//            _waitingChallengeHostId.value = challenges[0].hostId
}