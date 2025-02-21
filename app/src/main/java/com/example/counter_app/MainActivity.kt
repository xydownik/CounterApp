package com.example.counter_app

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var counterService: CounterService? = null
    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as CounterService.CounterBinder
            counterService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIncrement: Button = findViewById(R.id.btnIncrement)
        val btnDecrement: Button = findViewById(R.id.btnDecrement)
        val btnShowValue: Button = findViewById(R.id.btnShowValue)

        btnIncrement.setOnClickListener {
            counterService?.increment()
        }

        btnDecrement.setOnClickListener {
            counterService?.decrement()
        }

        btnShowValue.setOnClickListener {
            counterService?.presentValue(this)
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, CounterService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }
}
