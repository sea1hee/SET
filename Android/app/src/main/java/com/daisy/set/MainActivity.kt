package com.daisy.set

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.daisy.set.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    val logTag = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMode1.setOnClickListener{
            Log.d(logTag, "select single mode btn")
            gameMode = 1
            startGame()
        }
        binding.btnMode2.setOnClickListener {
            Log.d(logTag, "select multi mode btn")
            gameMode = 2
            preventGame()
            //startGame()
        }
        binding.btnMode3.setOnClickListener {
            Log.d(logTag, "select tutorial mode btn")
            gameMode = 3
            preventGame()
            //startGame()
        }
    }

    private fun startGame(){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun preventGame(){
        Toast.makeText(this, "준비중입니다.", Toast.LENGTH_LONG).show()
    }
}