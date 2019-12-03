package com.alemz.dailydiabetic1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alemz.dailydiabetic1.R
import com.alemz.dailydiabetic1.data.entities.GlikemiaEntity

class GlikemiaAdapter(): RecyclerView.Adapter<GlikemiaAdapter.GlikemiaHolder>() {

    private var list: List<GlikemiaEntity> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GlikemiaHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_glikemia, parent, false)
        return GlikemiaHolder(itemView)

    }

    override fun onBindViewHolder(holder: GlikemiaHolder, position: Int) {
        val currentGlikemia = list[position]
        holder.time.text = currentGlikemia.date.substring(11,16)
        holder.amount.text = currentGlikemia.amount.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setGlikemia(list: List<GlikemiaEntity>){
        this.list = list
        notifyDataSetChanged()
    }

    inner class GlikemiaHolder(view: View) : RecyclerView.ViewHolder(view){
        var time: TextView = view.findViewById(R.id.time_text)
        var amount: TextView = view.findViewById(R.id.amount_text)
    }
}