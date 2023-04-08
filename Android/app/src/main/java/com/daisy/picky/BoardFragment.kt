package com.daisy.picky

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        adapter = CardAdapter(this, requireContext())
        adapter.boardCard = gameViewModel.boardCard.value!!
        adapter.selectedCard = gameViewModel.selectedCard.value!!
        binding.rcCards.adapter = adapter
        binding.rcCards.layoutManager = GridLayoutManager(context, 4)

        gameViewModel.boardCard.observe(viewLifecycleOwner) {
            binding.rcCards.layoutManager = GridLayoutManager(context, 4)
            adapter.boardCard = gameViewModel.boardCard.value!!

            // set 없을 경우, Toast 팝업 후 새로고침
            if (gameViewModel.checkAllSet() == 0){
                Toast.makeText(context, "세트가 없어 새로고침합니다.",Toast.LENGTH_LONG).show()
                gameViewModel.addNewCard()
            }


        }

        gameViewModel.selectedCard.observe(viewLifecycleOwner) {
            adapter.selectedCard = gameViewModel.selectedCard.value!!
            adapter.notifyDataSetChanged()
        }

        gameViewModel.endGameFlag.observe(viewLifecycleOwner){
            if (it){
                activity?.finish()
            }
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }

    // 카드 없을 때 3장 추가하는 기능\
    // index번째 카드 선택 이벤트 발생 시
    override fun selected(index: Int) {
        //Log.d(logtag, gameViewModel.selectedCard.value?.get(0).toString())
        val selected = gameViewModel.selectedCard.value

        // 3개 선택 후 틀린 경우
        if (gameViewModel.setSelected(gameViewModel.getSelectedCard().contains(index), index) == false) {
            Log.d("1animation", "boardFragment")
            Log.d("1animation", selected!!.get(0).toString())
            Log.d("1animation", selected!!.get(1).toString())
            Log.d("1animation", selected!!.get(2).toString())
            adapter.notifyItemRangeChanged(0,12, "anim")
            Log.d("1animation", selected!!.size.toString())
        }
        else{
            adapter.notifyDataSetChanged()
        }
    }
}