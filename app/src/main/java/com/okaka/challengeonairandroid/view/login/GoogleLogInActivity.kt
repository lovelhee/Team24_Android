package com.okaka.challengeonairandroid.view.login

import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ActivityGoogleLoginBinding
import com.okaka.challengeonairandroid.viewmodel.GoogleLogInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoogleLogInActivity : AppCompatActivity() {

    private val viewModel: GoogleLogInViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityGoogleLoginBinding

    companion object {
        private const val TAG = "GoogleLogInActivity"
    }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            // 성공
            Log.d(TAG, "ID: ${account.id}")
            Log.d(TAG, "Email: ${account.email}")
            Log.d(TAG, "ID Token: ${account.idToken}")
        } catch (e: ApiException) {
            // 실패
            Log.e(TAG, "Sign in failed code: ${e.statusCode}")
            Log.e(TAG, "Sign in failed message: ${e.message}")
            Log.e(TAG, "Sign in failed status: ${e.status}")
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleLoginBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        setupGoogleSignIn()
        setupClickListener()
        observeViewModel()
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.server_client_id))
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Check if user is already signed in
        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let {
            Log.d(TAG, "User already signed in")
            Log.d(TAG, "Previous ID Token: ${it.idToken}")
        }
    }

    private fun setupClickListener() {
        binding.btnGoogleSignIn.setOnClickListener {
            Log.d(TAG, "Google Sign In button clicked")
            viewModel.setLoading(true)
            googleSignInLauncher.launch(googleSignInClient.signInIntent)
        }
    }

    private fun observeViewModel() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is GoogleLogInViewModel.LoginState.Success -> {
                    Log.d(TAG, "Login successful")
                    Log.d(TAG, "User email: ${state.email}")
                    Log.d(TAG, "Access token: ${state.token}")
                    // TODO: Navigate to main screen
                }
                is GoogleLogInViewModel.LoginState.Error -> {
                    Log.e(TAG, "Login failed: ${state.message}")
                }
                is GoogleLogInViewModel.LoginState.Loading -> {
                    Log.d(TAG, "Login in progress")
                }
            }
        }
    }
}