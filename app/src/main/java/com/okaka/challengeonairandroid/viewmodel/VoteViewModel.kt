package com.okaka.challengeonairandroid.viewmodel

import androidx.lifecycle.ViewModel
import com.okaka.challengeonairandroid.model.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VoteViewModel @Inject constructor(private val challengeRepository: ChallengeRepository) : ViewModel() {

}