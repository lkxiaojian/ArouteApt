package com.example.aroute

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.annotation.ARouterTmp
import com.example.ordermodule.OrderActivity
import kotlinx.android.synthetic.main.activity_main.*
@ARouterTmp(path = "main/MainActivity")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_main_order.setOnClickListener {
            startActivity(Intent(this,OrderActivity::class.java))
        }
    }
}