package com.okaka.challengeonairandroid.view.challenge

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.AndroidEntryPoint
import org.jitsi.meet.sdk.BroadcastEvent
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.URL
import java.util.UUID
import com.okaka.challengeonairandroid.databinding.ActivityChallengeOngoingBinding

@AndroidEntryPoint
class ChallengeOngoingActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ChallengeOngoing"
        private const val SERVER_URL = "https://meet.jit.si"
    }

    private lateinit var binding: ActivityChallengeOngoingBinding
    private var isConferenceJoined = false

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { intent ->
                val event = BroadcastEvent(intent)
                Log.d(TAG, "Received broadcast event: ${event.type}")

                when (event.type) {
                    BroadcastEvent.Type.CONFERENCE_JOINED -> {
                        isConferenceJoined = true
                        Log.i(TAG, "Successfully joined conference")
                        hideLoading()
                    }

                    BroadcastEvent.Type.CONFERENCE_TERMINATED -> {
                        isConferenceJoined = false
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
                        Log.d(TAG, "Attempting to join conference...")
                    }

                    BroadcastEvent.Type.PARTICIPANT_JOINED -> {
                        Log.d(TAG, "New participant joined")
                    }

                    BroadcastEvent.Type.AUDIO_MUTED_CHANGED -> {
                        Log.d(TAG, "Audio mute status changed")
                    }

                    BroadcastEvent.Type.VIDEO_MUTED_CHANGED -> {
                        Log.d(TAG, "Video mute status changed")
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

        setupBroadcastReceiver()
        initializeJitsiMeet()
        setupUIListeners()
    }

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

    private fun initializeJitsiMeet() {
        try {
            val defaultOptions = JitsiMeetConferenceOptions.Builder()
                .setServerURL(URL(SERVER_URL))
                .setFeatureFlag("welcomepage.enabled", false)
                .setFeatureFlag("prejoinpage.enabled", false)
                .setFeatureFlag("call-integration.enabled", false)
                .build()

            JitsiMeet.setDefaultConferenceOptions(defaultOptions)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize JitsiMeet", e)
            showError("JitsiMeet 초기화 실패: ${e.localizedMessage}")
        }
    }

    private fun setupUIListeners() {
        // 방 만들기 버튼 리스너
        binding.createButton.setOnClickListener {
            val roomName = binding.roomNameEditText.text.toString().trim()
            if (roomName.isBlank()) {
                // 방 이름이 비어있으면 자동으로 생성
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

        // 기존 참여 버튼 리스너
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
        // UUID를 사용하여 고유한 방 이름 생성
        return "room-${UUID.randomUUID().toString().substring(0, 8)}"
    }

    private fun createAndJoinRoom(roomName: String) {
        try {
            showLoading()

            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(roomName)
                .setAudioMuted(false)
                .setVideoMuted(false)
                .setFeatureFlag("chat.enabled", true)
                .setFeatureFlag("invite.enabled", true)  // 초대 기능 활성화 (방장용)
                .setFeatureFlag("meeting-name.enabled", true)  // 방 이름 변경 활성화 (방장용)
                .setFeatureFlag("raise-hand.enabled", true)
                .setFeatureFlag("recording.enabled", true)  // 녹화 기능 활성화 (방장용)
                .setFeatureFlag("live-streaming.enabled", false)
                .setFeatureFlag("toolbox.enabled", true)
                .setFeatureFlag("filmstrip.enabled", true)
                .build()

            Log.d(TAG, "Creating and joining room: $roomName")
            JitsiMeetActivity.launch(this, options)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create and join conference", e)
            hideLoading()
            showError("회의방 생성 실패: ${e.localizedMessage}")
        }
    }

    private fun isValidRoomName(roomName: String): Boolean {
        return roomName.matches(Regex("^[a-zA-Z0-9-]+$"))
    }

    private fun joinConference(roomName: String) {
        try {
            showLoading()

            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(roomName)
                .setAudioMuted(false)
                .setVideoMuted(false)
                .setFeatureFlag("lobby.enabled", false) // 대기실 기능 비활성화
                .setFeatureFlag("chat.enabled", true)
                .setFeatureFlag("invite.enabled", false)
                .setFeatureFlag("meeting-name.enabled", false)
                .setFeatureFlag("raise-hand.enabled", true)
                .setFeatureFlag("recording.enabled", false)
                .setFeatureFlag("live-streaming.enabled", false)
                .setFeatureFlag("toolbox.enabled", true)
                .setFeatureFlag("filmstrip.enabled", true)
                .build()

            Log.d(TAG, "Joining room: $roomName")
            JitsiMeetActivity.launch(this, options)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to join conference", e)
            hideLoading()
            showError("회의 참가 실패: ${e.localizedMessage}")
        }
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