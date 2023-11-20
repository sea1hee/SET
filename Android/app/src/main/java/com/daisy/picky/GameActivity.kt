package com.daisy.picky

import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.daisy.picky.databinding.ActivityGameBinding
import com.daisy.picky.found.FoundFragment

class GameActivity : BaseActivity() {

    val logTag = "GameActivity"

    private lateinit var binding: ActivityGameBinding

    private lateinit var gameViewModel: GameViewModel


    private var cntAnswer = 0
    private var cntAvailable = 0
    private var point = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        gameViewModel.setGame(gameMode)

        setFoundSetsOFF()

        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        // add를 통해 container에 Fragment 추가
        fragmentTransaction.add(R.id.fragment_container_board, BoardFragment())
        fragmentTransaction.setReorderingAllowed(true)
        // commit을 통해 transaction 등록
        fragmentTransaction.commit()

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


        gameViewModel.point.observe(this){
            point = it
            if(point < 0){
                binding.txtPoint.setTextColor(Color.RED);
            } else {
                binding.txtPoint.setTextColor(Color.WHITE);
            }
            binding.txtPoint.text = it.toString() + " Points"

        }

        gameViewModel.endGameFlag.observe(this){
            Log.d("theif", "call observer start")

            val prepareDialog = PrepareDialog(gameViewModel.getPoint(), this, object: CustomDialogInterface {
                override fun onExitButtonClicked(){
                    finish()
                }
                //again
                override fun onStayButtonClicked() {
                    finish()
                }

            })
            prepareDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            prepareDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            Log.d("theif", "call observer")
            prepareDialog.show()
        }

        binding.btnBack.setOnClickListener {
            val exitDialog = ExitDialog(this, object: CustomDialogInterface{
                override fun onExitButtonClicked() {
                    finish()
                }

                override fun onStayButtonClicked() {
                }
            })
            exitDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
            val exitDialog = ExitDialog(this, object: CustomDialogInterface{
                override fun onExitButtonClicked() {
                    finish()
                }

                override fun onStayButtonClicked() {
                }
            })
            exitDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            exitDialog.show()
        }
    }

    public fun setFoundSetsOFF(){
        binding.containerFound.visibility = View.GONE
    }
}