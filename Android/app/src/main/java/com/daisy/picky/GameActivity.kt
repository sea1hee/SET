package com.daisy.picky

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.daisy.picky.databinding.ActivityGameBinding
import com.daisy.picky.found.FoundFragment

class GameActivity : BaseActivity(), CustomDialogInterface {

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
            binding.txtCount.text = it.toString() + " Set"
        }

        gameViewModel.cntAvailable.observe(this){
            binding.txtExist.text = it.toString() + " Sets available"
        }

        binding.btnBack.setOnClickListener {
            val exitDialog = ExitDialog(this,this)
            exitDialog.show()
        }

        binding.btnSets.setOnClickListener {
            btnVisivility(View.GONE)
            binding.containerFound.visibility = View.VISIBLE
        }
        binding.btnReload.setOnClickListener {
            gameViewModel.addNewCard()
        }
    }

    public fun btnVisivility(value : Int) {
        binding.btnBack.visibility = value
        binding.btnReload.visibility = value
        binding.btnSets.visibility = value
    }

    override fun onBackPressed() {
        if(binding.containerFound.visibility == View.VISIBLE) {
            binding.containerFound.visibility = View.GONE
        }
        else {
            val exitDialog = ExitDialog(this, this)
            exitDialog.show()
        }
    }

    public fun setFoundSetsOFF(){
        binding.containerFound.visibility = View.GONE
    }

    override fun onExitButtonClicked() {
        finish()
    }

    override fun onStayButtonClicked() {
    }
}