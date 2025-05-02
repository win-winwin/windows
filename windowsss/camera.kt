package com.example.windowsss

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class camera : AppCompatActivity() {
    private lateinit var ivv:ImageView
    private lateinit var btn: Button
    private val camera_code=100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_camera)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ivv=findViewById(R.id.iv)
        btn=findViewById(R.id.oc)
        btn.setOnClickListener {
            val cc = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cc, camera_code)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==camera_code || resultCode== Activity.RESULT_OK) {
            val phot = data?.extras?.get("data") as? Bitmap
            phot?.let {
                ivv.setImageBitmap(it)
            }
        }
    }
}