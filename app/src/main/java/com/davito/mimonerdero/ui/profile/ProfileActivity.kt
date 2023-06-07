package com.davito.mimonerdero.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.davito.mimonerdero.databinding.ActivityProfileBinding
import com.davito.mimonerdero.ui.home.HomeActivity
import com.davito.mimonerdero.ui.login.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        viewModel.loadUserInfo()

        viewModel.errorMsg.observe(this) { errorMsg ->
            Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        viewModel.userLoaded.observe(this) {user ->
            with(binding){
                nameTextView.text = user?.name
                emailTextView.text = user?.email
                jobTextView.text = user?.job
                phoneTextView.text = user?.phoneNumber
            }
        }

        viewModel.isSignOut.observe(this){
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }

        binding.signOutButtonTextView.setOnClickListener {
            viewModel.signOut()
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPressed()
            }
        })

        setContentView(binding.root)
    }

    fun backPressed() {
        startActivity(Intent(this@ProfileActivity, HomeActivity::class.java))
        finish()
    }


}