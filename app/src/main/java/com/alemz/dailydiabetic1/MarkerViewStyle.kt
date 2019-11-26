package com.alemz.dailydiabetic1

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import java.text.SimpleDateFormat

@SuppressLint("ViewConstructor")
class MarkerViewStyle(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {
    private val tvContent = findViewById<TextView>(R.id.tvContent)
    private val time = findViewById<TextView>(R.id.tvContent_time)
    private val formatter = SimpleDateFormat("HH:mm")

    // runs every time the MarkerView is redrawn, can be used to update the
// content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight) {
        if (e is CandleEntry) {
            tvContent.text = Utils.formatNumber(e.high, 0, true)
            time.text = Utils.formatNumber(e.x, 0 , true)
            time.text = formatter.format(e.x)

        }
        else {
            tvContent.text = Utils.formatNumber(e.y, 0, true)
            time.text = formatter.format(e.x)
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }

}
