package com.example.dotsanimationdemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khalidtouch.loadinganimation.Circle
import com.khalidtouch.loadinganimation.LoadingAnimation

class MainActivity : AppCompatActivity() {
    private lateinit var loadingAnimation: LoadingAnimation
    private lateinit var circle: Circle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingAnimation = findViewById(R.id.loading);
        circle = findViewById(R.id.circle)
        loadingAnimation.setSize(20);
        loadingAnimation.setDotsCount(3);
        loadingAnimation.setDuration(100);
        loadingAnimation.setColor(Color.parseColor("#FF3700B3"));

    }
}