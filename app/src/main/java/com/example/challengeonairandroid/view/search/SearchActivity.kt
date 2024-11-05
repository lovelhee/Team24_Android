package com.example.challengeonairandroid.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeonairandroid.R
import com.example.challengeonairandroid.databinding.ActivitySearchBinding
import com.example.challengeonairandroid.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var searchBinding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)

        searchResultAdapter = SearchResultAdapter()
        searchBinding.rvSearchResult.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchResultAdapter
        }

        lifecycleScope.launchWhenStarted {
            searchViewModel.challenges.collect { challenges ->
                searchResultAdapter.submitList(challenges)
            }
        }

        searchBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchViewModel.setSearchQuery(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        searchBinding.rgChallengeCategory.setOnCheckedChangeListener { _, checkedId ->
            val selectedCategoryId = when (checkedId) {
                R.id.rbExercise -> 0
                R.id.rbDevelopment -> 1
                R.id.rbHobby -> 2
                R.id.rbStudy -> 3
                else -> 0
            }
            searchViewModel.setCategoryId(selectedCategoryId) // ViewModel에 선택된 categoryId 전달
        }
    }
}