package com.okaka.challengeonairandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okaka.challengeonairandroid.model.api.response.UserProfileUpdateRequest
import com.okaka.challengeonairandroid.model.data.SharedPreferenceManager
import com.okaka.challengeonairandroid.model.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    var nickname: String = sharedPreferenceManager.getUserNickname() ?: ""
    var imageUrl: String = sharedPreferenceManager.getUserImageUrl() ?: ""

    fun saveProfile(newNickname: String, newImageUrl: String) {
        nickname = newNickname
        imageUrl = newImageUrl

        viewModelScope.launch {
            try {
                val request = UserProfileUpdateRequest(
                    userNickName = newNickname,
                    imageUrl = newImageUrl
                )

                val response = userProfileRepository.updateUserProfile(request)

                response?.let { updateResponse ->
                    sharedPreferenceManager.saveUserNickname(newNickname)
                    updateResponse.imageUrl?.let { sharedPreferenceManager.saveUserImageUrl(it) }
                }
            } catch (e: Exception) {
                Log.e("SetProfileViewModel", "Error saving profile", e)
            }
        }
    }
}