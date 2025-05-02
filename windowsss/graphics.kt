package com.example.windowsss

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class graphics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dv = DemoView(this)
        setContentView(dv)

    }

    private inner class DemoView(context: Context) :
        androidx.appcompat.widget.AppCompatImageView(context) {
        @SuppressLint("DrawAllocation")
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            val p = Paint()
            p.style = Paint.Style.FILL
            p.color = Color.WHITE
            canvas.drawPaint(p)

            p.isAntiAlias = false
            p.color = Color.BLUE
            canvas.drawCircle(100f, 400f, 50f, p)

            p.color = Color.BLACK
            p.textSize = 50f
            canvas.save()
            canvas.rotate(105f, 100f, 400f)
            canvas.drawText("Princy", 100f, 400f, p)
            canvas.restore()
        }
    }
}

