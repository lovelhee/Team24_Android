package com.challengeonair.challengeonairandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeResponse
import com.challengeonair.challengeonairandroid.model.data.Challenge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private val dummyChallengeResponses: List<ChallengeResponse> = listOf(
    ChallengeResponse(
        challengeName = "아침 러닝",
        challengeBody = "222",
        point = 10,
        challengeDate = "2024-10-12",
        startTime = "06:00",
        endTime = "07:00",
        imageExtension = "https://media.istockphoto.com/id/1704187857/ko/%EC%82%AC%EC%A7%84/%EC%84%A0%EB%91%90-%EC%A3%BC%EC%9E%90-%EB%94%B0%EB%9D%BC%EC%9E%A1%EA%B8%B0.jpg?s=1024x1024&w=is&k=20&c=G09WByt9tu2ry6nUOpPiWFfxMTNsJpHVRM3D8oxdzmE=",
        currentParticipantNum = 3,
        maxParticipantNum = 4,
        minParticipantNum = 2,
        hostId = 1L,
        categoryId = 0,
        challengeId = 1
    ),
    ChallengeResponse(
        challengeName = "3대 500을 위하여",
        challengeDate = "2024-10-13",
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        startTime = "08:00",
        endTime = "09:00",
        imageExtension = "https://cdn.pixabay.com/photo/2016/03/27/23/00/weight-lifting-1284616_1280.jpg",
        currentParticipantNum = 2,
        maxParticipantNum = 4,
        hostId = 1L,
        categoryId = 1,
        challengeId = 2
    ),
    ChallengeResponse(
        challengeName = "자기 계발 독서",
        challengeDate = "2024-10-14",
        startTime = "10:00",
        endTime = "11:30",
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        imageExtension = "https://cdn.pixabay.com/photo/2017/08/09/10/32/reading-2614105_1280.jpg",
        currentParticipantNum = 4,
        maxParticipantNum = 4,
        hostId = 2,
        categoryId = 1,
        challengeId = 2
    ),
    ChallengeResponse(
        challengeName = "악기 연습",
        challengeDate = "2024-10-15",
        startTime = "12:00",
        endTime = "13:30",
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        imageExtension = "https://cdn.pixabay.com/photo/2017/06/24/04/31/piano-2436664_1280.jpg",
        currentParticipantNum = 1,
        maxParticipantNum = 4,
        hostId = 2L,
        categoryId = 2,
        challengeId = 7
    ),
    ChallengeResponse(
        challengeName = "수학 공부",
        challengeDate = "2024-10-17",
        startTime = "18:00",
        endTime = "20:00",
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        imageExtension = "https://cdn.pixabay.com/photo/2020/09/23/03/54/background-5594879_1280.jpg",
        currentParticipantNum = 3,
        maxParticipantNum = 4,
        hostId = 4L,
        categoryId = 3,
        challengeId = 22
    ),
    ChallengeResponse(
        challengeName = "토익 D-2",
        challengeDate = "2024-10-17",
        startTime = "18:00",
        endTime = "20:00",
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        maxParticipantNum = 4,
        currentParticipantNum = 2,
        imageExtension = "https://cdn.pixabay.com/photo/2018/09/26/09/07/education-3704026_1280.jpg",
        hostId = 4L,
        categoryId = 3,
        challengeId = 23
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
                challengeId = response.challengeId
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