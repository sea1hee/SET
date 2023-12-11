package com.daisy.picky

import android.content.ComponentName
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.daisy.picky.game.Card


open class BaseActivity: AppCompatActivity() {

    companion object{

        lateinit var prefs: SharedPreferences

        //setTutorial true:첫 설치 , false:메뉴로진입
        var isTutorial :Boolean = true
        //isMute true:music off, false: music on
        var turnOnMusic: Boolean = true

        val EVER_LOGIN = 0
        val GOOGLE_LOGIN = 1
        val KAKAO_LOGIN = 2
        val NAVER_LOGIN = 3

        lateinit var preLoginId: String
        var preLoginMethod: Int = EVER_LOGIN

        var gameMode = 0
        val NORMAL_MODE = 1
        val ONE_MINUTE_MODE = 2

        private var dimen = 3

        private val CARD_COLOR = listOf("red", "blue", "green")
        private val CARD_COUNT = listOf(1, 2, 3)
        private val CARD_SHAPE = listOf("cir", "rect", "wave")
        private val CARD_PATTERN = listOf("fill", "empty", "stripe")

        var cardPack :MutableList<Card> = mutableListOf<Card>()


        var musicService: MusicService? = null
        var isBound = false
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as MusicService.MusicBinder
                musicService = binder.getService()
                isBound = true
                if(turnOnMusic) {
                    startMusic()
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                isBound = false
                stopMusic()
            }
        }

        fun startMusic() {
            musicService?.startMusic()
        }

        fun stopMusic() {
            musicService?.stopMusic()
        }

        init {
        }

    }


}