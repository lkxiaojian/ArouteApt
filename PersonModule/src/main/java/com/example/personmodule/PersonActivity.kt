package com.example.personmodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.library.RecordPathManager
import kotlinx.android.synthetic.main.activity_person.*

class PersonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)
        bt_main.setOnClickListener {
            val targetClass = RecordPathManager.getTargetClass("main", "main/main")
            startActivity(Intent(this,targetClass))
        }
        bt_order.setOnClickListener {
            val targetClass = RecordPathManager.getTargetClass("order", "order/order")
            startActivity(Intent(this,targetClass))
        }
    }
}