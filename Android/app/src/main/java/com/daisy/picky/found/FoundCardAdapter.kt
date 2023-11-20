package com.daisy.picky.found


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daisy.picky.Card
import com.daisy.picky.R
import com.daisy.picky.databinding.ItemFoundSetsBinding


class FoundCardAdapter(context: Context) : RecyclerView.Adapter<FoundCardAdapter.ViewHolder>() {

    lateinit var foundCard: List<List<Card>>
    val context: Context = context



    inner class ViewHolder(private val binding: ItemFoundSetsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pos:Int){
            val index = foundCard.size - pos
            if (index < 10) {
                binding.txtCurFoundSet.text = "0$index"
            }else{
                binding.txtCurFoundSet.text = index.toString()
            }

            binding.rcyFoundCardset.apply{
                adapter = FoundCardDetailsAdapter(foundCard.get(pos))
                layoutManager = GridLayoutManager(context, 3)
                setHasFixedSize(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFoundSetsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = foundCard.size
}