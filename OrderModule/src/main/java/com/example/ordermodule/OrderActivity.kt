package com.example.ordermodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.library.RecordPathManager
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        bt_order_main.setOnClickListener {
            val targetClass = RecordPathManager.getTargetClass("main", "main/main")
            startActivity(Intent(this,targetClass))
        }
        bt_person.setOnClickListener {
            val targetClass = RecordPathManager.getTargetClass("person", "person/person")
            startActivity(Intent(this,targetClass))
        }
    }
}