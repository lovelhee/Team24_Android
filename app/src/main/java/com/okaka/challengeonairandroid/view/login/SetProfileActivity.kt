package com.okaka.challengeonairandroid.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ActivitySetProfileBinding
import com.okaka.challengeonairandroid.viewmodel.SetProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetProfileActivity : AppCompatActivity() {
    private val setProfileViewModel: SetProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val setProfileBinding: ActivitySetProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_page_profile)
        setProfileBinding.setProfileData = setProfileViewModel
        setProfileBinding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(setProfileBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, SetProfileActivity::class.java)
        }
    }
}