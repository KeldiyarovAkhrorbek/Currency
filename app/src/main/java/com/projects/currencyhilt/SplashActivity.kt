package com.projects.currencyhilt

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.projects.currencyhilt.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler().postDelayed({
            YoYo.with(Techniques.TakingOff).duration(1000)
                .withListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                        super.onAnimationEnd(animation, isReverse)
                        binding.logo.visibility = View.GONE
                    }
                }).playOn(binding.logo)
        }, 1000)

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}