package com.example.windowsss

import android.content.Intent
import android.media.audiofx.BassBoost
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class wifi : AppCompatActivity() {
    private lateinit var b1:Button
    private lateinit var b2:Button
    private lateinit var wifimanager:WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wifi)
        wifimanager=applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        b1 = findViewById(R.id.on)
        b2 = findViewById(R.id.off)
        b1.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                startActivity(intent)
            } else if (!wifimanager.isWifiEnabled) {
                wifimanager.isWifiEnabled = true
                Toast.makeText(this, "wifi is enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "wifi is already enabled", Toast.LENGTH_SHORT).show()
            }
        }
        b2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                startActivity(intent)
            } else if (wifimanager.isWifiEnabled) {
                wifimanager.isWifiEnabled = false
                Toast.makeText(this, "wifi is disabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "wifi is already disabled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
