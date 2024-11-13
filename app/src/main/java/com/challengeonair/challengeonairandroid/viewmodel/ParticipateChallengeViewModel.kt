package com.challengeonair.challengeonairandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeResponse
import com.challengeonair.challengeonairandroid.model.api.response.UserProfileResponse
import com.challengeonair.challengeonairandroid.model.repository.ChallengeRepository
import com.challengeonair.challengeonairandroid.model.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParticipateChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _challenge = MutableStateFlow<ChallengeResponse?>(null)
    val challenge: StateFlow<ChallengeResponse?> = _challenge

    private val _user = MutableStateFlow<UserProfileResponse?>(null)
    val user: StateFlow<UserProfileResponse?> = _user

    // 로직 구현 후엔 sharedPreference에서 가져오게 해야함
    private val currentUserId = "current_user_id"
    val isHost: StateFlow<Boolean> = MutableStateFlow(false)

    fun loadChallengeDetails(challengeId: Long) {
        viewModelScope.launch {
            val challengeResponse = challengeRepository.getChallengeDetails(challengeId)
            _challenge.value = challengeResponse

            challengeResponse?.let {
                loadUserProfile(it.hostId)
                (isHost as MutableStateFlow).value = it.hostId == currentUserId
            }
        }
    }

    private fun loadUserProfile(hostId: String) {
        viewModelScope.launch {
            val userProfileResponse = userProfileRepository.getUserProfile()
            _user.value = userProfileResponse
        }
    }


    // 더미 데이터용
    fun setChallengeData(challenge: ChallengeResponse) {
        _challenge.value = challenge
    }

    fun setUserData(user: UserProfileResponse) {
        _user.value = user
    }
}