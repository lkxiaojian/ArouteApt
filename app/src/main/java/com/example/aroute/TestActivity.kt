package com.example.aroute

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.annotation.ARouter

@ARouter("/test/testActivity")
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}