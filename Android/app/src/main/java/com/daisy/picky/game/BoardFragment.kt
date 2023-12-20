package com.daisy.picky.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.daisy.picky.R
import com.daisy.picky.Rotate3dAnimation
import com.daisy.picky.databinding.FragmentBoardBinding
import com.daisy.picky.game.CardAdapter.OnItemClickEventListener
import com.daisy.picky.game.GameViewModel
import com.google.android.material.card.MaterialCardView


class BoardFragment : Fragment(){

    private val logtag = "BoardFragment"

    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CardAdapter
    private lateinit var gameViewModel: GameViewModel

    private lateinit var mContainer :ViewGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        val view = binding.root

        gameViewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]

        adapter = CardAdapter(requireContext())
        adapter.boardCard = listOf<Card>()
        //adapter.boardCard = gameViewModel.boardCard.value!!
        adapter.selectedCard = listOf<Int>()

        //adapter.selectedCard = gameViewModel.selectedCard.value!!
        adapter.setOnItemClickListener(object : OnItemClickEventListener {
            override fun onItemClick(a_view: ViewGroup, view: MaterialCardView, index: Int) {
                mContainer = a_view
                //applyRotation(index,0.0f, 180f)

                val anim: Animation = ScaleAnimation(
                    1.0f, 0.9f,  // Start and end values for the X axis scaling
                    1.0f, 0.9f,  // Start and end values for the Y axis scaling
                    Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot point of X scaling
                    Animation.RELATIVE_TO_SELF, 0.5f
                ).apply {
                    fillAfter = false
                    duration = 200
                    interpolator = AccelerateInterpolator()
                    setAnimationListener(DisplayNextView((index)))
                }
                mContainer.startAnimation(anim)
            }
        })

        binding.rcCards.itemAnimator = null
        binding.rcCards.adapter = adapter
        binding.rcCards.layoutManager = GridLayoutManager(context, 4)


        gameViewModel.boardCard.observe(viewLifecycleOwner) {
            binding.rcCards.layoutManager = GridLayoutManager(context, 4)
            adapter.boardCard = gameViewModel.boardCard.value!!
            // set 없을 경우, Toast 팝업 후 새로고침
            if (gameViewModel.checkAllSet() == 0) {
                if (gameViewModel.addNewCard() == true) {
                    Toast.makeText(activity, resources.getString(R.string.redirect), Toast.LENGTH_LONG).show()
                }
            }
        }

        gameViewModel.selectedCard.observe(viewLifecycleOwner) {
            Log.d("animTest", it.toString())
            adapter.selectedCard = gameViewModel.selectedCard.value!!
            adapter.notifyDataSetChanged()
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }

/*
    // 카드 없을 때 3장 추가하는 기능\
    // index번째 카드 선택 이벤트 발생 시
    override fun selected(index: Int) {
        //Log.d(logtag, gameViewModel.selectedCard.value?.get(0).toString())
        val selected = gameViewModel.selectedCard.value

        // 3개 선택 후 틀린 경우
        if (gameViewModel.setSelected(gameViewModel.getSelectedCard().contains(index), index) == false) {
            adapter.notifyDataSetChanged()
        }
        else{
            adapter.notifyDataSetChanged()
        }
    }*/

    private fun applyRotation(position:Int, start: Float, end: Float) {
        val centerX = mContainer.width / 2.0f
        val centerY = mContainer.height / 2.0f

        val rotation = Rotate3dAnimation(start, end, centerX, centerY, 85.0f, false)
        rotation.duration = 200
        rotation.fillAfter = false
        rotation.interpolator = AccelerateInterpolator()
        rotation.setAnimationListener(DisplayNextView(position))
        mContainer.startAnimation(rotation)
    }

    inner class DisplayNextView(private val index: Int) :
        AnimationListener {
        override fun onAnimationStart(animation: Animation) {
        }
        override fun onAnimationEnd(animation: Animation) {
            if (gameViewModel.setSelected(gameViewModel.getSelectedCard().contains(index), index) == false) { // 맞은 경우
            }
            else{ // 틀리거나 3개가 아닌 경우
            }
        }

        override fun onAnimationRepeat(animation: Animation) {}
    }
}