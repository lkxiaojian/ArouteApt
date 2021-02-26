package com.example.aroute

import com.example.library.BaseApplication
import com.example.library.RecordPathManager
import com.example.ordermodule.OrderActivity
import com.example.personmodule.PersonActivity

class MyApplication:BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        RecordPathManager.joinGroup("order","order/order",OrderActivity::class.java)
        RecordPathManager.joinGroup("person","person/person",PersonActivity::class.java)
        RecordPathManager.joinGroup("main","main/main",PersonActivity::class.java)
    }
}