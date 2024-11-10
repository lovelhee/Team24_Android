package com.challengeonair.challengeonairandroid.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.challengeonair.challengeonairandroid.databinding.ActivityHomeBinding
import com.challengeonair.challengeonairandroid.model.data.Category
import com.challengeonair.challengeonairandroid.model.data.Challenge
import com.challengeonair.challengeonairandroid.view.challenge.CreateChallengeActivity
import com.challengeonair.challengeonairandroid.view.mypage.MyPageActivity
import com.challengeonair.challengeonairandroid.view.search.SearchActivity
import com.challengeonair.challengeonairandroid.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnComplete.setOnClickListener {
            startActivity(CreateChallengeActivity.intent(this))
        }

        binding.ibSearch.setOnClickListener {
            startActivity(SearchActivity.intent(this))
        }

        binding.ibMyPage.setOnClickListener {
            startActivity(MyPageActivity.intent(this))
        }


        // 더미 데이터
        val categories = listOf(
            Category(1, "운동", "운동 카테고리"),
            Category(2, "자기 계발", "자기 계발 카테고리"),
            Category(3, "취미", "취미 카테고리"),
            Category(4, "공부", "공부 카테고리")
        )

        val challengesByCategory: Map<Long, List<Challenge>> = mapOf(
            1L to listOf(
                Challenge(
                    challengeName = "아침 러닝",
                    challengeBody = "222",
                    point = 10,
                    challengeDate = "2024-10-12",
                    startTime = "06:00",
                    endTime = "07:00",
                    imageExtension = "https://media.istockphoto.com/id/1704187857/ko/%EC%82%AC%EC%A7%84/%EC%84%A0%EB%91%90-%EC%A3%BC%EC%9E%90-%EB%94%B0%EB%9D%BC%EC%9E%A1%EA%B8%B0.jpg?s=1024x1024&w=is&k=20&c=G09WByt9tu2ry6nUOpPiWFfxMTNsJpHVRM3D8oxdzmE=",
                    currentParticipantNum = 3,
                    maxParticipantNum = 4,
                    minParticipantNum = 2,
                    hostId = 1L,
                    categoryId = 1,
                    challengeId = 1
                ),
                Challenge(
                    challengeName = "3대 500을 위하여",
                    challengeDate = "2024-10-13",
                    challengeBody = "222",
                    point = 10,
                    minParticipantNum = 2,
                    startTime = "08:00",
                    endTime = "09:00",
                    imageExtension = "https://cdn.pixabay.com/photo/2016/03/27/23/00/weight-lifting-1284616_1280.jpg",
                    currentParticipantNum = 2,
                    maxParticipantNum = 4,
                    hostId = 1L,
                    categoryId = 1,
                    challengeId = 2
                )
            ),
            2L to listOf(
                Challenge(
                    challengeName = "자기 계발 독서",
                    challengeDate = "2024-10-14",
                    startTime = "10:00",
                    endTime = "11:30",
                    challengeBody = "222",
                    point = 10,
                    minParticipantNum = 2,
                    imageExtension = "https://cdn.pixabay.com/photo/2017/08/09/10/32/reading-2614105_1280.jpg",
                    currentParticipantNum = 4,
                    maxParticipantNum = 4,
                    hostId = 2L,
                    categoryId = 2,
                    challengeId = 3
                ),
                Challenge(
                    challengeName = "악기 연습",
                    challengeDate = "2024-10-15",
                    startTime = "12:00",
                    endTime = "13:30",
                    challengeBody = "222",
                    point = 10,
                    minParticipantNum = 2,
                    imageExtension = "https://cdn.pixabay.com/photo/2017/06/24/04/31/piano-2436664_1280.jpg",
                    currentParticipantNum = 1,
                    maxParticipantNum = 4,
                    hostId = 2L,
                    categoryId = 2,
                    challengeId = 1
                )
            ),
            3L to listOf(
                Challenge(
                    challengeName = "취미 요리",
                    challengeDate = "2024-10-16",
                    startTime = "15:00",
                    endTime = "17:00",
                    imageExtension = "https://cdn.pixabay.com/photo/2016/11/18/15/31/cooking-1835369_1280.jpg",
                    currentParticipantNum = 2,
                    maxParticipantNum = 4,
                    challengeBody = "222",
                    point = 10,
                    minParticipantNum = 2,
                    hostId = 3L,
                    categoryId = 3,
                    challengeId = 1
                ),
                Challenge(
                    challengeName = "뜨개질로 목도리 만들기",
                    challengeDate = "2024-10-16",
                    startTime = "15:00",
                    endTime = "17:00",
                    imageExtension = "https://cdn.pixabay.com/photo/2020/06/08/23/52/tissue-5276453_1280.jpg",
                    currentParticipantNum = 2,
                    maxParticipantNum = 4,
                    challengeBody = "222",
                    point = 10,
                    minParticipantNum = 2,
                    hostId = 3L,
                    categoryId = 3,
                    challengeId = 1
                )
            ),
            4L to listOf(
                Challenge(
                    challengeName = "수학 공부",
                    challengeDate = "2024-10-17",
                    startTime = "18:00",
                    endTime = "20:00",
                    challengeBody = "222",
                    point = 10,
                    minParticipantNum = 2,
                    imageExtension = "https://cdn.pixabay.com/photo/2020/09/23/03/54/background-5594879_1280.jpg",
                    currentParticipantNum = 3,
                    maxParticipantNum = 4,
                    hostId = 4L,
                    categoryId = 4,
                    challengeId = 2
                ),
                Challenge(
                    challengeName = "토익 D-2",
                    challengeDate = "2024-10-17",
                    startTime = "18:00",
                    endTime = "20:00",
                    challengeBody = "222",
                    point = 10,
                    minParticipantNum = 2,
                    maxParticipantNum = 4,
                    currentParticipantNum = 2,
                    imageExtension = "https://cdn.pixabay.com/photo/2018/09/26/09/07/education-3704026_1280.jpg",
                    hostId = 4L,
                    categoryId = 4,
                    challengeId = 7
                )
            )
        )

        val parentAdapter = ParentAdapter(categories, challengesByCategory)
        binding.rvParent.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = parentAdapter
        }
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}