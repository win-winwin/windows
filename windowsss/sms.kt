package com.example.windowsss

import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class sms : AppCompatActivity() {
    private lateinit var e1:EditText
    private lateinit var e2:EditText
    private lateinit var btn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sms)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        e1=findViewById(R.id.phn)
        e2=findViewById(R.id.msg)
        btn=findViewById(R.id.bs)
        btn.setOnClickListener {
            sendmessage()
        }
    }
    private fun sendmessage(){
        val ph=e1.text.toString()
        val ms=e2.text.toString()
        val sms=SmsManager.getDefault()
        sms.sendTextMessage(ph,null,ms,null,null)
        Toast.makeText(this,"message sent",Toast.LENGTH_SHORT).show()
    }
}