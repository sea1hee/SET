package com.daisy.picky.game

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

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

    // 정답 가능 개수
    private var _cntAvailable = MutableLiveData<Int>()
    val cntAvailable: LiveData<Int> get() = _cntAvailable

    // 포인트 점수
    private var _point = MutableLiveData<Int>()
    val point: LiveData<Int> get() = _point


    private var _endGameFlag = MutableLiveData<Boolean>()
    val endGameFlag: LiveData<Boolean> get() = _endGameFlag


    private var matchedCardList = mutableListOf<List<Card>>()
    private val _matchedCard = MutableLiveData<List<List<Card>>>()
    val matchCard: LiveData<List<List<Card>>> get() = _matchedCard


    private var _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> get() = _progress

    init{
        //setProgress(0)
    }

    fun setProgress(p: Int){
        _progress.postValue(p)
    }

    fun setGame(gm :Int){
        //setProgress(30)
        //sleep(1000)

        Log.d("viewModel", "setGame")

        Log.d("loading", "GameActivity: setGame, progress 30")
        CoroutineScope(Main).launch() {
            //setProgress(50)
            val resultInitCard = async(IO) {initCardPack(gm)}

            if (resultInitCard.await() != null) {

                //setProgress(80)
                Log.d("loading", boardCardList.toString())
                Log.d("loading", "GameActivity: setProgress 75")

                _boardCard.value = boardCardList
                _boardCard.postValue(_boardCard.value)

                _selectedCard.value = selectedCardList
                _selectedCard.postValue(selectedCardList)

                _matchedCard.value = matchedCardList
                _matchedCard.postValue(_matchedCard.value)

                _cntAnswer.value = 0
                _cntAnswer.postValue(_cntAnswer.value)

                _point.value = 0
                _point.postValue(_point.value)

                //setProgress(100)

            }
        }
    }

    suspend fun initCardPack(gm: Int){

        cardPack = mutableListOf<Card>()

        for (i in 1..3){
            for (j in 1..3){
                for (k in 1..3){
                    for (l in 1..3){
                        cardPack.add(Card(i,j,k,l))
                    }
                }
            }
        }

        cardPack = cardPack.shuffled().toMutableList()
        gameMode = gm

        for (i in 0..11) {
            boardCardList.add(cardPack.get(0))
            cardPack.removeAt(0)
        }

    }

    // 카드가 선택되었을 떄,
    public fun setSelected(isIncluded :Boolean, value: Int):Boolean {
        Log.d("theif", "setSelected")
        // 이미 선택되어 있는 경우, 선택 해제
        if (isIncluded) {
            selectedCardList.remove(value)

            _selectedCard.value = selectedCardList
            _selectedCard.postValue(selectedCardList)
            return true
        } else { // 카드 선택 후, 3개 선택 상태이면 set인지 확인, 3개 선택 해제
            selectedCardList.add(value)
            _selectedCard.value = selectedCardList
            _selectedCard.postValue(selectedCardList)

            if (selectedCardList.size == 3) { // 3개 선택됨
                Log.d("theif", "checkSET start")
                if (checkSET()) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        selectedCardList = mutableListOf<Int>()
                        _selectedCard.value = selectedCardList
                        _selectedCard.postValue(selectedCardList)
                    }, 10)
                    return true
                } else {
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
    public fun checkSET(): Boolean {
        Log.d("theif", "checkSET")
        val firstCard = boardCardList.get(selectedCardList.get(0))
        val secondCard = boardCardList.get(selectedCardList.get(1))
        val thirdCard = boardCardList.get(selectedCardList.get(2))

        var plusAnswerScore = 0
        var plusPointScore = 0

        if (isSETonThree(firstCard, secondCard, thirdCard)) {

            plusAnswerScore = 1
            plusPointScore = 3

            // set
            //_cntAnswer.value = _cntAnswer.value?.plus(1)
            //_cntAnswer.postValue(_cntAnswer.value)

            //_point.value = point.value?.plus(3)
            //_point.postValue(_point.value)
            Log.d("theif", "replaceNewCard start")
            replaceNewCard()

        } else {
            plusAnswerScore = 0
            plusPointScore = -2
            //_cntAnswer.value = _cntAnswer.value?.minus(0)
            //_cntAnswer.postValue(_cntAnswer.value)

            //_point.value = _point.value?.minus(2)
            //_point.postValue(_point.value)


        }

        _cntAnswer.value = _cntAnswer.value?.plus(plusAnswerScore)
        _cntAnswer.postValue(_cntAnswer.value)

        _point.value = point.value?.plus(plusPointScore)
        _point.postValue(_point.value)


        return plusAnswerScore == 0
    }

    // 파라미터의 세장이 세트이면 true 반환, 세트아니면 false 반환
    public fun isSETonThree(f: Card, s: Card, t: Card):Boolean {

        var returnValue = true
        if ((f.color == s.color) and (s.color != t.color)) {
            returnValue = false
        } else if ((f.color != s.color) and (s.color == t.color)) {
            returnValue = false
        } else if ((f.color == t.color) and (t.color != s.color)) {
            returnValue = false
        }

        if ((f.count == s.count) and (s.count != t.count)) {
            returnValue = false
        } else if ((f.count != s.count) and (s.count == t.count)) {
            returnValue = false
        } else if ((f.count == t.count) and (t.count != s.count)) {
            returnValue = false
        }

        if ((f.shape == s.shape) and (s.shape != t.shape)) {
            returnValue = false
        } else if ((f.shape != s.shape) and (s.shape == t.shape)) {
            returnValue = false
        } else if ((f.shape == t.shape) and (t.shape != s.shape)) {
            returnValue = false
        }

        if ((f.pattern == s.pattern) and (s.pattern != t.pattern)) {
            returnValue = false
        } else if ((f.pattern != s.pattern) and (s.pattern == t.pattern)) {
            returnValue = false
        } else if ((f.pattern == t.pattern) and (t.pattern != s.pattern)) {
            returnValue = false
        }

        return returnValue

    }

    // set 맞출 경우 불려져서, 선택된 카드의 인덱스 자리의 카드 교체
    public fun replaceNewCard(){
        if (cardPack.size < 3){
            Log.d("theif", "replaceNewCard")
            setEndGameFlag(true)
        }
        else {
            selectedCardList.sort()

            var curMatchedCard = mutableListOf<Card>()

            for (i in 0..selectedCardList.lastIndex) {
                var curIndex = selectedCardList.get(i)
                Log.d(logtag, curIndex.toString())

                curMatchedCard.add(boardCardList.get(curIndex))
            }

            for (i in 0..selectedCardList.lastIndex) {
                var curIndex = selectedCardList.get(i)

                boardCardList[curIndex] = cardPack.get(cardPack.lastIndex)
                cardPack.removeAt(cardPack.lastIndex)
            }

            matchedCardList.add(0, curMatchedCard.toList())

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

            for (i in 0..boardCardList.lastIndex - 2) {
                for (j in 1..boardCardList.lastIndex - 1) {
                    if (i == j) {
                        continue
                    }
                    for (k in 2..boardCardList.lastIndex) {
                        if (i == k || j == k) {
                            continue
                        }
                        if (isSETonThree(
                                boardCardList.get(i),
                                boardCardList.get(j),
                                boardCardList.get(k)
                            )
                        ) {
                            cntSet += 1
                            sets.add(setOf(i + 1, j + 1, k + 1))
                        }
                    }
                }
            }

            Log.d(logtag, sets.size.toString() + " " + sets.toString())

        _cntAvailable.value = sets.size
        _cntAvailable.postValue(_cntAvailable.value)

        return sets.size
    }

    // 보드에 set가 될 카드가 없을 경우, 불려짐
    // 보드의 카드를 cardPack index 0 ~ 11 로 교체
    public fun addNewCard():Boolean{
        _point.value = point.value?.plus(-1)
        _point.postValue(_point.value)

        if (cardPack.size < 3){
            Log.d("theif", "addNewCard")
            setEndGameFlag(false)
            return false
        }
        else {
            Log.d("viewmodel", "remain card Pack : " + cardPack.size.toString())

            selectedCardList = mutableListOf<Int>()
            _selectedCard.value = selectedCardList
            _selectedCard.postValue(selectedCardList)


            cardPack.addAll(boardCardList)
            cardPack = cardPack.shuffled().toMutableList()
            boardCardList = mutableListOf<Card>()

            for (i in 0..11) {
                boardCardList.add(cardPack.get(i))
            }
            for (i in 0..11) {
                cardPack.removeAt(0)
            }

            _boardCard.value = boardCardList
            _boardCard.postValue(_boardCard.value)
            return true
        }
    }

    // 게임 종료 flag 설정
    public fun setEndGameFlag(value: Boolean){
        Log.d("theif", "setEndGameFlag")
        _endGameFlag.postValue(value)
    }

    fun getPoint(): Int {
        return point.value!!
    }

    fun setLoading() {
        setProgress(0)
    }
}