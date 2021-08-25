package com.curiousdev.newsapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.curiousdev.newsapp.R
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch{
            //wait 2 seconds then go to home activity
            delay(2000)
            val toHomeIntent = Intent(this@SplashActivity,HomeActivity::class.java)
            startActivity(toHomeIntent)
            this.cancel()
            finish()
        }
    }
}