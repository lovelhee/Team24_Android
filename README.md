# Team24_Android

## 10주차 코드 리뷰

### ✨ 세부 사항

#### 1. 챌린지 검색 기능 구현
- [#4] 챌린지 검색 기능 구현(https://github.com/kakao-tech-campus-2nd-step3/Team24_Android/pull/23)
- SearchActivity.kt
- SearchResultAdapter.kt
- activity_search.xml

#### 2. 챌린지 생성 기능 구현
- [#5] 챌린지 생성 뷰모델 구현(https://github.com/kakao-tech-campus-2nd-step3/Team24_Android/pull/26)
- CreateChallengeActivity.kt
- CreateChallengeStep1Fragment.kt
- CreateChallengeStep2Fragment.kt
- CreateChallengeStepAdapter.kt

#### 3. 챌린지 완성 기능 구현
- [#5] 챌린지 완성 다이얼로그 레이아웃 수정(https://github.com/kakao-tech-campus-2nd-step3/Team24_Android/pull/27)
- CreateChallengeCompletedDialog.kt

#### 4. 홈 화면 기능 구현
- [홈 화면 기능 구현](https://github.com/kakao-tech-campus-2nd-step3/Team24_Android/commit/39d226be9ad214b86313b82520f13e44b108d044)
- HomeActivity.kt
- ParentAdapter.kt
- ChilAdpater.kt

---
### ❓ 질문 사항

#### 1. 홈 화면에서의 중첩 리사이클러뷰에 대하여 여쭤보고 싶습니다.

현재 홈 화면은 중첩된 리사이클러뷰로 구성되어 있습니다. 이때 'item_home_parent.xml'은 한 번만 보여지면 되는데, 이런 경우에도 중첩된 리사이클러뷰가 더 효율적인가요? 아니면 스크롤뷰로 변경한 후, 카테고리별 챌린지 항목만 리사이클러뷰로 구현하는 것이 더 효율적일까요?

#### 2. 홈과 검색 화면의 챌린지 목록 공유에 대한 질문
홈 화면과 검색 화면에서 동일한 챌린지 목록을 사용할 때, 홈 화면에서 받은 응답을 그대로 활용할 수 있는 최선의 방법이 궁금합니다. 이 경우 공용 ViewModel을 사용하는 것이 좋을지, 아니면 다른 방식이 있을까요?

#### 3. 예외 처리에 대한 질문 (레포지토리와 ViewModel)
데이터 처리 과정에서 예외가 발생할 수 있을 때, 일반적으로 예외를 null 처리하거나 try-catch를 사용하는 시점을 레포지토리와 ViewModel 중 어느 쪽에서 하는 것이 좋을지 알고 싶습니다.

#### 4. Room에 데이터 저장 전략
Room에 데이터를 저장하여 API 요청을 줄이는 방향으로 설계하고자 합니다. 이때, 자주 갱신되지 않는 정보만 저장하는 것이 좋은지, 혹은 갱신 빈도와 관계없이 모든 데이터를 캐싱하는 게 좋을지에 대해 고민하고 있습니다.

---
### 🔗 피그마 링크
[피그마 디자인 보기](https://www.figma.com/design/t14LOydaYTHOitC2Q7bwMf/%EC%8B%A4%EC%8B%9C%EA%B0%84%EC%B1%8C%EB%A6%B0%EC%A7%80?node-id=0-1&t=sfSd5mXgkwwuwp4c-1)
