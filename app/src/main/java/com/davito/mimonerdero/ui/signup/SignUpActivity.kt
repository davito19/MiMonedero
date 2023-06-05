package com.davito.mimonerdero.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.davito.mimonerdero.databinding.ActivitySignUpBinding
import com.davito.mimonerdero.ui.home.HomeActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        setContentView(binding.root)

        viewModel.errorMsg.observe(this) { errorMsg ->
            Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        viewModel.isSuccessSignUp.observe(this) {
            startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
            finish()
        }

        with(binding){

            signUpButtonTextView.setOnClickListener {
                viewModel.validateSignUp(
                    nameEditText.text.toString(),
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    replyPasswordEditText.text.toString(),
                    phoneEditText.text.toString(),
                    jobEditText.text.toString()
                )
            }

            cancelButtonTextView.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}