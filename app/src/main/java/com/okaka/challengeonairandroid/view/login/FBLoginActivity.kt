package com.okaka.challengeonairandroid.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ActivityFbloginBinding
import com.okaka.challengeonairandroid.view.challenge.ChallengeOngoingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FBLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFbloginBinding

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google 로그인 실패: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFbloginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase 초기화 추가
        FirebaseApp.initializeApp(this)

        // Google 로그인 설정
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 기존 로그인 상태 확인
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // 이미 로그인된 상태
            startActivity(Intent(this, ChallengeOngoingActivity::class.java))
            finish()
        }

        binding.btnGoogleSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 로그인 성공
                    val user = auth.currentUser
                    Toast.makeText(this, "로그인 성공: ${user?.email}", Toast.LENGTH_SHORT).show()

                    // ChallengeOngoingActivity로 이동하면서 사용자 정보 전달
                    val intent = Intent(this, ChallengeOngoingActivity::class.java).apply {
                        putExtra("user_token", idToken)
                        putExtra("user_email", user?.email)
                        putExtra("example_user_name", user?.displayName)
                        putExtra("user_id", user?.uid)
                        putExtra("user_photo", user?.photoUrl?.toString())
                    }

                    startActivity(intent)
                    finish()
                } else {
                    // 로그인 실패
                    Toast.makeText(this, "Firebase 인증 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }
}