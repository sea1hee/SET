package com.daisy.picky

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.Binder
import android.util.Log

class MusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("musicLog", "Service onBind")
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("musicLog", "Service onCreate")
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.picky)
        mediaPlayer.isLooping = true
    }

    fun startMusic() {
        Log.d("musicLog", "Service start Music")
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun stopMusic() {
        Log.d("musicLog", "Service stop Music")
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
