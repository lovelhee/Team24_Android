package com.okaka.challengeonairandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okaka.challengeonairandroid.model.api.response.ChallengeResponse
import com.okaka.challengeonairandroid.model.data.entity.Challenge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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