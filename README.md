# Team24_Android

## 9주차 코드 리뷰

### ✨ 세부 사항

#### 1. 챌린지 생성 페이지 TabLayout으로 변경
- 로직파일
  - CreateChallengeActivity.kt
  - CreateChallengeStep1Fragment.kt
  - CreateChallengeStep2Fragment.kt
  - CreatechallengeStepAdapter.kt
- 레이아웃
  - activity_create_challenge.xml
  - fragment_create_challenge_step1.xml
  - fragment_create_challenge_step2.xml

#### 2. 홈화면 중첩 리사이클러뷰로 변경 및 어댑터 설정
- 로직파일
  - HomeActicity.kt
  - ParentAdapter.kt
  - ChildAdapter.kt
- 레이아웃
  - category_challenge_item.xml
  - activity_home.xml
  - home_parent_item.xml

#### 3. 홈 -> 챌린지 생성 -> 챌린지 완료 -> 홈 흐름 구현
- StartActivity(~), finish()로 구현 완료

#### 4. 챌린지 생성 데이터 변수에 저장 구현
- 사용자가 선택한 데이터 값에 따라 변수에 저장하도록 구현
- 추후에 viewModel 연결 및 서버 저장 구현 예정

---
### 🖼️ ️스크린샷
[3_액티비티간_흐름_구현.webm](https://github.com/user-attachments/assets/6d2abeb2-5d96-4435-9a9c-0902e36eeba6)
[4_챌린지_생성_데이터_저장_구현.webm](https://github.com/user-attachments/assets/612cf164-1593-4dd8-943b-9bbb0dfa2ed3)

---
### ❓ 질문 사항

#### 1. 챌린지 생성 단계에서 사용자가 입력한 값을 관리하기 위해 ViewModel을 사용하는 것이 적합한 방법일까요?

챌린지 생성과 같은 단계별 입력 과정에서 각 단계의 데이터를 관리하기 위해 ViewModel을 사용하는 것이 일반적으로 권장되는 방법이라고 알고 있습니다. ViewModel을 통해 데이터가 액티비티나 프래그먼트가 재생성되더라도 유지될 수 있다는 점에서 장점이 있을 것 같은데, 이렇게 진행하는 것이 실무에서도 유용한 방법인지 궁금합니다.

혹시 이와 관련해 더 효율적인 패턴이나 고려해야 할 추가적인 사항이 있을까요?

#### 2. 버튼 클릭 시 날짜와 시간 설정을 저장하는 방식이 적절한지 문의드립니다. (fragment_create_challenge_step2.xml 관련)

현재 Step2 단계에서 사용자가 날짜와 시간을 선택하고, 이후 설정 완료 버튼을 눌렀을 때 데이터가 저장되도록 구현하려고 합니다. DatePicker와 TimePicker로 값을 선택하고 나서 ViewModel을 통해 데이터를 관리할 계획인데, 이러한 방식이 사용자의 입력을 관리하는 데 적절한지 고민이 됩니다. 특히, 날짜와 시간 선택 값을 프래그먼트 간 또는 다른 액티비티로 안전하게 전달하고 저장하는 방법으로 이 방식이 실무에서도 자주 사용되는지 궁금합니다.

혹시 더 나은 접근법이나 구조적인 개선이 필요한 부분이 있을지 조언을 구하고 싶습니다.

---
### 🔗 피그마 링크
[피그마 디자인 보기](https://www.figma.com/design/t14LOydaYTHOitC2Q7bwMf/%EC%8B%A4%EC%8B%9C%EA%B0%84%EC%B1%8C%EB%A6%B0%EC%A7%80?node-id=0-1&t=sfSd5mXgkwwuwp4c-1)
