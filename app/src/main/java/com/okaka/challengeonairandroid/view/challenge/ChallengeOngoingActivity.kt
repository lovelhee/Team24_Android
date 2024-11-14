package com.okaka.challengeonairandroid.view.challenge

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ActivityChallengeOngoingBinding
import dagger.hilt.android.AndroidEntryPoint
import org.jitsi.meet.sdk.BroadcastEvent
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo
import java.net.URL
import java.util.UUID

@AndroidEntryPoint
class ChallengeOngoingActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ChallengeOngoing"
        private const val SERVER_URL = "https://meet.jit.si"
        private const val RC_SIGN_IN = 9001
        private const val JOIN_TIMEOUT_MS = 30000L // 30 seconds timeout
    }

    private lateinit var binding: ActivityChallengeOngoingBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private var isConferenceJoined = false
    private var googleIdToken: String? = null
    private var currentRoomName: String? = null
    private val joinTimeoutHandler = Handler(Looper.getMainLooper())

    private val joinTimeoutRunnable = Runnable {
        if (!isConferenceJoined) {
            Log.e(TAG, "Conference join timeout for room: $currentRoomName")
            hideLoading()
            showError("회의 참가 시간 초과. 다시 시도해주세요.")
        }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { intent ->
                val event = BroadcastEvent(intent)
                Log.d(TAG, "Received broadcast event: ${event.type}")
                Log.d(TAG, "Current room: $currentRoomName")

                when (event.type) {
                    BroadcastEvent.Type.CONFERENCE_JOINED -> {
                        isConferenceJoined = true
                        joinTimeoutHandler.removeCallbacks(joinTimeoutRunnable)
                        Log.i(TAG, "Successfully joined conference: $currentRoomName")
                        hideLoading()
                    }

                    BroadcastEvent.Type.CONFERENCE_TERMINATED -> {
                        isConferenceJoined = false
                        joinTimeoutHandler.removeCallbacks(joinTimeoutRunnable)
                        val error = intent.extras?.getString("error")
                        if (error != null) {
                            Log.e(TAG, "Conference terminated with error: $error")
                            showError("회의 연결 실패: $error")
                        } else {
                            Log.i(TAG, "Conference terminated normally")
                        }
                        hideLoading()
                    }

                    BroadcastEvent.Type.CONFERENCE_WILL_JOIN -> {
                        Log.d(TAG, "Attempting to join conference: $currentRoomName")
                        joinTimeoutHandler.postDelayed(joinTimeoutRunnable, JOIN_TIMEOUT_MS)
                    }

                    BroadcastEvent.Type.PARTICIPANT_JOINED -> {
                        Log.d(TAG, "New participant joined conference: $currentRoomName")
                    }

                    BroadcastEvent.Type.AUDIO_MUTED_CHANGED -> {
                        Log.d(TAG, "Audio mute status changed for conference: $currentRoomName")
                    }

                    BroadcastEvent.Type.VIDEO_MUTED_CHANGED -> {
                        Log.d(TAG, "Video mute status changed for conference: $currentRoomName")
                    }

                    else -> Log.d(TAG, "Unhandled broadcast event: ${event.type}")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengeOngoingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent에서 토큰 받아오기
        googleIdToken = intent.getStringExtra("user_token")
        Log.d(TAG, "Received token from intent: ${googleIdToken?.take(20)}...")

        setupBroadcastReceiver()
        initializeGoogleSignIn()
        setupUIListeners()

        // Intent에서 받아온 토큰이 있으면 바로 초기화
        if (googleIdToken != null) {
            initializeJitsiMeet()
        } else {
            // 토큰이 없는 경우 기존 로직 실행
            val account = GoogleSignIn.getLastSignedInAccount(this)
            Log.d(TAG, "Initial account check: $account")
            Log.d(TAG, "Initial account email: ${account?.email}")
            Log.d(TAG, "Initial ID token available: ${account?.idToken != null}")

            if (account?.idToken == null) {
                Log.d(TAG, "No ID token found, initiating sign out and sign in")
                signOut()
            } else {
                googleIdToken = account.idToken
                Log.d(TAG, "Set googleIdToken length: ${googleIdToken?.length}")
                Log.d(TAG, "Token preview: ${googleIdToken?.take(20)}...")
                initializeJitsiMeet()
            }
        }
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityChallengeOngoingBinding.inflate(layoutInflater)
//        googleIdToken = intent.getStringExtra("user_token")
//        setContentView(binding.root)
//        Log.d(TAG, "Received token from intent: ${googleIdToken?.take(20)}...")
//        setupBroadcastReceiver()
//        initializeGoogleSignIn()
//        setupUIListeners()
//
//        // Check if user is already signed in
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        Log.d(TAG, "Initial account check: $account")
//        Log.d(TAG, "Initial account email: ${account?.email}")
//        Log.d(TAG, "Initial ID token available: ${account?.idToken != null}")
//
//        if (account?.idToken == null) {
//            Log.d(TAG, "No ID token found, initiating sign out and sign in")
//            signOut()
//        } else {
//            googleIdToken = account.idToken
//            Log.d(TAG, "Set googleIdToken length: ${googleIdToken?.length}")
//            Log.d(TAG, "Token preview: ${googleIdToken?.take(20)}...")
//            initializeJitsiMeet()
//        }
//    }

    private fun setupBroadcastReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(BroadcastEvent.Type.CONFERENCE_JOINED.action)
            addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.action)
            addAction(BroadcastEvent.Type.CONFERENCE_WILL_JOIN.action)
            addAction(BroadcastEvent.Type.PARTICIPANT_JOINED.action)
            addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.action)
            addAction(BroadcastEvent.Type.VIDEO_MUTED_CHANGED.action)
        }

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun initializeGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.server_client_id))
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this) {
                googleIdToken = null
                signIn()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)

                Log.d(TAG, "Sign in successful")
                Log.d(TAG, "Account email: ${account?.email}")
                Log.d(TAG, "Account name: ${account?.displayName}")
                Log.d(TAG, "ID Token available: ${account?.idToken != null}")
                Log.d(TAG, "ID Token length: ${account?.idToken?.length}")
                Log.d(TAG, "Token preview: ${account?.idToken?.take(20)}...")

                googleIdToken = account?.idToken
                initializeJitsiMeet()
            } catch (e: ApiException) {
                Log.e(TAG, "Google sign in failed with status code: ${e.statusCode}")
                Log.e(TAG, "Error message: ${e.message}")
                showError("Google 로그인 실패: ${e.localizedMessage}")
            }
        }
    }

    private fun initializeJitsiMeet() {
        try {
            val defaultOptions = JitsiMeetConferenceOptions.Builder()
                .setServerURL(URL(SERVER_URL))
                .setToken(googleIdToken)  // 토큰 설정
                .setFeatureFlag("welcomepage.enabled", false)
                .setFeatureFlag("prejoinpage.enabled", false)
                .setFeatureFlag("call-integration.enabled", false)
                .setFeatureFlag("authentication.enabled", false)
                .setFeatureFlag("google-api.enabled", false)
                .setFeatureFlag("security.enabled", false)
                .build()

            JitsiMeet.setDefaultConferenceOptions(defaultOptions)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize JitsiMeet", e)
            showError("JitsiMeet 초기화 실패: ${e.localizedMessage}")
        }
    }

    private fun setupUIListeners() {
        binding.createButton.setOnClickListener {
            val roomName = binding.roomNameEditText.text.toString().trim()
            if (roomName.isBlank()) {
                val generatedRoomName = generateRoomName()
                binding.roomNameEditText.setText(generatedRoomName)
                createAndJoinRoom(generatedRoomName)
            } else {
                if (!isValidRoomName(roomName)) {
                    binding.roomNameEditText.error = "영문, 숫자, 하이픈만 사용 가능합니다"
                    return@setOnClickListener
                }
                createAndJoinRoom(roomName)
            }
        }

        binding.joinButton.setOnClickListener {
            val roomName = binding.roomNameEditText.text.toString().trim()
            if (roomName.isBlank()) {
                binding.roomNameEditText.error = "채팅방 이름을 입력해주세요"
                return@setOnClickListener
            }

            if (!isValidRoomName(roomName)) {
                binding.roomNameEditText.error = "영문, 숫자, 하이픈만 사용 가능합니다"
                return@setOnClickListener
            }

            joinConference(roomName)
        }
    }

    private fun generateRoomName(): String {
        return "room-${UUID.randomUUID().toString().substring(0, 8)}"
    }

    private fun createAndJoinRoom(roomName: String) {
        if (googleIdToken == null) {
            Log.e(TAG, "Attempting to create room without token")
            showError("Google 인증이 필요합니다")
            return
        }

        try {
            showLoading()
            currentRoomName = roomName

            Log.d(TAG, "Creating room with token length: ${googleIdToken?.length}")
            Log.d(TAG, "Token preview: ${googleIdToken?.take(20)}...")
            Log.d(TAG, "Room name: $roomName")

            val account = GoogleSignIn.getLastSignedInAccount(this)
            Log.d(TAG, "Current account: ${account?.email}")

            val userInfo = JitsiMeetUserInfo().apply {
                displayName = account?.displayName
                email = account?.email
                avatar = account?.photoUrl?.let { URL(it.toString()) }
            }

            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(roomName)
                .setToken(googleIdToken)
                .setServerURL(URL(SERVER_URL))
                .setAudioMuted(false)
                .setVideoMuted(false)
                .setFeatureFlag("authentication.enabled", true)
                .setFeatureFlag("google-api.enabled", true)
                .setFeatureFlag("lobby.enabled", false)
                .setFeatureFlag("calendar.enabled", false)
                .setFeatureFlag("call-integration.enabled", false)
                .setFeatureFlag("recording.enabled", false)
                .setFeatureFlag("live-streaming.enabled", false)
                .setFeatureFlag("meeting-password.enabled", false)
                .setUserInfo(userInfo)
                .build()

            Log.d(TAG, "Launching JitsiMeetActivity...")
            JitsiMeetActivity.launch(this, options)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create and join conference", e)
            hideLoading()
            showError("회의방 생성 실패: ${e.localizedMessage}")
        }
    }

    private fun joinConference(roomName: String) {
        if (googleIdToken == null) {
            Log.e(TAG, "Attempting to join room without token")
            showError("Google 인증이 필요합니다")
            return
        }

        try {
            showLoading()
            currentRoomName = roomName

            Log.d(TAG, "Joining room with token length: ${googleIdToken?.length}")
            Log.d(TAG, "Token preview: ${googleIdToken?.take(20)}...")
            Log.d(TAG, "Room name: $roomName")

            val account = GoogleSignIn.getLastSignedInAccount(this)
            val userInfo = JitsiMeetUserInfo().apply {
                displayName = account?.displayName
                email = account?.email
                avatar = account?.photoUrl?.let { URL(it.toString()) }
            }

            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(roomName)
                .setToken(googleIdToken)
                .setServerURL(URL(SERVER_URL))
                .setAudioMuted(false)
                .setVideoMuted(false)
                .setFeatureFlag("authentication.enabled", true)
                .setFeatureFlag("google-api.enabled", true)
                .setFeatureFlag("lobby.enabled", false)
                .setFeatureFlag("calendar.enabled", false)
                .setFeatureFlag("call-integration.enabled", false)
                .setFeatureFlag("recording.enabled", false)
                .setFeatureFlag("live-streaming.enabled", false)
                .setFeatureFlag("meeting-password.enabled", false)
                .setUserInfo(userInfo)
                .build()

            Log.d(TAG, "Launching JitsiMeetActivity...")
            JitsiMeetActivity.launch(this, options)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to join conference", e)
            hideLoading()
            showError("회의 참가 실패: ${e.localizedMessage}")
        }
    }

    private fun isValidRoomName(roomName: String): Boolean {
        return roomName.matches(Regex("^[a-zA-Z0-9-]+$"))
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.createButton.isEnabled = false
        binding.joinButton.isEnabled = false
        binding.roomNameEditText.isEnabled = false
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.createButton.isEnabled = true
        binding.joinButton.isEnabled = true
        binding.roomNameEditText.isEnabled = true
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        try {
            if (isConferenceJoined) {
                Log.d(TAG, "Leaving conference...")
            }
            LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(broadcastReceiver)
        } catch (e: Exception) {
            Log.e(TAG, "Error during cleanup", e)
        }
        super.onDestroy()
    }
}