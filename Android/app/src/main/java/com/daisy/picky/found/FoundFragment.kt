package com.daisy.picky.found

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.daisy.picky.BaseActivity
import com.daisy.picky.R
import com.daisy.picky.game.GameActivity
import com.daisy.picky.game.GameViewModel
import com.daisy.picky.game.OnCardClick
import com.daisy.picky.databinding.FragmentFoundBinding
import com.daisy.picky.game.Card


class FoundFragment : Fragment(), OnCardClick {

    private val logtag = "FoundFragment"

    private var _binding: FragmentFoundBinding? = null
    private val binding get() = _binding!!

    private lateinit var gameViewModel: GameViewModel
    private lateinit var adapter: FoundCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoundBinding.inflate(inflater, container, false)
        val view = binding.root

        gameViewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]

        adapter = FoundCardAdapter(requireContext())
        adapter.foundCard = listOf<List<Card>>()
        //adapter.foundCard = gameViewModel.matchCard.value!!
        binding.rcFoundsets.adapter = adapter
        binding.rcFoundsets.layoutManager = GridLayoutManager(context, 1)

        if (BaseActivity.gameMode == BaseActivity.NORMAL_MODE){
            binding.txtModeName.text = getString(R.string.game_mode_name_1)
        }else if (BaseActivity.gameMode == BaseActivity.ONE_MINUTE_MODE){
            binding.txtModeName.text = getString(R.string.game_mode_name_2)
        }


        gameViewModel.matchCard.observe(viewLifecycleOwner) {

            if(it.isEmpty()){
                //empty
                binding.imgFound1.visibility = View.VISIBLE
                binding.imgFound2.visibility = View.VISIBLE
                binding.imgFound3.visibility = View.VISIBLE
                binding.txtNotSupported.visibility = View.VISIBLE
            }
            else {
                // 1 or 2
                binding.imgFound1.visibility = View.GONE
                binding.imgFound2.visibility = View.GONE
                binding.imgFound3.visibility = View.GONE
                binding.txtNotSupported.visibility = View.GONE
            }

            adapter.foundCard = it!!
            Log.d(logtag, adapter.foundCard.size.toString())
            adapter.notifyDataSetChanged()
        }


        binding.btnExit.setOnClickListener{
            (activity as GameActivity).setContainerFound(View.GONE)
            (activity as GameActivity).btnVisivility(View.VISIBLE)
        }

        return view
    }

    override fun selected(index: Int) {
    }

}