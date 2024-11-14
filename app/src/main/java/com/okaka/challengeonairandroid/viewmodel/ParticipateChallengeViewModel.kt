package com.okaka.challengeonairandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okaka.challengeonairandroid.model.api.response.ChallengeResponse
import com.okaka.challengeonairandroid.model.api.response.UserProfileResponse
import com.okaka.challengeonairandroid.model.api.response.UserProfileSpecificResponse
import com.okaka.challengeonairandroid.model.repository.ChallengeRepository
import com.okaka.challengeonairandroid.model.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _specificUser = MutableStateFlow<UserProfileSpecificResponse?>(null)
    val specificUser: StateFlow<UserProfileSpecificResponse?> = _specificUser

    // 로직 구현 후엔 sharedPreference에서 가져오게 해야함
    private val currentUserId = "current_user_id"

    private val _isHost = MutableStateFlow(false)
    val isHost: StateFlow<Boolean> = _isHost

    fun joinChallenge(challengeId: Long) {
        viewModelScope.launch {
            val response = challengeRepository.reserveChallenge(challengeId)

            if (response != null) {
                loadChallengeDetails(challengeId)
            }
        }
    }

    fun loadChallengeDetails(challengeId: Long) {
        viewModelScope.launch {
            val challengeResponse = challengeRepository.getChallengeDetails(challengeId)
            _challenge.value = challengeResponse

            challengeResponse?.let {
                Log.d("ParticipateChallengeViewModel", "Host ID retrieved: ${it.hostId}")
                if (it.hostId != null) {
                    loadUserProfile(it.hostId)
                } else {
                    Log.e("ParticipateChallengeViewModel", "Host ID is null, cannot load user profile")
                }
                _isHost.value = it.hostId == currentUserId
            }
        }
    }

    private fun loadUserProfile(hostId: String) {
        viewModelScope.launch {
            val specificUserProfileResponse = userProfileRepository.getSpecificUserProfile(hostId)
            _specificUser.value = specificUserProfileResponse
            Log.d("ParticipateChallengeViewModel", "Host Info: $specificUserProfileResponse")
        }
    }

    fun deleteChallenge(challengeId: Long) {
        viewModelScope.launch {
            Log.d("ParticipateChallengeViewModel", "Attempting to delete challenge with ID: $challengeId")
            val response = challengeRepository.deleteChallenge(challengeId)
            if (response != null) {
                Log.d("ParticipateChallengeViewModel", "Challenge successfully deleted.")
            } else {
                Log.e("ParticipateChallengeViewModel", "Failed to delete challenge.")
            }
        }
    }

    fun cancelChallenge(challengeId: Long) {
        viewModelScope.launch {
            val response = challengeRepository.cancelChallenge(challengeId)
            if (response != null) {
                _challenge.value = _challenge.value?.copy(currentParticipantNum = 0)
            }
        }
    }

}