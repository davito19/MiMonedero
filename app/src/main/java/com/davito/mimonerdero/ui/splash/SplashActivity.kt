package com.davito.mimonerdero.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.davito.mimonerdero.databinding.ActivitySplashBinding
import com.davito.mimonerdero.ui.home.HomeActivity
import com.davito.mimonerdero.ui.login.LoginActivity
import java.util.*
import kotlin.concurrent.timerTask

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var biding: ActivitySplashBinding
    private lateinit var splashViewModel: SplashViewModel
    private var isSessionActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = ActivitySplashBinding.inflate(layoutInflater)
        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        val view = biding.root
        setContentView(view)

        splashViewModel.validateSessionActive()

        splashViewModel.isSessionActive.observe(this) { active ->
            this.isSessionActive = active
        }

        val timer = Timer()
        timer.schedule(
            timerTask {
                if (!isSessionActive) {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 2000
        )
    }
}