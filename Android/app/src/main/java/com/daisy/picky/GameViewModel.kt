package com.daisy.picky

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel() : ViewModel() {

    private val logtag = "ViewModel"

    private var gameMode: Int = -1
    private lateinit var cardPack: MutableList<Card>

    // 깔려있는 카드
    private val boardCardList = mutableListOf<Card>()
    private val _boardCard = MutableLiveData<List<Card>>()
    val boardCard: LiveData<List<Card>> get() = _boardCard

    // 전체 카드(cardPack) 중 어디까지 썼는지
    private var cardIndex: Int = 12

    // 선택된 카드
    private var selectedCardList = mutableListOf<Int>()
    private val _selectedCard = MutableLiveData<List<Int>>()
    val selectedCard: LiveData<List<Int>> get() = _selectedCard

    // 정답 개수
    private var _cntAnswer = MutableLiveData<Int>()
    val cntAnswer: LiveData<Int> get() = _cntAnswer


    fun setGame(gm :Int, shuffledCardPack: MutableList<Card>){
        gameMode = gm

        cardPack = shuffledCardPack

        for (i in 0..11){
            boardCardList.add(shuffledCardPack.get(i))
        }

        _boardCard.value = boardCardList
        _boardCard.postValue(_boardCard.value)

        _selectedCard.value = selectedCardList
        _selectedCard.postValue(selectedCardList)

        _cntAnswer.value = 0
        _cntAnswer.postValue(_cntAnswer.value)

        cardIndex = 12

        printBoardCardLog(logtag)

    }


    public fun setCntSelected(isIncluded :Boolean, value: Int):Boolean{

        if (isIncluded) {
            selectedCardList.remove(value)
            _selectedCard.value = selectedCardList
            _selectedCard.postValue(selectedCardList)
            return false
        }
        else {
            selectedCardList.add(value)
            if (selectedCardList.size == 3) { // 3개 선택됨
                checkSET()
                selectedCardList = mutableListOf<Int>()
                _selectedCard.value = selectedCardList
                _selectedCard.postValue(selectedCardList)
                return true
            } else { // 3개 이하
                _selectedCard.value = selectedCardList
                _selectedCard.postValue(selectedCardList)
                return false
            }
        }
    }


    public fun getSelectedCard():List<Int>{
        // 선택된 카드 리스트
        return selectedCardList
    }


    public fun getBoardCard(ind :Int):Card{
        //보드 내 카드 중 ind 인덱스 값
        return boardCardList.get(ind)
    }

    public fun printBoardCardLog(tag: String){
        // 보드 정보 Log 기록
        for (i in 0..11){
            Log.d(tag, boardCardList.get(i).count.toString()+"/"+boardCardList.get(i).color.toString()+"/"+boardCardList.get(i).shape.toString()+"/"+boardCardList.get(i).pattern.toString())
        }
    }

    public fun printSelectedCardLog(tag: String){
        // 보드 정보 Log 기록
        for (i in 0..selectedCardList.lastIndex){
            Log.d(tag, selectedCardList.get(i).toString())
        }

        Log.d(tag, "end")
    }

    public fun checkSET(){
        val firstCard = boardCardList.get(selectedCardList.get(0))
        val secondCard = boardCardList.get(selectedCardList.get(1))
        val thirdCard = boardCardList.get(selectedCardList.get(2))

        if (isSETonThree(firstCard, secondCard, thirdCard)){
            // set
            _cntAnswer.value = _cntAnswer.value?.plus(1)
            _cntAnswer.postValue(_cntAnswer.value)
            replaceNewCard()
        }
        else{
            _cntAnswer.value = _cntAnswer.value?.minus(1)
            _cntAnswer.postValue(_cntAnswer.value)
        }
    }

    public fun isSETonThree(f: Card, s: Card, t: Card):Boolean {
        if ((f.color == s.color) and (s.color != t.color)){
            return false
        }else if ((f.color != s.color) and (s.color == t.color)){
            return false
        }else if ((f.color == t.color) and (t.color != s.color)){
            return false
        }

        if ((f.count == s.count) and (s.count != t.count)){
            return false
        }else if ((f.count != s.count) and (s.count == t.count)){
            return false
        }else if ((f.count == t.count) and (t.count != s.count)){
            return false
        }

        if ((f.shape == s.shape) and (s.shape != t.shape)){
            return false
        }else if ((f.shape != s.shape) and (s.shape == t.shape)){
            return false
        }else if ((f.shape == t.shape) and (t.shape != s.shape)){
            return false
        }

        if ((f.pattern == s.pattern) and (s.pattern != t.pattern)){
            return false
        }else if ((f.pattern != s.pattern) and (s.pattern == t.pattern)){
            return false
        }else if ((f.pattern == t.pattern) and (t.pattern != s.pattern)){
            return false
        }

        return true
    }

    public fun replaceNewCard(){
        for (i in 0..selectedCardList.lastIndex){
            val curIndex = selectedCardList.get(i)

            boardCardList.removeAt(curIndex)
            boardCardList.add(curIndex, cardPack.get(cardIndex))
            cardIndex += 1
        }

        _boardCard.value = boardCardList
        _boardCard.postValue(_boardCard.value)
    }



}