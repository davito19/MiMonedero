package com.davito.mimonerdero.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davito.mimonerdero.R
import com.davito.mimonerdero.databinding.ActivityLoginBinding
import com.davito.mimonerdero.ui.HomeActivity
import com.davito.mimonerdero.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(2000)
        setTheme(R.style.Theme_MiMonedero)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding){

            logInButonTextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            }

            signUpButtonTextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }
        }
    }
}