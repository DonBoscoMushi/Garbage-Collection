package com.team.green.utils

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.team.green.R

class Connectivity : AppCompatActivity() {

    val layoutConnected = findViewById<LinearLayout>(R.id.connected)
    val layoutDisconnected = findViewById<LinearLayout>(R.id.disconnected)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connectivity)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->

            if (isConnected) {
                layoutDisconnected.visibility = View.GONE
                layoutConnected.visibility = View.VISIBLE
            } else {
                layoutConnected.visibility = View.GONE
                layoutDisconnected.visibility = View.VISIBLE
            }
        })
    }
}