package com.okaka.challengeonairandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okaka.challengeonairandroid.model.data.entity.Challenge
import com.okaka.challengeonairandroid.model.repository.ChallengeRepository
import com.okaka.challengeonairandroid.model.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    private val _challengesList = MutableStateFlow<List<Challenge>>(emptyList())
    val challengesList: StateFlow<List<Challenge>> = _challengesList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _categoryId = MutableStateFlow(0) // 초기값 설정
    val categoryId: StateFlow<Int> get() = _categoryId.asStateFlow()

    private val _searchQuery = MutableStateFlow("") // 검색어 상태 추가
    val searchQuery: StateFlow<String> get() = _searchQuery.asStateFlow()

    private val _filteredChallenges = MutableStateFlow<List<Challenge>>(emptyList())
    val filteredChallenges: StateFlow<List<Challenge>> = _filteredChallenges.asStateFlow()

    fun loadAllChallenges() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = challengeRepository.getAllChallenges()
                if (response != null) {
                    _challengesList.value = response
                    observeCategoryChanges()
                }
            } catch (e: Exception) {
                // 에러 처리
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setCategoryId(id: Int) {
        _categoryId.value = id
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun observeCategoryChanges() {
        viewModelScope.launch {
            categoryId.collect {
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
        val filteredList = challengesList.value.filter { challenge ->
            challenge.categoryId == categoryId.value &&
                    challenge.challengeName.contains(searchQuery.value, ignoreCase = true)
        }
        _filteredChallenges.value = filteredList
    }
}