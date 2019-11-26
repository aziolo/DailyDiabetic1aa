package com.alemz.dailydiabetic1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alemz.dailydiabetic1.data.entities.MedicineEntity

class MedAdapter: RecyclerView.Adapter<MedAdapter.MedHolder>()  {

    private var list: List<MedicineEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_medicines, parent, false)
        return MedHolder(itemView)
    }

    override fun onBindViewHolder(holder: MedHolder, position: Int) {
        val currentMed = list[position]
        holder.time.text = currentMed.date.substring(11,16)
        holder.name.text = currentMed.medicine_name
        holder.dose.text = currentMed.dose.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setMed(list: List<MedicineEntity>){
        this.list = list
        notifyDataSetChanged()
    }
    inner class MedHolder(view: View): RecyclerView.ViewHolder(view){
        var time : TextView = view.findViewById(R.id.hour_TV)
        var name : TextView = view.findViewById(R.id.namee_TV)
        var dose : TextView = view.findViewById(R.id.dose_TV)
    }
}