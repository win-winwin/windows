package com.example.windowsss

import android.Manifest

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class MicrophoneActivity : AppCompatActivity() {

    private lateinit var recordBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var playBtn: Button
    private lateinit var recordedInfo: TextView
    private lateinit var playLayout: LinearLayout

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var audioFile: File

    private val REQUEST_MIC_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_microphone)

        recordBtn = findViewById(R.id.btnRecord)
        stopBtn = findViewById(R.id.btnStop)
        playBtn = findViewById(R.id.btnplay)
        recordedInfo = findViewById(R.id.txtRecordedInfo)
        playLayout = findViewById(R.id.playLayout)

        playLayout.visibility = View.GONE  // Hide play section initially

        recordBtn.setOnClickListener {
            if (checkMicPermission()) {
                startRecording()
            } else {
                requestMicPermission()
            }
        }

        stopBtn.setOnClickListener {
            stopRecording()
        }

        playBtn.setOnClickListener {
            playRecording()
        }

        stopBtn.isEnabled = false
    }

    private fun checkMicPermission(): Boolean {
        val micPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        return micPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestMicPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_MIC_PERMISSION
        )
    }

    private fun startRecording() {
        audioFile = File(getExternalFilesDir(null), "audio_record.3gp")
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioFile.absolutePath)
            prepare()
            start()
        }
        Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show()
        recordBtn.isEnabled = false
        stopBtn.isEnabled = true
        playLayout.visibility = View.GONE
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null

        Toast.makeText(this, "Recording saved", Toast.LENGTH_SHORT).show()
        recordBtn.isEnabled = true
        stopBtn.isEnabled = false

        // Show play layout
        playLayout.visibility = View.VISIBLE
        recordedInfo.text = "Saved at: ${audioFile.absolutePath}"
    }

    private fun playRecording() {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioFile.absolutePath)
            prepare()
            start()
        }

        Toast.makeText(this, "Playing recording...", Toast.LENGTH_SHORT).show()

        mediaPlayer?.setOnCompletionListener {
            Toast.makeText(this, "Playback finished", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_MIC_PERMISSION && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startRecording()
        } else {
            Toast.makeText(this, "Microphone permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaRecorder?.release()
        mediaPlayer?.release()
    }
}
