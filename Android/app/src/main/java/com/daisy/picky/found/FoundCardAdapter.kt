package com.daisy.picky.found


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daisy.picky.Card
import com.daisy.picky.R


class FoundCardAdapter() : RecyclerView.Adapter<FoundCardAdapter.ViewHolder>() {

    lateinit var foundCard: List<Card>

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

        fun setColor(curCard: Card) {
            val color = curCard.color
            if (color == 1) {
                pattern1.setColorFilter(
                    ContextCompat.getColor(
                        pattern1.context,
                        R.color.card_color_1
                    )
                )
                pattern2.setColorFilter(
                    ContextCompat.getColor(
                        pattern2.context,
                        R.color.card_color_1
                    )
                )
                pattern3.setColorFilter(
                    ContextCompat.getColor(
                        pattern3.context,
                        R.color.card_color_1
                    )
                )
                pattern4.setColorFilter(
                    ContextCompat.getColor(
                        pattern4.context,
                        R.color.card_color_1
                    )
                )
                pattern5.setColorFilter(
                    ContextCompat.getColor(
                        pattern5.context,
                        R.color.card_color_1
                    )
                )
            } else if (color == 2) {
                pattern1.setColorFilter(
                    ContextCompat.getColor(
                        pattern1.context,
                        R.color.card_color_2
                    )
                )
                pattern2.setColorFilter(
                    ContextCompat.getColor(
                        pattern2.context,
                        R.color.card_color_2
                    )
                )
                pattern3.setColorFilter(
                    ContextCompat.getColor(
                        pattern3.context,
                        R.color.card_color_2
                    )
                )
                pattern4.setColorFilter(
                    ContextCompat.getColor(
                        pattern4.context,
                        R.color.card_color_2
                    )
                )
                pattern5.setColorFilter(
                    ContextCompat.getColor(
                        pattern5.context,
                        R.color.card_color_2
                    )
                )
            } else if (color == 3) {
                pattern1.setColorFilter(
                    ContextCompat.getColor(
                        pattern1.context,
                        R.color.card_color_3
                    )
                )
                pattern2.setColorFilter(
                    ContextCompat.getColor(
                        pattern2.context,
                        R.color.card_color_3
                    )
                )
                pattern3.setColorFilter(
                    ContextCompat.getColor(
                        pattern3.context,
                        R.color.card_color_3
                    )
                )
                pattern4.setColorFilter(
                    ContextCompat.getColor(
                        pattern4.context,
                        R.color.card_color_3
                    )
                )
                pattern5.setColorFilter(
                    ContextCompat.getColor(
                        pattern5.context,
                        R.color.card_color_3
                    )
                )
            }
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

        fun setShape(curCard: Card) {
            val shape = curCard.shape
            val pattern = curCard.pattern
            if ((shape == 1) and (pattern == 1)) {
                pattern1.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern1.context,
                        R.drawable.shape_1_1
                    )
                )
                pattern2.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern2.context,
                        R.drawable.shape_1_1
                    )
                )
                pattern3.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern3.context,
                        R.drawable.shape_1_1
                    )
                )
                pattern4.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern4.context,
                        R.drawable.shape_1_1
                    )
                )
                pattern5.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern5.context,
                        R.drawable.shape_1_1
                    )
                )
            } else if ((shape == 1) and (pattern == 2)) {
                pattern1.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern1.context,
                        R.drawable.shape_1_2
                    )
                )
                pattern2.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern2.context,
                        R.drawable.shape_1_2
                    )
                )
                pattern3.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern3.context,
                        R.drawable.shape_1_2
                    )
                )
                pattern4.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern4.context,
                        R.drawable.shape_1_2
                    )
                )
                pattern5.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern5.context,
                        R.drawable.shape_1_2
                    )
                )
            } else if ((shape == 1) and (pattern == 3)) {
                pattern1.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern1.context,
                        R.drawable.shape_1_3
                    )
                )
                pattern2.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern2.context,
                        R.drawable.shape_1_3
                    )
                )
                pattern3.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern3.context,
                        R.drawable.shape_1_3
                    )
                )
                pattern4.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern4.context,
                        R.drawable.shape_1_3
                    )
                )
                pattern5.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern5.context,
                        R.drawable.shape_1_3
                    )
                )
            } else if ((shape == 2) and (pattern == 1)) {
                pattern1.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern1.context,
                        R.drawable.shape_2_1
                    )
                )
                pattern2.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern2.context,
                        R.drawable.shape_2_1
                    )
                )
                pattern3.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern3.context,
                        R.drawable.shape_2_1
                    )
                )
                pattern4.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern4.context,
                        R.drawable.shape_2_1
                    )
                )
                pattern5.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern5.context,
                        R.drawable.shape_2_1
                    )
                )
            } else if ((shape == 2) and (pattern == 2)) {
                pattern1.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern1.context,
                        R.drawable.shape_2_2
                    )
                )
                pattern2.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern2.context,
                        R.drawable.shape_2_2
                    )
                )
                pattern3.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern3.context,
                        R.drawable.shape_2_2
                    )
                )
                pattern4.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern4.context,
                        R.drawable.shape_2_2
                    )
                )
                pattern5.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern5.context,
                        R.drawable.shape_2_2
                    )
                )
            } else if ((shape == 2) and (pattern == 3)) {
                pattern1.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern1.context,
                        R.drawable.shape_2_3
                    )
                )
                pattern2.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern2.context,
                        R.drawable.shape_2_3
                    )
                )
                pattern3.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern3.context,
                        R.drawable.shape_2_3
                    )
                )
                pattern4.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern4.context,
                        R.drawable.shape_2_3
                    )
                )
                pattern5.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern5.context,
                        R.drawable.shape_2_3
                    )
                )
            } else if ((shape == 3) and (pattern == 1)) {
                pattern1.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern1.context,
                        R.drawable.shape_3_1
                    )
                )
                pattern2.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern2.context,
                        R.drawable.shape_3_1
                    )
                )
                pattern3.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern3.context,
                        R.drawable.shape_3_1
                    )
                )
                pattern4.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern4.context,
                        R.drawable.shape_3_1
                    )
                )
                pattern5.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern5.context,
                        R.drawable.shape_3_1
                    )
                )
            } else if ((shape == 3) and (pattern == 2)) {
                pattern1.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern1.context,
                        R.drawable.shape_3_2
                    )
                )
                pattern2.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern2.context,
                        R.drawable.shape_3_2
                    )
                )
                pattern3.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern3.context,
                        R.drawable.shape_3_2
                    )
                )
                pattern4.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern4.context,
                        R.drawable.shape_3_2
                    )
                )
                pattern5.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern5.context,
                        R.drawable.shape_3_2
                    )
                )
            } else if ((shape == 3) and (pattern == 3)) {
                pattern1.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern1.context,
                        R.drawable.shape_3_3
                    )
                )
                pattern2.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern2.context,
                        R.drawable.shape_3_3
                    )
                )
                pattern3.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern3.context,
                        R.drawable.shape_3_3
                    )
                )
                pattern4.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern4.context,
                        R.drawable.shape_3_3
                    )
                )
                pattern5.setImageDrawable(
                    ContextCompat.getDrawable(
                        pattern5.context,
                        R.drawable.shape_3_3
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setShape(foundCard.get(position))
        holder.setColor(foundCard.get(position))
        holder.setCount(foundCard.get(position))
    }

    override fun getItemCount() = foundCard.size
}