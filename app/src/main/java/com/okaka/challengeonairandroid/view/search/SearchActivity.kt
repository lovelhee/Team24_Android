package com.okaka.challengeonairandroid.view.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ActivitySearchBinding
import com.okaka.challengeonairandroid.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var searchBinding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchResultAdapter: SearchResultAdapter

    @SuppressLint("ClickableViewAccessibility")
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

        searchBinding.btnBack.setOnClickListener {
            finish()
        }

        searchBinding.etSearch.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = searchBinding.etSearch.compoundDrawables[2] // drawableEnd는 index 2
                if (drawableEnd != null) {
                    val drawableWidth = drawableEnd.bounds.width()
                    val touchArea = searchBinding.etSearch.width - searchBinding.etSearch.paddingEnd - drawableWidth

                    if (event.rawX >= touchArea) {
                        searchBinding.etSearch.text.clear()
                        return@setOnTouchListener true
                    }
                }
            }
            false
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

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }
}