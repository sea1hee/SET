package com.daisy.picky

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.daisy.picky.databinding.FragmentBoardBinding

class BoardFragment : Fragment(), OnCardClick {

    private val logtag = "BoardFragment"

    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter:CardAdapter
    private lateinit var gameViewModel: GameViewModel


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
        gameViewModel.printBoardCardLog(logtag)

        adapter = CardAdapter(this)
        adapter.boardCard = gameViewModel.boardCard.value!!
        adapter.selectedCard = gameViewModel.selectedCard.value!!
        binding.rcCards.adapter = adapter
        binding.rcCards.layoutManager = GridLayoutManager(context, 4)

        gameViewModel.boardCard.observe(viewLifecycleOwner) {
            adapter.boardCard = gameViewModel.boardCard.value!!
        }

        gameViewModel.selectedCard.observe(viewLifecycleOwner) {
            adapter.selectedCard = gameViewModel.selectedCard.value!!
            adapter.notifyDataSetChanged()
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }

    override fun selected(index: Int) {
        //Log.d(logtag, gameViewModel.selectedCard.value?.get(0).toString())
        gameViewModel.setCntSelected(gameViewModel.getSelectedCard().contains(index), index)
    }
}