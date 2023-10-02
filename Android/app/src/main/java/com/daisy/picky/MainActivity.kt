package com.daisy.picky

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import com.daisy.picky.databinding.ActivityMainBinding
import kotlin.random.Random
import android.animation.ObjectAnimator.ofFloat
import android.content.Context
import com.kakao.sdk.user.UserApiClient

private const val SNOWING_MESSAGE_ID = 10

class MainActivity : BaseActivity(), Handler.Callback  {
    val logTag = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private var isSnowing: Boolean = true
    private val delayedSnowing: Handler = Handler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnMode0.setOnClickListener{
            Log.d(logTag, "select Multi mode btn")
            gameMode = 0
            preventGame()
            //startGame()
        }
        binding.btnMode1.setOnClickListener{
            Log.d(logTag, "select Beginner mode btn")
            gameMode = 1
            startGame()
        }
        binding.btnMode2.setOnClickListener {
            Log.d(logTag, "select Expert mode btn")
            gameMode = 2
            preventGame()
            //startGame()
        }
        binding.btnMode3.setOnClickListener {
            Log.d(logTag, "select My Rank mode btn")
            gameMode = 3
            preventGame()
            //startGame()
        }
        binding.btnMode4.setOnClickListener{
            Log.d(logTag, "select Encyclopedia mode btn")
            gameMode = 4
            preventGame()
            //startGame()
        }
        binding.btnMode5.setOnClickListener {
            Log.d(logTag, "select Tutorial mode btn")
            gameMode = 5
            preventGame()
            //startGame()
        }
        binding.btnMode6.setOnClickListener {
            prefs = this.getSharedPreferences("login", Context.MODE_PRIVATE)

            if(preLoginMethod == GOOGLE_LOGIN){

            }
            else if(preLoginMethod == KAKAO_LOGIN) {
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        Log.e(logTag, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    } else {
                        Log.i(logTag, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    }
                }
            }
            else if(preLoginMethod == NAVER_LOGIN){

            }

            preLoginMethod = EVER_LOGIN
            preLoginId = ""
            prefs.edit().putInt("method", EVER_LOGIN).apply()
            prefs.edit().putString("token", "").apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnMode0.visibility = View.GONE
        binding.btnMode3.visibility = View.GONE
        binding.btnMode4.visibility = View.GONE

        delayedSnowing.sendEmptyMessageDelayed(SNOWING_MESSAGE_ID, 100)
    }

    private fun startGame(){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun preventGame(){
        var modeName:String = ""
        if(gameMode == 0){
            modeName = "Multi mode"
        }else if(gameMode == 1){
            modeName = "Beginner mode"
        }else if(gameMode == 2){
            modeName = "Expert mode"
        }else if(gameMode == 3){
            modeName = "My Rank"
        }else if(gameMode == 4){
            modeName = "Encyclopedia"
        }else if(gameMode == 5){
            modeName = "Tutorial"
        }else if(gameMode == 6){
            modeName = "Logout"
        }
        Toast.makeText(this, modeName+"는 준비중입니다.", Toast.LENGTH_SHORT).show()
    }

    private fun snowing(snowObj : AppCompatImageView) {
        binding.layoutMain.addView(snowObj)

        val startPointX = Random.nextFloat() * binding.layoutMain.width - (0.5 * binding.layoutMain.width).toFloat()
        val endPointX = Random.nextFloat() * binding.layoutMain.width
        val moverX = ofFloat(snowObj, View.TRANSLATION_X, startPointX, endPointX)

        Log.d("viewmodel",startPointX.toString())

        val snowHeight = snowObj.measuredHeight * snowObj.scaleY
        val startPointY = -snowHeight
        val endPointY = (binding.layoutMain.height + snowHeight)
        val moverY = ofFloat(snowObj, View.TRANSLATION_Y, startPointY, endPointY).apply {
            interpolator = AccelerateInterpolator(0.5f)
        }

        val set = AnimatorSet().apply {
            playTogether(moverX, moverY)
            duration = (Math.random() * 3000 + 3000).toLong()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.layoutMain.removeView(snowObj)
                }
            })
        }
        set.start()
    }

    private fun makeSnowObject() = AppCompatImageView(this).apply {
        setImageResource(R.drawable.pattern_2_1_2)
        scaleX = Math.random().toFloat() * 0.3f + .2f
        scaleY = scaleX
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    }

    private fun makeSnowObject2() = AppCompatImageView(this).apply {
        setImageResource(R.drawable.pattern_3_1_1)
        scaleX = Math.random().toFloat() * 0.3f + .2f
        scaleY = scaleX
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    }
    private fun makeSnowObject3() = AppCompatImageView(this).apply {
        setImageResource(R.drawable.pattern_2_3_3)
        scaleX = Math.random().toFloat() * 0.3f + .2f
        scaleY = scaleX
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    }

    override fun handleMessage(msg: Message): Boolean {
        if (msg.what == SNOWING_MESSAGE_ID && isSnowing) {
            snowing(makeSnowObject())
            snowing(makeSnowObject2())
            snowing(makeSnowObject3())
            delayedSnowing.sendEmptyMessageDelayed(
                SNOWING_MESSAGE_ID,
                (Random.nextFloat() * 1 + 500).toLong()
            )
        }

        return true
    }
}