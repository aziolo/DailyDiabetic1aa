package com.alemz.dailydiabetic1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alemz.dailydiabetic1.data.entities.PressureEntity

class PressureAdapter: RecyclerView.Adapter<PressureAdapter.PressureHolder>() {

    private var list: List<PressureEntity> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PressureHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pressure, parent, false)
        return PressureHolder(itemView)

    }

    override fun onBindViewHolder(holder: PressureHolder, position: Int) {
        val currentPressure = list[position]
        holder.time.text = currentPressure.date.substring(11,16)
        holder.pressure.text = currentPressure.level_systolic.toString()+" / "+ currentPressure.level_diastolic.toString()
        holder.pulse.text = currentPressure.pulse.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setPressure(list: List<PressureEntity>){
        this.list = list
        notifyDataSetChanged()
    }


    inner class PressureHolder(view: View): RecyclerView.ViewHolder(view) {
        var time: TextView = view.findViewById(R.id.time_pressure)
        var pressure: TextView = view.findViewById(R.id.bp_TV)
        var pulse: TextView = view.findViewById(R.id.pulse_TV)
    }
}