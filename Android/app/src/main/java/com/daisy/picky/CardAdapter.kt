package com.daisy.picky

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.MaterialCalendar
import java.security.AccessController.getContext


class CardAdapter(listener: OnCardClick, context: Context) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    lateinit var boardCard: List<Card>
    lateinit var selectedCard: List<Int>
    var context: Context = context

    interface OnItemClickListener {
        fun onItemClick(pos: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        onItemClickListener = listener
    }

    private val mCallback = listener
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
        }

        fun setCount(curCard:Card){
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

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("1animation", "origin "+position.toString())
        holder.card.setOnClickListener{
            mCallback.selected(position)
        }

        var selected = false
        for(i in 0..selectedCard.lastIndex){
            if (position == selectedCard[i]){
                selected = true
            }
        }

        // 카드 선택 여부 확인 selected
        if (selected) {
            holder.card.strokeWidth = 10
            holder.card.strokeColor = ContextCompat.getColor(holder.card.context, R.color.card_stroke_selected)
            /*
            holder.card.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.card.context,
                    R.color.card_background_selected
                )

            )
             */
        }else {
            holder.card.strokeWidth = 3
            holder.card.strokeColor = ContextCompat.getColor(holder.card.context, R.color.card_stroke_normal)
            /*
            holder.card.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.card.context,
                    R.color.card_background_normal
                )
            )
             */
        }

        holder.setShape(boardCard.get(position))
        holder.setCount(boardCard.get(position))

    }



    override fun getItemCount() = boardCard.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {

        Log.d("1animation", position.toString() +" "+ payloads.toString())
        if (!payloads.isEmpty()){
            for (any in payloads){
                if (any == "anim"){
                    Log.d("1animation", position.toString())
                    val animation = AnimationUtils.loadAnimation(holder.card.context, R.anim.wave)
                    holder.card.animation = animation
                }
            }
        }
        else{
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}