package com.daisy.picky

import android.R.attr.level
import android.R.attr.start
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.animation.addListener
import androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.daisy.picky.databinding.FragmentLoadingBinding
import com.daisy.picky.game.GameViewModel


class LoadingFragment : Fragment() {


    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameViewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        val view = binding.root

        gameViewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]

        val rotation_animation = AnimationUtils.loadAnimation(context, R.anim.loading_rotation)
        val rotation_animation_reverse = AnimationUtils.loadAnimation(context, R.anim.loading_rotation_reverse)
        binding.card1.startAnimation(rotation_animation)
        binding.card2.startAnimation(rotation_animation_reverse)
        binding.card3.startAnimation(rotation_animation)
        binding.card4.startAnimation(rotation_animation_reverse)
        binding.card5.startAnimation(rotation_animation)
        binding.card6.startAnimation(rotation_animation_reverse)

        binding.prgrLoading.max = 100

        gameViewModel.progress.observe(viewLifecycleOwner){
            if(it == 0) {
                makeLoading()
            }
        }
        return view
    }

    public fun makeLoading(){

        Log.d("loading", "starAnim")

        ObjectAnimator.ofInt(binding.prgrLoading, "progress", 100).apply {
            duration = 2000
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    Log.d("loading", "execute end listener")
                    gameViewModel.setProgress(100)
                }
            })
            start()
        }
    }
}