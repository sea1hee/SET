package com.daisy.picky

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val logtag = "ViewModel"

    private var gameMode: Int = -1
    private lateinit var cardPack: MutableList<Card>
    private lateinit var boardCard: MutableList<Card>
    private var cardIndex: Int = 12

    private var cntSelected: Int = 0
    private var selectedCard: MutableList<Int> = mutableListOf<Int>()

    fun setGame(gm :Int, shuffledCardPack: MutableList<Card>){
        gameMode = gm
        cardPack = shuffledCardPack
        boardCard = mutableListOf<Card>()

        for (i in 0..11){
            boardCard.add(cardPack.get(i))
        }


        printBoardCardLog(logtag)
    }

    public fun setCntSelected(value :Int, index: Int):Boolean?{
        cntSelected += value

        if (value == -1) {
            selectedCard.remove(index)
            return false
        }
        else {
            if (cntSelected == 3) {
                return true
            } else {
                selectedCard.add(index)
                return false
            }
        }
    }


    public fun getSelectedCard():MutableList<Int>{
        return selectedCard
    }

    public fun getBoardCard(ind :Int):Card{
        return boardCard.get(ind)
    }

    public fun printBoardCardLog(tag: String){
        for (i in 0..11){
            Log.d(tag, boardCard.get(i).count.toString()+"/"+boardCard.get(i).color.toString()+"/"+boardCard.get(i).shape.toString()+"/"+boardCard.get(i).pattern.toString())
        }
    }

    public fun checkSET():Boolean{
        if (cntSelected != 3){
            return false
        }
        //TODO
        return true
    }



}