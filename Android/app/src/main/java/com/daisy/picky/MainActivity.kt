package com.daisy.picky

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.daisy.picky.databinding.ActivityMainBinding
import com.daisy.picky.dialog.ReadyDialog
import com.daisy.picky.game.GameActivity
import com.daisy.picky.tutorial.TutorialActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.random.Random


private const val SNOWING_MESSAGE_ID = 10

class MainActivity : BaseActivity(), Handler.Callback  {
    val logTag = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private var isSnowing: Boolean = true
    private val delayedSnowing: Handler = Handler(this)

    private lateinit var configuration: Configuration
    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        splashScreen = installSplashScreen()

        setContentView(binding.root)

        // 첫 설치 여부 확인
        prefs = this.getSharedPreferences("picky", MODE_PRIVATE)

        turnOnMusic = prefs.getBoolean("music", true)
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
        binding.tgMusic.isChecked = turnOnMusic

        isTutorial = prefs.getBoolean("tutorial", true)
        Log.d("tutorialTest", "install, tutorial on?"+ isTutorial.toString())
        if(isTutorial) {
            startTutorial()
        }

        bindAnimation()

        var subject: PublishSubject<Int> = PublishSubject.create()
        val mCompositeDisposable = CompositeDisposable()

        val disposable = subject.throttleFirst(3, TimeUnit.SECONDS)
            .subscribe{
                binding.btnMode1.callOnClick()
            }
        mCompositeDisposable.add(disposable)

/*
        binding.btnMode0.setOnClickListener{
            Log.d(logTag, "select Multi mode btn")
            gameMode = 0
            readyGame()
            //startGame()
        }
 */
        binding.tgMusic.setOnCheckedChangeListener { it, isChecked ->

            turnOnMusic = isChecked
            prefs.edit().putBoolean("music", turnOnMusic).apply()

            if (isChecked) {
                startMusic()
            } else {
                stopMusic()
            }
        }
        binding.btnMode1.setOnClickListener{
            Log.d(logTag, "select Normal mode btn")
            gameMode = NORMAL_MODE
            startGame()
        }
        binding.btnMode2.setOnClickListener {
            Log.d(logTag, "select 1-Minute mode btn")
            gameMode = ONE_MINUTE_MODE
            startGame()
        }
        binding.btnMode3.setOnClickListener {
            startTutorial()
        }
        /*
        binding.btnMode3.setOnClickListener {
            Log.d(logTag, "select My Rank mode btn")
            gameMode = 3
            readyGame()
            //startGame()
        }
        binding.btnMode4.setOnClickListener{
            Log.d(logTag, "select Encyclopedia mode btn")
            gameMode = 4
            readyGame()
            //startGame()
        }
        binding.btnMode5.setOnClickListener {
            Log.d(logTag, "select Tutorial mode btn")
            gameMode = 5
            //startGame()
            startTutorial()
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
                NaverIdLoginSDK.logout()
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
        binding.btnMode6.visibility = View.GONE

        delayedSnowing.sendEmptyMessageDelayed(SNOWING_MESSAGE_ID, 100)
        */
    }

    override fun onStart() {
        super.onStart()
        Log.d("musicLog", "main onStart")
        startMusic()
    }

    override fun onStop() {
        super.onStop()
        Log.d("musicLog", "main onStop")
        stopMusic()/*
        if (isBound) {
            unbindService(connection)
            isBound = false
        }*/
    }

    private fun startTutorial() {
        Log.d("tutorialTest", "startTutorialActivity")
        val intent = Intent(this, TutorialActivity::class.java)
        startActivity(intent)
    }

    private fun startGame(){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun snowing(snowObj : AppCompatImageView) {

        /*
        //binding.layoutMain.addView(snowObj)

        val startPointX = Random.nextFloat() * binding.layoutMain.width - (0.5 * binding.layoutMain.width).toFloat()
        val endPointX = Random.nextFloat() * binding.layoutMain.width
        val moverX = ofFloat(snowObj, View.TRANSLATION_X, startPointX, endPointX)

        //Log.d("viewmodel",startPointX.toString())

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
        */
        //set.start()
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

    fun readyGame(){
        val readyDialog = ReadyDialog(this)
        readyDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        readyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        readyDialog.show()
    }

    // splash의 애니메이션 설정
    private fun startSplash() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 5f, 1f)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 5f, 1f)

            ObjectAnimator.ofPropertyValuesHolder(splashScreenView.iconView, scaleX, scaleY).run {
                interpolator = AnticipateInterpolator()
                duration = 1000L
                doOnEnd {
                    splashScreenView.remove()
                }
                start()
            }
        }
    }

    fun bindAnimation(){
        var animTest: Animation = AnimationUtils.loadAnimation(this, R.anim.icon_home_1)
        var animTest2: Animation = AnimationUtils.loadAnimation(this, R.anim.icon_home_2)
        var animTest3: Animation = AnimationUtils.loadAnimation(this, R.anim.icon_home_3)
        //var animTest2: Animator = AnimatorInflater.loadAnimator(this, R.animator.icon_home_3)
        binding.imgHome1.animation = animTest
        binding.imgHome2.animation = animTest3
        binding.imgHome3.animation = animTest2
        //animTest2.apply {
        //    setTarget(binding.imgHome3)
        //    start()
        //}

/*
        var animation_1 = TranslateAnimation(0f, 30f, 0f, 30f)
        animation_1.setDuration(1000)
        animation_1.setRepeatCount(-1)
        animation_1.setRepeatMode(Animation.REVERSE)

        var animation_2 = TranslateAnimation(0f, 60f, 0f, 0f)
        animation_2.setDuration(1000)
        animation_2.setRepeatCount(-1)
        animation_2.setRepeatMode(Animation.REVERSE)


        var animation_3 = TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.RELATIVE_TO_PARENT, -1f, TranslateAnimation.RELATIVE_TO_PARENT, 1.0f)
        animation_3.setDuration(1000)

        animation_3.setFillAfter(true)
        animation_3.setRepeatCount(-1)
        animation_3.setRepeatMode(Animation.REVERSE)


        binding.imgHome1.animation = animation_1
        binding.imgHome2.animation = animation_3
        binding.imgHome3.animation = animation_2
 */
    }

}