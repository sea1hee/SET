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

            binding.txtExist.text = gameViewModel.checkAllSet().toString() + " Sets available"
            Log.d(logTag, "cntAnswer"+it.toString())
        }

        binding.btnBack.setOnClickListener {
            val exitDialog = ExitDialog(this,this)
            exitDialog.show()
        }

        binding.btnSets.setOnClickListener {
            btnBackVisivility(View.GONE)
            binding.containerFound.visibility = View.VISIBLE
        }
        binding.btnReload.setOnClickListener {
            gameViewModel.addNewCard()
        }
    }

    public fun btnBackVisivility(value : Int) {
        binding.btnBack.visibility = value
        super.onPostResume()
    }

    override fun onBackPressed() {
        val exitDialog = ExitDialog(this,this)
        exitDialog.show()
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