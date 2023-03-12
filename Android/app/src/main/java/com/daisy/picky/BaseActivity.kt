package com.daisy.picky

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    companion object{
        var gameMode = 0
        private val GAME_MODE_SINGLE = 1
        private val GAME_MODE_MULTI = 2
        private val GAME_MODE_TUTORIAL = 3

        private var dimen = 3

        private val CARD_COLOR = listOf("red", "blue", "green")
        private val CARD_COUNT = listOf(1, 2, 3)
        private val CARD_SHAPE = listOf("cir", "rect", "wave")
        private val CARD_PATTERN = listOf("fill", "empty", "stripe")

        var cardPack :MutableList<Card> = mutableListOf<Card>()

        init {
            initCardPack()
        }

        public fun initCardPack(){
            for (i in 1..dimen){
                for (j in 1..dimen){
                    for (k in 1..dimen){
                        for (l in 1..dimen){
                            cardPack.add(Card(i,j,k,l))
                        }
                    }
                }
            }

        }
    }


}