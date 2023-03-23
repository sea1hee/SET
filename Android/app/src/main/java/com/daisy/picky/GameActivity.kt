package com.daisy.picky

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.daisy.picky.databinding.ActivityGameBinding
import com.daisy.picky.found.FoundFragment

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


        setFoundSetsOFF()

        gameViewModel.cntAnswer.observe(this){
            binding.txtCount.text = it.toString() + " SET"

            binding.txtExist.text = gameViewModel.checkAllSet().toString() + " exists"
            Log.d(logTag, "cntAnswer"+it.toString())
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSets.setOnClickListener {
            binding.containerFound.visibility = View.VISIBLE

        }
    }

    public fun setFoundSetsOFF(){
        binding.containerFound.visibility = View.GONE
    }
}