package com.example.windowsss
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Bluetooth : AppCompatActivity() {
    private val bluetoothadapter:BluetoothAdapter?=BluetoothAdapter.getDefaultAdapter()
    private lateinit var b1:Button
    private lateinit var b2:Button
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bluetooth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
            requestPermissions(arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT),1)
        }
        b1=findViewById(R.id.off)
        b2=findViewById(R.id.on)
        b1.setOnClickListener {
            if(bluetoothadapter==null)
            {
                Toast.makeText(this,"Bluetooth is not available",Toast.LENGTH_SHORT).show()
            }
            else if(!bluetoothadapter.isEnabled)
            {
                val intent= Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivity(intent)
                Toast.makeText(this,"Bluetooth is enabled",Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this,"Bluetooth is already enabled",Toast.LENGTH_SHORT).show()
            }
        }
        b2.setOnClickListener {
                val intent=Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS)
                startActivity(intent)
        }
    }
}