package com.daisy.picky.found

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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

        gameViewModel.matchCard.observe(viewLifecycleOwner) {
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