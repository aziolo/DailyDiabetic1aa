package com.alemz.dailydiabetic1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alemz.dailydiabetic1.R
import com.alemz.dailydiabetic1.data.entities.InsulinEntity

class InsulinAdapter: RecyclerView.Adapter<InsulinAdapter.InsulinHolder>() {

    private var list: List<InsulinEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsulinHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_insulin, parent, false)
        return InsulinHolder(itemView)
    }

    override fun onBindViewHolder(holder: InsulinHolder, position: Int) {
        val currentInsulin = list[position]
        holder.time.text = currentInsulin.date.substring(11,16)
        holder.amount.text = currentInsulin.amount.toString()
        holder.type.text = currentInsulin.type
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setInsulin(list: List<InsulinEntity>){
        this.list = list
        notifyDataSetChanged()
    }
    inner class InsulinHolder(view: View): RecyclerView.ViewHolder(view){
        var time : TextView = view.findViewById(R.id.time_insulin)
        var amount : TextView = view.findViewById(R.id.amount_insulin)
        var type : TextView = view.findViewById(R.id.type_insulin)
    }
}