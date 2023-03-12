package com.daisy.picky

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.daisy.picky.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {

    private val logtag = "BoardFragment"

    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!

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


        binding.txtCard0.setText(gameViewModel.getBoardCard(0).count.toString()+"/"+gameViewModel.getBoardCard(0).color.toString()+"/"+gameViewModel.getBoardCard(0).shape.toString()+"/"+gameViewModel.getBoardCard(0).pattern.toString())
        binding.txtCard1.setText(gameViewModel.getBoardCard(1).count.toString()+"/"+gameViewModel.getBoardCard(1).color.toString()+"/"+gameViewModel.getBoardCard(1).shape.toString()+"/"+gameViewModel.getBoardCard(1).pattern.toString())
        binding.txtCard2.setText(gameViewModel.getBoardCard(2).count.toString()+"/"+gameViewModel.getBoardCard(2).color.toString()+"/"+gameViewModel.getBoardCard(2).shape.toString()+"/"+gameViewModel.getBoardCard(2).pattern.toString())
        binding.txtCard3.setText(gameViewModel.getBoardCard(3).count.toString()+"/"+gameViewModel.getBoardCard(3).color.toString()+"/"+gameViewModel.getBoardCard(3).shape.toString()+"/"+gameViewModel.getBoardCard(3).pattern.toString())
        binding.txtCard4.setText(gameViewModel.getBoardCard(4).count.toString()+"/"+gameViewModel.getBoardCard(4).color.toString()+"/"+gameViewModel.getBoardCard(4).shape.toString()+"/"+gameViewModel.getBoardCard(4).pattern.toString())
        binding.txtCard5.setText(gameViewModel.getBoardCard(5).count.toString()+"/"+gameViewModel.getBoardCard(5).color.toString()+"/"+gameViewModel.getBoardCard(5).shape.toString()+"/"+gameViewModel.getBoardCard(5).pattern.toString())
        binding.txtCard6.setText(gameViewModel.getBoardCard(6).count.toString()+"/"+gameViewModel.getBoardCard(6).color.toString()+"/"+gameViewModel.getBoardCard(6).shape.toString()+"/"+gameViewModel.getBoardCard(6).pattern.toString())
        binding.txtCard7.setText(gameViewModel.getBoardCard(7).count.toString()+"/"+gameViewModel.getBoardCard(7).color.toString()+"/"+gameViewModel.getBoardCard(7).shape.toString()+"/"+gameViewModel.getBoardCard(7).pattern.toString())
        binding.txtCard8.setText(gameViewModel.getBoardCard(8).count.toString()+"/"+gameViewModel.getBoardCard(8).color.toString()+"/"+gameViewModel.getBoardCard(8).shape.toString()+"/"+gameViewModel.getBoardCard(8).pattern.toString())
        binding.txtCard9.setText(gameViewModel.getBoardCard(9).count.toString()+"/"+gameViewModel.getBoardCard(9).color.toString()+"/"+gameViewModel.getBoardCard(9).shape.toString()+"/"+gameViewModel.getBoardCard(9).pattern.toString())
        binding.txtCard10.setText(gameViewModel.getBoardCard(10).count.toString()+"/"+gameViewModel.getBoardCard(10).color.toString()+"/"+gameViewModel.getBoardCard(10).shape.toString()+"/"+gameViewModel.getBoardCard(10).pattern.toString())
        binding.txtCard11.setText(gameViewModel.getBoardCard(11).count.toString()+"/"+gameViewModel.getBoardCard(11).color.toString()+"/"+gameViewModel.getBoardCard(11).shape.toString()+"/"+gameViewModel.getBoardCard(11).pattern.toString())

        binding.card0.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(0)){
                gameViewModel.setCntSelected(-1, 0)
                binding.card0.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 0)
                binding.card0.useCompatPadding = true
            }
        }
        binding.card1.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(1)){
                gameViewModel.setCntSelected(-1, 1)
                binding.card1.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 1)
                binding.card1.useCompatPadding = true
            }
        }
        binding.card2.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(2)){
                gameViewModel.setCntSelected(-1, 2)
                binding.card2.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 2)
                binding.card2.useCompatPadding = true
            }
        }
        binding.card3.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(3)){
                gameViewModel.setCntSelected(-1, 3)
                binding.card3.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 3)
                binding.card3.useCompatPadding = true
            }
        }
        binding.card4.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(4)){
                gameViewModel.setCntSelected(-1, 4)
                binding.card4.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 4)
                binding.card4.useCompatPadding = true
            }
        }
        binding.card5.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(5)){
                gameViewModel.setCntSelected(-1, 5)
                binding.card5.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 5)
                binding.card5.useCompatPadding = true
            }
        }
        binding.card6.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(6)){
                gameViewModel.setCntSelected(-1, 6)
                binding.card6.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 6)
                binding.card6.useCompatPadding = true
            }
        }
        binding.card7.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(7)){
                gameViewModel.setCntSelected(-1, 7)
                binding.card7.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 7)
                binding.card7.useCompatPadding = true
            }
        }
        binding.card8.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(8)){
                gameViewModel.setCntSelected(-1, 8)
                binding.card8.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 8)
                binding.card8.useCompatPadding = true
            }
        }
        binding.card9.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(9)){
                gameViewModel.setCntSelected(-1, 9)
                binding.card9.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 9)
                binding.card9.useCompatPadding = true
            }
        }
        binding.card10.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(10)){
                gameViewModel.setCntSelected(-1, 10)
                binding.card10.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 10)
                binding.card10.useCompatPadding = true
            }
        }
        binding.card11.setOnClickListener {
            if (gameViewModel.getSelectedCard().contains(11)){
                gameViewModel.setCntSelected(-1, 11)
                binding.card11.useCompatPadding = false
            }
            else{
                gameViewModel.setCntSelected(1, 11)
                binding.card11.useCompatPadding = true
            }
        }



        return view

    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }
}