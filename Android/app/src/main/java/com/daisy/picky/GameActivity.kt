package com.daisy.picky

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.daisy.picky.databinding.ActivityGameBinding
import kotlin.math.log

class GameActivity : BaseActivity() {

    val logTag = "GameActivity"

    private lateinit var binding: ActivityGameBinding

    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        gameViewModel.setGame(gameMode, cardPack.shuffled().toMutableList())


        gameViewModel.cntAnswer.observe(this){
            binding.txtCount.text = it.toString() + " SET" + gameViewModel.checkAllSet().toString()
            Log.d(logTag, "cntAnswer"+it.toString())
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}