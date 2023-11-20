package com.daisy.picky.found

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daisy.picky.game.Card
import com.daisy.picky.databinding.ItemCardBinding

class FoundCardDetailsAdapter(private val data: List<Card>) : RecyclerView.Adapter<FoundCardDetailsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding:ItemCardBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        fun bind(pos:Int){
            setShape(data.get(pos), context)
            setCount(data.get(pos))
        }

        fun setCount(curCard: Card) {
            // 갯수
            val count = curCard.count
            if (count == 1) {
                binding.card1.visibility = View.INVISIBLE
                binding.card2.visibility = View.VISIBLE
                binding.card3.visibility = View.INVISIBLE
                binding.card4.visibility = View.INVISIBLE
                binding.card5.visibility = View.INVISIBLE
            } else if (count == 2) {
                binding.card1.visibility = View.INVISIBLE
                binding.card2.visibility = View.INVISIBLE
                binding.card3.visibility = View.INVISIBLE
                binding.card4.visibility = View.VISIBLE
                binding.card5.visibility = View.VISIBLE
            } else if (count == 3) {
                binding.card1.visibility = View.VISIBLE
                binding.card2.visibility = View.VISIBLE
                binding.card3.visibility = View.VISIBLE
                binding.card4.visibility = View.INVISIBLE
                binding.card5.visibility = View.INVISIBLE
            }
        }
        fun setShape(curCard: Card, context: Context){
            val shape = curCard.shape
            val pattern = curCard.pattern
            val color = curCard.color

            val name = "pattern_"+shape.toString()+"_"+pattern.toString()+"_"+color.toString()

            val id = context.resources.getIdentifier(name, "drawable", context.packageName)

            binding.card1.setImageResource(id)
            binding.card2.setImageResource(id)
            binding.card3.setImageResource(id)
            binding.card4.setImageResource(id)
            binding.card5.setImageResource(id)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

