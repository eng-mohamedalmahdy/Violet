package com.dnteam.purble.ui.activities

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.dnteam.purble.R

class SplashActivity : AppCompatActivity() {

    private lateinit var animationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animationView = findViewById(R.id.flower_animation)
        animationView.addAnimatorUpdateListener(AnimatorUpdateListener { animation: ValueAnimator ->
            if (animation.isRunning) {
                val i = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(i)
                finish()
            }
        })
    }
}