package com.okaka.challengeonairandroid.model.data

import com.okaka.challengeonairandroid.model.api.response.AllHistoriesResponse
import com.okaka.challengeonairandroid.model.api.response.ChallengeResponse
import com.okaka.challengeonairandroid.model.api.response.HistoryResponse
import com.okaka.challengeonairandroid.model.api.response.UserProfileResponse
import com.okaka.challengeonairandroid.model.data.entity.Challenge
import com.okaka.challengeonairandroid.model.data.entity.History

object FakeLocalData {
    private var userId = "1234L"
    private var userNickName = "김민혁"
    private var userImgUrl = "https://picsum.photos/id/237/200/300"

    fun getUserId(): String = userId
    fun getUserNickName(): String = userNickName
    fun getUserImgUrl(): String = userImgUrl
}

val dummyChallenge: List<Challenge> = listOf(
    Challenge(
        challengeName = "아침 러닝",
        challengeBody = "222",
        point = 10,
        challengeDate = "2024-10-12",
        startTime = "06:00",
        endTime = "07:00",
        imageUrl = "https://media.istockphoto.com/id/1704187857/ko/%EC%82%AC%EC%A7%84/%EC%84%A0%EB%91%90-%EC%A3%BC%EC%9E%90-%EB%94%B0%EB%9D%BC%EC%9E%A1%EA%B8%B0.jpg?s=1024x1024&w=is&k=20&c=G09WByt9tu2ry6nUOpPiWFfxMTNsJpHVRM3D8oxdzmE=",
        currentParticipantNum = 3,
        maxParticipantNum = 4,
        minParticipantNum = 2,
        hostId = "1L",
        categoryId = 0,
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
        imageUrl = "https://cdn.pixabay.com/photo/2016/03/27/23/00/weight-lifting-1284616_1280.jpg",
        currentParticipantNum = 2,
        maxParticipantNum = 4,
        hostId = "1L",
        categoryId = 1,
        challengeId = 2
    ),
    Challenge(
        challengeName = "자기 계발 독서",
        challengeDate = "2024-10-14",
        startTime = "10:00",
        endTime = "11:30",
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        imageUrl = "https://cdn.pixabay.com/photo/2017/08/09/10/32/reading-2614105_1280.jpg",
        currentParticipantNum = 4,
        maxParticipantNum = 4,
        hostId = "2L",
        categoryId = 1,
        challengeId = 2
    ),
    Challenge(
        challengeName = "악기 연습",
        challengeDate = "2024-10-15",
        startTime = "12:00",
        endTime = "13:30",
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        imageUrl = "https://cdn.pixabay.com/photo/2017/06/24/04/31/piano-2436664_1280.jpg",
        currentParticipantNum = 1,
        maxParticipantNum = 4,
        hostId = "2L",
        categoryId = 2,
        challengeId = 7
    ),
    Challenge(
        challengeName = "수학 공부",
        challengeDate = "2024-10-17",
        startTime = "18:00",
        endTime = "20:00",
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        imageUrl = "https://cdn.pixabay.com/photo/2020/09/23/03/54/background-5594879_1280.jpg",
        currentParticipantNum = 3,
        maxParticipantNum = 4,
        hostId = "4L",
        categoryId = 3,
        challengeId = 22
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
        imageUrl = "https://cdn.pixabay.com/photo/2018/09/26/09/07/education-3704026_1280.jpg",
        hostId = "4L",
        categoryId = 3,
        challengeId = 23
    )

)

val dummyChallenge1 = Challenge(
    challengeName = "수학 공부",
    challengeDate = "2024-10-17",
    startTime = "18:00",
    endTime = "20:00",
    challengeBody = "222",
    point = 10,
    minParticipantNum = 2,
    imageUrl = "https://cdn.pixabay.com/photo/2020/09/23/03/54/background-5594879_1280.jpg",
    currentParticipantNum = 3,
    maxParticipantNum = 4,
    hostId = "4L",
    categoryId = 3,
    challengeId = 22
)

val dummyChallenge2 = Challenge(
    challengeName = "토익 D-2",
    challengeDate = "2024-10-17",
    startTime = "18:00",
    endTime = "20:00",
    challengeBody = "222",
    point = 10,
    minParticipantNum = 2,
    maxParticipantNum = 4,
    currentParticipantNum = 2,
    imageUrl = "https://cdn.pixabay.com/photo/2018/09/26/09/07/education-3704026_1280.jpg",
    hostId = "4L",
    categoryId = 3,
    challengeId = 23
)

val dummyHistoryList = listOf(
    History(challenge = dummyChallenge1, isSucceeded = true, isHost = true, point = 100),
    History(challenge = dummyChallenge2, isSucceeded = true, isHost = true, point = 200)
)

val dummyHistoryListResponse = AllHistoriesResponse(
    histories = listOf(
        HistoryResponse(
            challenge = Challenge(
                categoryId = 0,
                challengeName = "아침 운동 챌린지",
                challengeBody = "매일 아침 30분 운동하기",
                point = 100,
                challengeDate = "2024-03-01",
                startTime = "06:00",
                endTime = "07:00",
                imageUrl = "https://example.com/morning_exercise.jpg",
                minParticipantNum = 5,
                maxParticipantNum = 20,
                currentParticipantNum = 12,
                hostId = "2L",
                challengeId = 1
            ),
            isSucceeded = true,
            isHost = false,
            point = 100
        ),
        HistoryResponse(
            challenge = Challenge(
                categoryId = 1,
                challengeName = "독서 챌린지",
                challengeBody = "한 달 동안 5권 책 읽기",
                point = 150,
                challengeDate = "2024-03-15",
                startTime = "00:00",
                endTime = "23:59",
                imageUrl = "https://example.com/reading_challenge.jpg",
                minParticipantNum = 10,
                maxParticipantNum = 50,
                currentParticipantNum = 25,
                hostId = "2L",
                challengeId = 1
            ),
            isSucceeded = false,
            isHost = true,
            point = 100
        )
    )
)

val dummyUserProfileResponse = UserProfileResponse(
    userNickName = "챌린지마스터",
    imageUrl = "https://example.com/profile_image.jpg",
    point = 1000
)

val dummyChallengeResponses: List<ChallengeResponse> = listOf(
    ChallengeResponse(
        categoryId = 1,
        challengeName = "아침 운동 챌린지",
        challengeBody = "매일 아침 30분 운동하기",
        point = 100,
        challengeDate = "2024-03-01",
        startTime = "06:00",
        endTime = "07:00",
        imageUrl = "https://picsum.photos/200/300",
        minParticipantNum = 5,
        maxParticipantNum = 20,
        currentParticipantNum = 12,
        hostId = "2L",
        challengeId = 1
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "독서 챌린지",
        challengeBody = "한 달 동안 5권 책 읽기",
        point = 150,
        challengeDate = "2024-03-15",
        startTime = "00:00",
        endTime = "23:59",
        imageUrl = "https://picsum.photos/200/300",
        minParticipantNum = 10,
        maxParticipantNum = 50,
        currentParticipantNum = 25,
        hostId = "3L",
        challengeId = 1
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "물 마시기 챌린지",
        challengeBody = "하루 2리터 물 마시기",
        point = 80,
        challengeDate = "2024-04-01",
        startTime = "08:00",
        endTime = "22:00",
        imageUrl = "https://picsum.photos/200/300",
        minParticipantNum = 3,
        maxParticipantNum = 100,
        currentParticipantNum = 75,
        hostId = "4L",
        challengeId = 0
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "코딩 스터디 챌린지",
        challengeBody = "매일 2시간 코딩 공부하기",
        point = 200,
        challengeDate = "2024-04-15",
        startTime = "19:00",
        endTime = "21:00",
        imageUrl = "https://picsum.photos/200/300",
        minParticipantNum = 5,
        maxParticipantNum = 15,
        currentParticipantNum = 8,
        hostId = "5L",
        challengeId = 0
    ),
    ChallengeResponse(
        categoryId = 1,
        challengeName = "환경 보호 챌린지",
        challengeBody = "일주일 동안 일회용품 사용 줄이기",
        point = 120,
        challengeDate = "2024-05-01",
        startTime = "00:00",
        endTime = "23:59",
        imageUrl = "https://picsum.photos/200/300",
        minParticipantNum = 20,
        maxParticipantNum = 200,
        currentParticipantNum = 150,
        hostId = "2L",
        challengeId = 0
    ),
    ChallengeResponse(
        challengeName = "취미 요리",
        challengeDate = "2024-10-16",
        startTime = "15:00",
        endTime = "17:00",
        imageUrl = "https://cdn.pixabay.com/photo/2016/11/18/15/31/cooking-1835369_1280.jpg",
        currentParticipantNum = 2,
        maxParticipantNum = 4,
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        hostId = "3L",
        challengeId = 3,
        categoryId = 1
    ),
    ChallengeResponse(
        challengeName = "뜨개질로 목도리 만들기",
        challengeDate = "2024-10-16",
        startTime = "15:00",
        endTime = "17:00",
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/08/23/52/tissue-5276453_1280.jpg",
        currentParticipantNum = 2,
        maxParticipantNum = 4,
        challengeBody = "222",
        point = 10,
        minParticipantNum = 2,
        hostId = "3L",
        categoryId = 3,
        challengeId = 1
    )
)
