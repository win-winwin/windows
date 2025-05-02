package com.example.windowsss

import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.emptyLongSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class audvid : AppCompatActivity() {
    private lateinit var vidview:VideoView
    private lateinit var img: ImageView
    private lateinit var play:Button
    private lateinit var stop: Button
    private lateinit var mediaplayer:MediaPlayer
    private lateinit var pvv:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audvid)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //audio
        mediaplayer=MediaPlayer.create(this,R.raw.conclude)
        play=findViewById(R.id.plb)
        stop=findViewById(R.id.stb)
        play.setOnClickListener {
            if(!mediaplayer.isPlaying)
            {
                mediaplayer.start()
            }
        }
        stop.setOnClickListener {
            if(mediaplayer.isPlaying)
            {
                mediaplayer.pause()
                mediaplayer.seekTo(0)
            }
        }
        pvv=findViewById(R.id.pv)
        pvv.setOnClickListener {
            //videoview
            vidview=findViewById(R.id.vv)
            val uri= Uri.parse("android.resource://"+packageName+"/"+R.raw.objectives)
            vidview.setVideoURI(uri)
            val mediacontroller=MediaController(this)
            mediacontroller.setAnchorView(vidview)
            vidview.setMediaController(mediacontroller)
            vidview.start()
        }

        //animate
        img=findViewById(R.id.iv)
        img.setBackgroundResource(R.drawable.animation_list)

    }
    //for audio
    override fun onDestroy() {
        super.onDestroy()
        if(this::mediaplayer.isInitialized)
        {
            mediaplayer.release()
        }
    }
    //animate
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val sm=img.background as?AnimationDrawable
        sm?. let{
            if(hasFocus)
            {
                it.start()
            }
            else{
                it.stop()
            }
        }

    }
}