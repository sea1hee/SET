package com.daisy.picky.game

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daisy.picky.R
import com.google.android.material.card.MaterialCardView


class CardAdapter(context: Context) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    var boardCard: List<Card> = listOf<Card>()
    var selectedCard: List<Int> = listOf<Int>()
    var context: Context = context

    fun setOnItemClickListener(a_listener: OnItemClickEventListener) {
        mItemClickListener = a_listener
    }
    private lateinit var mItemClickListener: OnItemClickEventListener
    interface OnItemClickEventListener {
        fun onItemClick(a_view: ViewGroup, view: MaterialCardView, a_position: Int)
    }

    inner class ViewHolder(view: View,
        a_itemClickListener: OnItemClickEventListener) : RecyclerView.ViewHolder(view) {

        val card: MaterialCardView
        val pattern1: ImageView
        val pattern2: ImageView
        val pattern3: ImageView
        val pattern4: ImageView
        val pattern5: ImageView
        init {
            card = view.findViewById(R.id.card)
            pattern1 = view.findViewById(R.id.card_1)
            pattern2 = view.findViewById(R.id.card_2)
            pattern3 = view.findViewById(R.id.card_3)
            pattern4 = view.findViewById(R.id.card_4)
            pattern5 = view.findViewById(R.id.card_5)

            view.setOnClickListener { a_view ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    a_itemClickListener.onItemClick(card, card, position)
                }
            }

        }

        fun setCount(curCard: Card){
            // 갯수
            val count = curCard.count
            if (count == 1){
                pattern1.visibility = View.INVISIBLE
                pattern2.visibility = View.VISIBLE
                pattern3.visibility = View.INVISIBLE
                pattern4.visibility = View.INVISIBLE
                pattern5.visibility = View.INVISIBLE
            } else if(count == 2){
                pattern1.visibility = View.INVISIBLE
                pattern2.visibility = View.INVISIBLE
                pattern3.visibility = View.INVISIBLE
                pattern4.visibility = View.VISIBLE
                pattern5.visibility = View.VISIBLE
            } else if(count == 3){
                pattern1.visibility = View.VISIBLE
                pattern2.visibility = View.VISIBLE
                pattern3.visibility = View.VISIBLE
                pattern4.visibility = View.INVISIBLE
                pattern5.visibility = View.INVISIBLE
            }
        }

        fun setShape(curCard: Card){
            val shape = curCard.shape
            val pattern = curCard.pattern
            val color = curCard.color

            val name = "pattern_"+shape.toString()+"_"+pattern.toString()+"_"+color.toString()

            val id = context.resources.getIdentifier(name, "drawable", context.packageName)

            pattern1.setImageResource(id)
            pattern2.setImageResource(id)
            pattern3.setImageResource(id)
            pattern4.setImageResource(id)
            pattern5.setImageResource(id)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)

        return ViewHolder(view, mItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
/*
        holder.card.setOnClickListener{
            mCallback.selected(position)
        }*/

        var selected = false
        for(i in 0..selectedCard.lastIndex){
            if (position == selectedCard[i]){
                selected = true
            }
        }

        // 카드 선택 여부 확인 selected
        if (selected) {
            holder.card.strokeWidth = 2
            holder.card.strokeColor = ContextCompat.getColor(holder.card.context,
                R.color.card_stroke_selected
            )
        }else {
            holder.card.strokeWidth = 3
            holder.card.strokeColor = ContextCompat.getColor(
                holder.card.context,
                R.color.card_stroke_normal
            )
        }

        holder.setShape(boardCard.get(position))
        holder.setCount(boardCard.get(position))

    }

    override fun getItemCount() = boardCard.size

}