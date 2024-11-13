package com.challengeonair.challengeonairandroid.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challengeonair.challengeonairandroid.model.api.response.AllHistoriesResponse
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeResponse
import com.challengeonair.challengeonairandroid.model.api.response.HistoryResponse
import com.challengeonair.challengeonairandroid.model.api.response.UserProfileResponse
import com.challengeonair.challengeonairandroid.model.data.entity.Challenge
import com.challengeonair.challengeonairandroid.model.data.entity.History
import com.challengeonair.challengeonairandroid.model.data.entity.UserProfile
import com.challengeonair.challengeonairandroid.model.repository.HistoryRepository
import com.challengeonair.challengeonairandroid.model.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private val dummyHistoryListResponse = AllHistoriesResponse(
    histories = listOf(
        HistoryResponse(
            challenge = ChallengeResponse(
                categoryId = 0,
                challengeName = "아침 운동 챌린지",
                challengeBody = "매일 아침 30분 운동하기",
                point = 100,
                challengeDate = "2024-03-01",
                startTime = "06:00",
                endTime = "07:00",
                imageExtension = "https://example.com/morning_exercise.jpg",
                minParticipantNum = 5,
                maxParticipantNum = 20,
                currentParticipantNum = 12,
                hostId = "2L",
                challengeId = 1
            ),
            isSucceeded = true,
            isHost = false,
            point = 100
        ),
        HistoryResponse(
            challenge = ChallengeResponse(
                categoryId = 1,
                challengeName = "독서 챌린지",
                challengeBody = "한 달 동안 5권 책 읽기",
                point = 150,
                challengeDate = "2024-03-15",
                startTime = "00:00",
                endTime = "23:59",
                imageExtension = "https://example.com/reading_challenge.jpg",
                minParticipantNum = 10,
                maxParticipantNum = 50,
                currentParticipantNum = 25,
                hostId = "2L",
                challengeId = 1
            ),
            isSucceeded = false,
            isHost = true,
            point = 100
        )
    )
)

private val dummyUserProfileResponse = UserProfileResponse(
    userNickName = "챌린지마스터",
    userBody = "건강한 삶을 위해 매일 노력합니다!",
    imageUrl = "https://example.com/profile_image.jpg",
    point = 1000
)

//data class ChallengeResponse(
//    @SerializedName("challengeId") val challengeId: Long,
//    @SerializedName("challengeName") val challengeName: String,
//    @SerializedName("challengeBody") val challengeBody: String,
//    @SerializedName("point") val point: Int,
//    @SerializedName("challengeDate") val challengeDate: String,
//    @SerializedName("startTime") val startTime: String,
//    @SerializedName("endTime") val endTime: String,
//    @SerializedName("imageExtension") val imageExtension: String,
//    @SerializedName("minParticipantNum") val minParticipantNum: Int,
//    @SerializedName("maxParticipantNum") val maxParticipantNum: Int,
//    @SerializedName("currentParticipantNum") val currentParticipantNum: Int,
//    @SerializedName("hostId") val hostId: Long,
//    @SerializedName("categoryId") val categoryId: Int
//)
private val dummyChallengeResponses: List<ChallengeResponse> = listOf(
    ChallengeResponse(
        categoryId = 1,
        challengeName = "아침 운동 챌린지",
        challengeBody = "매일 아침 30분 운동하기",
        point = 100,
        challengeDate = "2024-03-01",
        startTime = "06:00",
        endTime = "07:00",
        imageExtension = "https://picsum.photos/200/300",
        minParticipantNum = 5,
        maxParticipantNum = 20,
        currentParticipantNum = 12,
        hostId = "2L",
        challengeId = 1
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "독서 챌린지",
        challengeBody = "한 달 동안 5권 책 읽기",
        point = 150,
        challengeDate = "2024-03-15",
        startTime = "00:00",
        endTime = "23:59",
        imageExtension = "https://picsum.photos/200/300",
        minParticipantNum = 10,
        maxParticipantNum = 50,
        currentParticipantNum = 25,
        hostId = "3L",
        challengeId = 1
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "물 마시기 챌린지",
        challengeBody = "하루 2리터 물 마시기",
        point = 80,
        challengeDate = "2024-04-01",
        startTime = "08:00",
        endTime = "22:00",
        imageExtension = "https://picsum.photos/200/300",
        minParticipantNum = 3,
        maxParticipantNum = 100,
        currentParticipantNum = 75,
        hostId = "4L",
        challengeId = 0
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "코딩 스터디 챌린지",
        challengeBody = "매일 2시간 코딩 공부하기",
        point = 200,
        challengeDate = "2024-04-15",
        startTime = "19:00",
        endTime = "21:00",
        imageExtension = "https://picsum.photos/200/300",
        minParticipantNum = 5,
        maxParticipantNum = 15,
        currentParticipantNum = 8,
        hostId = "5L",
        challengeId = 0
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "환경 보호 챌린지",
        challengeBody = "일주일 동안 일회용품 사용 줄이기",
        point = 120,
        challengeDate = "2024-05-01",
        startTime = "00:00",
        endTime = "23:59",
        imageExtension = "https://picsum.photos/200/300",
        minParticipantNum = 20,
        maxParticipantNum = 200,
        currentParticipantNum = 150,
        hostId = "2L",
        challengeId = 0
    ),
    ChallengeResponse(
        challengeName = "취미 요리",
        challengeDate = "2024-10-16",
        startTime = "15:00",
        endTime = "17:00",
        imageExtension = "https://cdn.pixabay.com/photo/2016/11/18/15/31/cooking-1835369_1280.jpg",
        currentParticipantNum = 2,
        maxParticipantNum = 4,
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        hostId = "3L",
        challengeId = 3,
        categoryId = 1
    ),
    ChallengeResponse(
        challengeName = "뜨개질로 목도리 만들기",
        challengeDate = "2024-10-16",
        startTime = "15:00",
        endTime = "17:00",
        imageExtension = "https://cdn.pixabay.com/photo/2020/06/08/23/52/tissue-5276453_1280.jpg",
        currentParticipantNum = 2,
        maxParticipantNum = 4,
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        hostId = "3L",
        categoryId = 3,
        challengeId = 1
    )
)

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
                challengeName = historyResponse.challenge.challengeName,
                challengeStartTime = historyResponse.challenge.startTime,
                challengeEndTime = historyResponse.challenge.endTime,
                historyDate = historyResponse.challenge.challengeDate,
                isSucceeded = historyResponse.isSucceeded,
                isHost = historyResponse.isHost
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
            userBody = response.userBody,
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
                imageExtension = response.imageExtension,
                hostId = response.hostId,
                challengeId = 1
            )
        }
    }
}

