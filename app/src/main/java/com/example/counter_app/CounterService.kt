package com.example.counter_app

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast

class CounterService : Service() {
    private val binder = CounterBinder()
    private var counter = 0

    inner class CounterBinder : Binder() {
        fun getService(): CounterService = this@CounterService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun increment() {
        counter++
    }

    fun decrement() {
        counter--
    }

    fun presentValue(context: Context) {
        Toast.makeText(context, "$counter", Toast.LENGTH_SHORT).show()
    }

    fun getCounter(): Int {
        return counter
    }
}
