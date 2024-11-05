package com.example.challengeonairandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeonairandroid.model.api.response.ChallengeResponse
import com.example.challengeonairandroid.model.data.Challenge
import com.example.challengeonairandroid.model.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private val dummyChallengeResponses: List<ChallengeResponse> = listOf(
    ChallengeResponse(
        categoryId = 0,
        challengeName = "아침 운동 챌린지",
        challengeBody = "매일 아침 30분 운동하기",
        point = 100,
        challengeDate = "2024-03-01",
        startTime = "06:00",
        endTime = "07:00",
        imageUrl = "https://picsum.photos/200/300",
        minParticipantNum = 5,
        maxParticipantNum = 20,
        currentParticipantNum = 12,
        hostId = "user123"
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "독서 챌린지",
        challengeBody = "한 달 동안 5권 책 읽기",
        point = 150,
        challengeDate = "2024-03-15",
        startTime = "00:00",
        endTime = "23:59",
        imageUrl = "https://picsum.photos/200/300",
        minParticipantNum = 10,
        maxParticipantNum = 50,
        currentParticipantNum = 25,
        hostId = "user456"
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "물 마시기 챌린지",
        challengeBody = "하루 2리터 물 마시기",
        point = 80,
        challengeDate = "2024-04-01",
        startTime = "08:00",
        endTime = "22:00",
        imageUrl = "https://picsum.photos/200/300",
        minParticipantNum = 3,
        maxParticipantNum = 100,
        currentParticipantNum = 75,
        hostId = "user789"
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "코딩 스터디 챌린지",
        challengeBody = "매일 2시간 코딩 공부하기",
        point = 200,
        challengeDate = "2024-04-15",
        startTime = "19:00",
        endTime = "21:00",
        imageUrl = "https://picsum.photos/200/300",
        minParticipantNum = 5,
        maxParticipantNum = 15,
        currentParticipantNum = 8,
        hostId = "user101"
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "환경 보호 챌린지",
        challengeBody = "일주일 동안 일회용품 사용 줄이기",
        point = 120,
        challengeDate = "2024-05-01",
        startTime = "00:00",
        endTime = "23:59",
        imageUrl = "https://picsum.photos/200/300",
        minParticipantNum = 20,
        maxParticipantNum = 200,
        currentParticipantNum = 150,
        hostId = "user202"
    )
)

//@HiltViewModel
//class SearchViewModel @Inject constructor(
//    private val searchRepository: SearchRepository
//) : ViewModel() {

class SearchViewModel : ViewModel() {
    private val _categoryId = MutableStateFlow(0) // 초기값 설정
    val categoryId: StateFlow<Int> get() = _categoryId.asStateFlow()

    private val _searchQuery = MutableStateFlow("") // 검색어 상태 추가
    val searchQuery: StateFlow<String> get() = _searchQuery.asStateFlow()

    private val _allChallenges = MutableStateFlow<List<Challenge>>(emptyList())
    private val _challenges = MutableStateFlow<List<Challenge>>(emptyList())
    val challenges: StateFlow<List<Challenge>> = _challenges.asStateFlow()

    init {
        loadChallenges()
        observeCategoryChanges()
    }

    private fun loadChallenges() {
        viewModelScope.launch {
            val challengeResponses = dummyChallengeResponses
            updateChallenges(challengeResponses)
        }
    }

    private fun updateChallenges(challengeResponses: List<ChallengeResponse>) {
        _allChallenges.value = challengeResponses.map { response ->
            Challenge(
                categoryId = response.categoryId,
                challengeName = response.challengeName,
                imageUrl = response.imageUrl,
                hostId = response.hostId.toLongOrNull() ?: 0L
            )
        }
        filterChallenges()
    }

    fun setCategoryId(id: Int) {
        _categoryId.value = id
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun observeCategoryChanges() {
        viewModelScope.launch {
            _categoryId.collect {
                filterChallenges()
            }
        }
        viewModelScope.launch {
            searchQuery.collect {
                filterChallenges()
            }
        }
    }

    private fun filterChallenges() {
        val filteredList = _allChallenges.value.filter { challenge ->
            challenge.categoryId == _categoryId.value &&
                    challenge.challengeName.contains(_searchQuery.value, ignoreCase = true)
        }
        _challenges.value = filteredList
    }
}