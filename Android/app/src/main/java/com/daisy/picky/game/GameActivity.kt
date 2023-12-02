package com.daisy.picky.game

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.daisy.picky.BaseActivity
import com.daisy.picky.LoadingFragment
import com.daisy.picky.R
import com.daisy.picky.databinding.ActivityGameBinding
import com.daisy.picky.dialog.CustomDialogInterface
import com.daisy.picky.dialog.ExitDialog
import com.daisy.picky.dialog.FinishDialog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class GameActivity : BaseActivity() {

    val logTag = "GameActivity"

    private lateinit var binding: ActivityGameBinding

    private lateinit var gameViewModel: GameViewModel


    private var cntAnswer = 0
    private var cntAvailable = 0
    private var point = 0

    lateinit var loadingFragment: LoadingFragment

    var subject: PublishSubject<Int> = PublishSubject.create()
    val mCompositeDisposable = CompositeDisposable()

    private lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        setContainerLoading(View.VISIBLE)
        gameViewModel.setLoading()

        fragmentTransaction = supportFragmentManager.beginTransaction()
        // add를 통해 container에 Fragment 추가
        fragmentTransaction.add(R.id.fragment_container_board, BoardFragment())
        fragmentTransaction.setReorderingAllowed(true)
        // commit을 통해 transaction 등록
        fragmentTransaction.commit()

        setContainerFound(View.GONE)

        gameViewModel.setGame(gameMode)

        gameViewModel.progress.observe(this){
            if(it == 100) {
                setContainerLoading(View.GONE)
            }
        }

        //Log.d("loading", gameViewModel.boardCard.value.toString())

        gameViewModel.cntAnswer.observe(this){

            Log.d("loading", "GameActivity: observe cntAnswer")
            cntAnswer = it
            if (it <= 1) {
                binding.txtCount.text = it.toString() +" "+ resources.getString(R.string.set)
            } else {
                binding.txtCount.text = it.toString() +" "+ resources.getString(R.string.sets)
            }
            if(cntAnswer == 23){
                binding.btnReload.visibility=View.INVISIBLE
            }
        }

        gameViewModel.cntAvailable.observe(this){

            Log.d("loading", "GameActivity: observe cntAvailabe")
            cntAvailable = it
            if (it <= 1) {
                binding.txtExist.text = it.toString() +" "+ resources.getString(R.string.set_available)
            } else {
                binding.txtExist.text = it.toString() +" "+ resources.getString(R.string.sets_available)
            }
        }


        gameViewModel.point.observe(this){

            Log.d("loading", "GameActivity: observe point")
            point = it
            if(point < 0){
                //binding.txtPoint.setTextColor(Color.RED);
            } else {
                //binding.txtPoint.setTextColor(Color.WHITE);
            }
            //binding.txtPoint.text = it.toString() + " Points"

        }

        gameViewModel.endGameFlag.observe(this){
            Log.d("loading", "GameActivity: observe endGameFlag")
            Log.d("theif", "call observer start")

            val prepareDialog = FinishDialog(gameViewModel.getPoint(), this, object: CustomDialogInterface {
                override fun onLeftButtonClicked(){
                    finish()
                }
                override fun onRightButtonClicked() {
                    btnVisivility(View.VISIBLE)
                    gameViewModel.setGame(gameMode)
                }
            })
            prepareDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            prepareDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            Log.d("theif", "call observer")
            prepareDialog.show()
        }

        binding.btnBack.setOnClickListener {
            val exitDialog = ExitDialog(this, object: CustomDialogInterface{
                override fun onLeftButtonClicked() {
                    finish()
                }

                override fun onRightButtonClicked() {
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

        val disposable = subject.throttleFirst(3, TimeUnit.SECONDS)
            .subscribe{
                binding.btnReload.callOnClick()
            }
        mCompositeDisposable.add(disposable)


        binding.btnReload.setOnClickListener {
            if (gameViewModel.addNewCard()){
                Toast.makeText(this, resources.getString(R.string.redirect), Toast.LENGTH_LONG).show()
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
                override fun onLeftButtonClicked() {
                    finish()
                }

                override fun onRightButtonClicked() {
                }
            })
            exitDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            exitDialog.show()
        }
    }

    public fun setContainerFound(visibility: Int){
        binding.containerFound.visibility = visibility
    }

    public fun setContainerLoading(visibility: Int){
        binding.containerLoading.visibility = visibility
    }

}