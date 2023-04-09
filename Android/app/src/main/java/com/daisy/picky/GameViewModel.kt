package com.daisy.picky

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.log

class GameViewModel() : ViewModel() {

    private val logtag = "ViewModel"

    private var gameMode: Int = -1
    private lateinit var cardPack: MutableList<Card>

    // 깔려있는 카드
    private var boardCardList = mutableListOf<Card>()
    private val _boardCard = MutableLiveData<List<Card>>()
    val boardCard: LiveData<List<Card>> get() = _boardCard

    // 선택된 카드
    private var selectedCardList = mutableListOf<Int>()
    private val _selectedCard = MutableLiveData<List<Int>>()
    val selectedCard: LiveData<List<Int>> get() = _selectedCard

    // 정답 개수
    private var _cntAnswer = MutableLiveData<Int>()
    val cntAnswer: LiveData<Int> get() = _cntAnswer


    private var endGameFlaginViewModel: Boolean = false
    private val _endGameFlag = MutableLiveData<Boolean>()
    val endGameFlag: LiveData<Boolean> get() = _endGameFlag


    private var matchedCardList = mutableListOf<Card>()
    private val _matchedCard = MutableLiveData<List<Card>>()
    val matchCard: LiveData<List<Card>> get() = _matchedCard

    fun setGame(gm :Int, shuffledCardPack: MutableList<Card>){
        gameMode = gm

        cardPack = shuffledCardPack

        for (i in 0..11){
            boardCardList.add(cardPack.get(0))
            cardPack.removeAt(0)
        }

        _boardCard.value = boardCardList
        _boardCard.postValue(_boardCard.value)

        _selectedCard.value = selectedCardList
        _selectedCard.postValue(selectedCardList)

        _matchedCard.value = matchedCardList
        _matchedCard.postValue(_matchedCard.value)

        _cntAnswer.value = 0
        _cntAnswer.postValue(_cntAnswer.value)

    }

    // 카드가 선택되었을 떄,
    public fun setSelected(isIncluded :Boolean, value: Int):Boolean{
        // 이미 선택되어 있는 경우, 선택 해제
        if (isIncluded) {
            selectedCardList.remove(value)
            _selectedCard.value = selectedCardList
            _selectedCard.postValue(selectedCardList)
            return true
        }
        else { // 카드 선택 후, 3개 선택 상태이면 set인지 확인, 3개 선택 해제
            selectedCardList.add(value)

            _selectedCard.value = selectedCardList
            _selectedCard.postValue(selectedCardList)

            if (selectedCardList.size == 3) { // 3개 선택됨
                if (checkSET()) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        selectedCardList = mutableListOf<Int>()
                        _selectedCard.value = selectedCardList
                        _selectedCard.postValue(selectedCardList)
                    }, 10)
                    return true
                } else{
                    Handler(Looper.getMainLooper()).postDelayed({
                        selectedCardList = mutableListOf<Int>()
                        _selectedCard.value = selectedCardList
                        _selectedCard.postValue(selectedCardList)
                    }, 20)
                    return false
                }
            } else { // 3개 이하
                return true
            }
        }
    }

    //선택된 카드 리스트 반환
    public fun getSelectedCard():List<Int>{
        // 선택된 카드 리스트
        return selectedCardList
    }

    // 선택된 카드 3장이 set인지 확인 후, 세트이면 점수 1점 올리고 true, 아니면 false 반환
    public fun checkSET(): Boolean{
        val firstCard = boardCardList.get(selectedCardList.get(0))
        val secondCard = boardCardList.get(selectedCardList.get(1))
        val thirdCard = boardCardList.get(selectedCardList.get(2))

        if (isSETonThree(firstCard, secondCard, thirdCard)){
            // set
            _cntAnswer.value = _cntAnswer.value?.plus(1)
            _cntAnswer.postValue(_cntAnswer.value)
            replaceNewCard()

            return true
        }
        else{
            _cntAnswer.value = _cntAnswer.value?.minus(0)
            _cntAnswer.postValue(_cntAnswer.value)


            return false
        }
    }

    // 파라미터의 세장이 세트이면 true 반환, 세트아니면 false 반환
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

    // set 맞출 경우 불려져서, 선택된 카드의 인덱스 자리의 카드 교체
    public fun replaceNewCard(){
        if (cardPack.size < 3){
            setEndGameFlag(true)
        }
        else {
            selectedCardList.sort()

            for (i in 0..selectedCardList.lastIndex) {
                var curIndex = selectedCardList.get(i)
                Log.d(logtag, curIndex.toString())

                matchedCardList.add(boardCardList.get(curIndex))
            }

            for (i in 0..selectedCardList.lastIndex){
                var curIndex = selectedCardList.get(i)

                boardCardList[curIndex] = cardPack.get(cardPack.lastIndex)
                cardPack.removeAt(cardPack.lastIndex)
            }

            _matchedCard.value = matchedCardList
            _matchedCard.postValue(_matchedCard.value)

            _boardCard.value = boardCardList
            _boardCard.postValue(_boardCard.value)
        }

    }

    // 현재 보드의 set 갯수 반환
    public fun checkAllSet():Int{

        val sets = mutableSetOf<Set<Int>>()
        var cntSet = 0

        for (i in 0..boardCardList.lastIndex-2){
            for (j in 1..boardCardList.lastIndex-1){
                if (i == j){
                    continue
                }
                for (k in 2..boardCardList.lastIndex){
                    if (i == k || j == k){
                        continue
                    }
                    if (isSETonThree(boardCardList.get(i), boardCardList.get(j), boardCardList.get(k))){
                        cntSet += 1
                        sets.add(setOf(i+1, j+1, k+1))
                    }
                }
            }
        }

        Log.d(logtag, sets.size.toString() + " " + sets.toString())
        return sets.size
    }

    // 보드에 set가 될 카드가 없을 경우, 불려짐
    // 보드의 카드를 cardPack index 0 ~ 11 로 교체
    public fun addNewCard(){
        if (cardPack.size < 3){
            setEndGameFlag(true)
        }

        cardPack.addAll(boardCardList)
        cardPack = cardPack.shuffled().toMutableList()
        boardCardList = mutableListOf<Card>()

        for (i in 0..11){
            boardCardList.add(cardPack.get(i))
        }
        for (i in 0..11){
            cardPack.removeAt(0)
        }

        _boardCard.value = boardCardList
        _boardCard.postValue(_boardCard.value)
    }

    // 게임 종료 flag 설정
    public fun setEndGameFlag(value: Boolean){
        endGameFlaginViewModel = value
        _endGameFlag.value = endGameFlaginViewModel
        _endGameFlag.postValue(_endGameFlag.value)
    }
}