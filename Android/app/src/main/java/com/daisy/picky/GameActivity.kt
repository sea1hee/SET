package com.daisy.picky

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.daisy.picky.databinding.ActivityGameBinding
import com.daisy.picky.found.FoundFragment

class GameActivity : BaseActivity(), CustomDialogInterface {

    val logTag = "GameActivity"

    private lateinit var binding: ActivityGameBinding

    private lateinit var gameViewModel: GameViewModel


    private var cntAnswer = 0
    private var cntAvailable = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        gameViewModel.setGame(gameMode, cardPack.shuffled().toMutableList())

        setFoundSetsOFF()

        gameViewModel.cntAnswer.observe(this){
            cntAnswer = it
            binding.txtCount.text = it.toString() + " Set"
            if(cntAnswer == 23){
                binding.btnReload.visibility=View.INVISIBLE
            }
        }

        gameViewModel.cntAvailable.observe(this){
            cntAvailable = it
            binding.txtExist.text = it.toString() + " Sets available"
        }

        gameViewModel.endGameFlag.observe(this){
            if (it){ // 더이상 추가할 카드가 없을 때
                finish()
            }
            else{
                if (cntAnswer == 23 && cntAvailable == 0){ // 남은 카드가 없는데 Set가 없을 때(섞는게 불가능할 때)
                    finish()
                } else{
                    Log.d("viewmodel", "no remain")
                    Toast.makeText(this,"남은 카드가 없습니다.", Toast.LENGTH_LONG).show()
                }
            }
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
            if (gameViewModel.addNewCard()){
                Toast.makeText(this, "화면을 새로고침합니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    public fun btnVisivility(value : Int) {
        binding.btnBack.visibility = value
        binding.btnReload.visibility = value
        binding.btnSets.visibility = value

        if(cntAnswer == 23){
            binding.btnReload.visibility = View.INVISIBLE
        }
    }

    override fun onBackPressed() {
        if(binding.containerFound.visibility == View.VISIBLE) {
            binding.containerFound.visibility = View.GONE
            btnVisivility(View.VISIBLE)
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