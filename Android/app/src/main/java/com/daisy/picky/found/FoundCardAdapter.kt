package com.daisy.picky.found


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daisy.picky.Card
import com.daisy.picky.R


class FoundCardAdapter(context: Context) : RecyclerView.Adapter<FoundCardAdapter.ViewHolder>() {

    lateinit var foundCard: List<Card>
    val context: Context = context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView
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

        fun setCount(curCard: Card) {
            // 갯수
            val count = curCard.count
            if (count == 1) {
                pattern1.visibility = View.INVISIBLE
                pattern2.visibility = View.VISIBLE
                pattern3.visibility = View.INVISIBLE
                pattern4.visibility = View.INVISIBLE
                pattern5.visibility = View.INVISIBLE
            } else if (count == 2) {
                pattern1.visibility = View.INVISIBLE
                pattern2.visibility = View.INVISIBLE
                pattern3.visibility = View.INVISIBLE
                pattern4.visibility = View.VISIBLE
                pattern5.visibility = View.VISIBLE
            } else if (count == 3) {
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
        holder.setShape(foundCard.get(position))
        holder.setCount(foundCard.get(position))
    }

    override fun getItemCount() = foundCard.size
}