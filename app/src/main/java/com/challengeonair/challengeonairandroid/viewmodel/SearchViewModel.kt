package com.challengeonair.challengeonairandroid.viewmodel

import androidx.lifecycle.ViewModel
import com.challengeonair.challengeonairandroid.model.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel()