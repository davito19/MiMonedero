package com.davito.mimonerdero.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.davito.mimonerdero.databinding.ActivityLoginBinding
import com.davito.mimonerdero.ui.home.HomeActivity
import com.davito.mimonerdero.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var isSessionActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.validateSessionActive()

        viewModel.isSessionActive.observe(this){active ->
            this.isSessionActive = active
        }

        viewModel.errorMsg.observe(this) { errorMsg ->
            Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        viewModel.isSuccessSignIn.observe(this) {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        }

        setContentView(binding.root)

        with(binding){

            logInButonTextView.setOnClickListener {
                viewModel.validateLogin(
                    emailInputEditText.text.toString(),
                    passwordIpnutEditText.text.toString()
                )
            }

            signUpButtonTextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }
        }
    }
}