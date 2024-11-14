package com.okaka.challengeonairandroid.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.FragmentSearchBinding
import com.okaka.challengeonairandroid.model.data.auth.TokenManager
import com.okaka.challengeonairandroid.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val searchBinding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var searchResultAdapter: SearchResultAdapter

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return searchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchResultAdapter = SearchResultAdapter()
        searchBinding.rvSearchResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchResultAdapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewModel.filteredChallenges.collect { challenges ->
                searchResultAdapter.submitList(challenges)
            }
        }

        searchBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        searchBinding.etSearch.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = searchBinding.etSearch.compoundDrawables[2]
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
                homeViewModel.setSearchQuery(s.toString())
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
            homeViewModel.setCategoryId(selectedCategoryId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}