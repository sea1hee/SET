package com.daisy.picky

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.daisy.picky.databinding.FragmentBoardBinding
import com.daisy.picky.databinding.FragmentLoadingBinding
import com.daisy.picky.game.GameActivity
import com.daisy.picky.game.GameViewModel
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit


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

        binding.prgrLoading.max = 100


        gameViewModel.progress.observe(viewLifecycleOwner){
            if(it == 0) {
                makeLoading()
            }
        }
        /*
        gameViewModel.progress.observe(viewLifecycleOwner) {
            /*
            Log.d("loading", it.toString())
            binding.prgrLoading.progress = it
            if(it == 100){
                (activity as GameActivity).setContainerLoading(View.INVISIBLE)
            }*/


            val currentProgress = binding.prgrLoading.progress
            getInterval().subscribe {
                binding.prgrLoading.progress = currentProgress + it.toInt()
            }
            if (gameViewModel.progress.value == 100){
                (activity as GameActivity).setContainerLoading(View.INVISIBLE)
            }

/*
            CoroutineScope(Main).launch {
                val anim = async(Main) {
                    animateProgressBar(binding.prgrLoading.progress.toFloat(), it.toFloat()) }
                if (anim.await() != null) {
                    (activity as GameActivity).setContainerLoading(View.INVISIBLE)
                }
            }
 */


        }
*/



        return view
    }

    fun animateProgressBar(f: Float, t: Float) {
        val anim = AnimateProgressBar(binding.prgrLoading, f, t)
        anim.duration = 1
        binding.prgrLoading.startAnimation(anim)

    }

    public fun makeLoading(){

        for(i: Int in 10..100 step(10)){
            binding.prgrLoading.progress = i.toInt()
            sleep(100)
        }

        gameViewModel.setProgress(100)

        /*
        binding.prgrLoading.progress = 0
        var prg = 10
        while (prg <= 100) {
            Log.d("loading", prg.toString())
            binding.prgrLoading.progress = prg
            prg += 10
            Thread.sleep(1000)

        }

        (activity as GameActivity).setContainerLoading(View.INVISIBLE)
         */
    }

    private fun getInterval(): Observable<Long> =
        Observable.interval(1L, TimeUnit.MILLISECONDS).map { interval ->
            interval + 1
        }.take(100)

}