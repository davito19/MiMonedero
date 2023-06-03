package com.davito.mimonerdero.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davito.mimonerdero.R
import com.davito.mimonerdero.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(2000)
        setTheme(R.style.Theme_MiMonedero)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}