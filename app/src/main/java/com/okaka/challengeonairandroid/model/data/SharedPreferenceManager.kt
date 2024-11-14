package com.okaka.challengeonairandroid.model.data

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceManager @Inject constructor(
    private val prefs: SharedPreferences
) {
    private val editor = prefs.edit()

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NICKNAME = "user_nickname"
        private const val KEY_USER_IMAGE_URL = "user_image_url"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveUserId(userId: String) {
        editor.putString(KEY_USER_ID, userId).apply()
    }

    fun saveUserNickname(nickname: String) {
        editor.putString(KEY_USER_NICKNAME, nickname).apply()
    }

    fun saveUserImageUrl(imageUrl: String) {
        editor.putString(KEY_USER_IMAGE_URL, imageUrl).apply()
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun getUserId(): String? = prefs.getString(KEY_USER_ID, null)
    fun getUserNickname(): String? = prefs.getString(KEY_USER_NICKNAME, null)
    fun getUserImageUrl(): String? = prefs.getString(KEY_USER_IMAGE_URL, null)
    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun clearUserInfo() {
        editor.apply {
            remove(KEY_USER_ID)
            remove(KEY_USER_NICKNAME)
            remove(KEY_USER_IMAGE_URL)
            putBoolean(KEY_IS_LOGGED_IN, false)
            apply()
        }
    }
}