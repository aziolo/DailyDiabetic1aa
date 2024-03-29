package com.alemz.dailydiabetic1.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Color.WHITE
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alemz.dailydiabetic1.AppViewModel
import com.alemz.dailydiabetic1.MarkerViewStyle
import com.alemz.dailydiabetic1.R
import com.alemz.dailydiabetic1.data.entities.InsulinEntity
import com.alemz.dailydiabetic1.data.entities.PressureEntity
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.round

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RaportFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RaportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RaportFragment : Fragment(), OnChartValueSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null



    private lateinit var chartG: LineChart
    private lateinit var chartBP: CombinedChart
    private lateinit var chartI: CombinedChart
    val appViewModel: AppViewModel by lazy{ ViewModelProviders.of(this).get(AppViewModel::class.java)}
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val formMonth = SimpleDateFormat("yyyy-MM")
    private val statisticFormatter = SimpleDateFormat("yyyy-MM HH")

    private  val months = listOf("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień","Wrzesień", "Październik","Listopad","Grudzień")
    private lateinit var currentMonth: String
    private var countDaysInMounth = 0
    private var step = 0
    private var firstDayOfSelectedMonth = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_raport, container, false)
        val c = Calendar.getInstance()
        // Inflate the layout for this fragment
        val btnG = view.findViewById<Button>(R.id.button_g)
        val btnBP = view.findViewById<Button>(R.id.button_bp)
        val btnI = view.findViewById<Button>(R.id.button_i)

        val cvg = view.findViewById<CardView>(R.id.CVG)
        val cvbp = view.findViewById<CardView>(R.id.CVBP)
        val cvi = view.findViewById<CardView>(R.id.CVI)


        val previous = view.findViewById<Button>(R.id.button_past)
        val next = view.findViewById<Button>(R.id.button_future)

        val monthTxt = view.findViewById<TextView>(R.id.textView_current_moth)
        val lowTV = view.findViewById<TextView>(R.id.textView_low)
        val highTV = view.findViewById<TextView>(R.id.textView_high)
        val inRangeTV = view.findViewById<TextView>(R.id.textView_in_target)
        val averageTV = view.findViewById<TextView>(R.id.textView_average)
        val countTV = view.findViewById<TextView>(R.id.textView_count)


        //start state
        currentMonth = "%"+formMonth.format(c.time)+"%"
        countDaysInMounth = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        monthTxt.text = months[c.get(Calendar.MONTH)] +" "+ c.get(Calendar.YEAR).toString()
        cvbp.isVisible = false
        cvi.isVisible = false
        cvg.isVisible = true

        drawChartGlikemiaBetter(view)
        drawChartBP(view)
        drawSecondInsulin(view)

        statisticGlikemia(inRangeTV, lowTV, highTV, averageTV, countTV)

        next.setOnClickListener {
            c.add(Calendar.MONTH, 1)
            val m = c.get(Calendar.MONTH)
            monthTxt.text = "${months[m]}"+" "+"${c.get(Calendar.YEAR)}"
            currentMonth = "%"+formMonth.format(c.time)+"%"
            countDaysInMounth = c.getActualMaximum(Calendar.DAY_OF_MONTH)
            drawChartBP(view)
            drawChartGlikemiaBetter(view)
            //drawChartInsulin(view)
            drawSecondInsulin(view)
            statisticGlikemia(inRangeTV, lowTV, highTV, averageTV, countTV)
        }

        previous.setOnClickListener {
            c.add(Calendar.MONTH, -1)
            val date = c.time
            val m = c.get(Calendar.MONTH)
            monthTxt.text = "${months[m]}"+" "+"${c.get(Calendar.YEAR)}"
            currentMonth = "%"+formMonth.format(c.time)+"%"
            countDaysInMounth = c.getActualMaximum(Calendar.DAY_OF_MONTH)

            drawChartGlikemiaBetter(view)
            drawChartBP(view)
           // drawChartInsulin(view)
            drawSecondInsulin(view)
            statisticGlikemia(inRangeTV, lowTV, highTV, averageTV, countTV)


        }

        btnG.setOnClickListener {
            cvbp.isVisible = false
            cvi.isVisible = false
            cvg.isVisible = true
        }
        btnBP.setOnClickListener {
            cvg.isVisible = false
            cvi.isVisible = false
            cvbp.isVisible = true

        }
        btnI.setOnClickListener {
            cvbp.isVisible = false
            cvg.isVisible = false
            cvi.isVisible = true

        }


        return view
    }

    @SuppressLint("SetTextI18n")
    private fun statisticGlikemia(
        inRangeTV: TextView,
        lowTV: TextView,
        highTV: TextView,
        averageTV: TextView,
        countTV: TextView
    ) {
        val averageG = round(appViewModel.monthlyAverageGlikemia(currentMonth)*10)/10
        val countAllG = appViewModel.countAllForThisMounth(currentMonth)
        val countInRangeG = appViewModel.countInRageForThisMonth(currentMonth, "70", "180")
        countTV.text = countAllG.toString()
        if(countAllG == 0.0 ) {
            lowTV.text = context!!.resources.getString(R.string.no_value)
            highTV.text = context!!.resources.getString(R.string.no_value)
            inRangeTV.text = context!!.resources.getString(R.string.no_value)
            averageTV.text = context!!.resources.getString(R.string.no_value)
        }
        else{
            inRangeTV.text = context!!.resources.getString(R.string.in_range) +" "+
                    (round(countInRangeG / countAllG * 100 * 10) / 10).toString() + "%"
            lowTV.text = context!!.resources.getString(R.string.low_level) +" "+ (round(
                appViewModel.countInRageForThisMonth(
                    currentMonth,
                    "0",
                    "70"
                ) / countAllG * 100 * 10
            ) / 10).toString() + "%"
            highTV.text = context!!.resources.getString(R.string.high_level) + " "+(round(
                appViewModel.countInRageForThisMonth(
                    currentMonth,
                    "180",
                    "1000"
                ) / countAllG * 100 * 10
            ) / 10).toString() + "%"
            averageTV.text = context!!.resources.getString(R.string.average) +" "+ averageG

        }
    }

    private fun drawChartBP(view: View) {

        //style
        chartBP = view.findViewById(R.id.BPChart)
        chartBP.setBackgroundColor(WHITE)
        chartBP.setGridBackgroundColor(WHITE)
        chartBP.setDrawGridBackground(true)
        chartBP.setDrawBorders(true)
        chartBP.description.isEnabled = false

        val lBP = chartBP.legend
        lBP.isWordWrapEnabled = false
        lBP.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        lBP.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        lBP.orientation = Legend.LegendOrientation.HORIZONTAL
        lBP.setDrawInside(false)


        //  all data
        val pulseData = LineData()
        val bpData = LineData()

        // lists of entries
        val entriesSys: ArrayList<Entry> = ArrayList()
        val entriesDia: ArrayList<Entry> = ArrayList()
        val entriesPulse: ArrayList<Entry> = ArrayList()

        // creates Entries
        appViewModel.getBPForChosenDate(currentMonth).observe(this, Observer<List<PressureEntity>> { t ->
            val list = ArrayList(t)
            for (i in 0 until list.size) {
                val sys = list[i].level_diastolic.toFloat()
                val dia = list[i].level_systolic.toFloat()
                val pulse = list[i].pulse.toFloat()
                var date = formatter.parse(list[i].date).time
                entriesSys.add(Entry(date.toFloat(), sys))
                entriesDia.add(Entry(date.toFloat(), dia))
                entriesPulse.add(Entry(date.toFloat(),pulse))
            }

            val sysDataSet = LineDataSet(entriesSys, "rozkurczowe")
            val diaDataSet = LineDataSet(entriesDia, "skurczowe")
            val pulseDataSet = LineDataSet(entriesPulse, "puls")

            sysDataSet.axisDependency = YAxis.AxisDependency.LEFT
            sysDataSet.color = Color.rgb(55, 99, 190)
            sysDataSet.lineWidth = 2.5f
            sysDataSet.setDrawCircleHole(false)
            sysDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            sysDataSet.setDrawValues(true)
            sysDataSet.valueTextSize = 10f
            sysDataSet.valueTextColor = Color.rgb(55, 99, 190)

            diaDataSet.axisDependency = YAxis.AxisDependency.LEFT
            diaDataSet.color = Color.rgb(61, 165, 255)
            diaDataSet.lineWidth = 2.5f
            diaDataSet.setDrawCircleHole(false)
            diaDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            diaDataSet.setDrawValues(true)
            diaDataSet.valueTextSize = 10f
            diaDataSet.valueTextColor = Color.rgb(61, 165, 255)

            pulseDataSet.setColors(Color.RED)
            pulseDataSet.valueTextSize = 10f
            pulseDataSet.valueTextColor = (Color.RED)

            pulseDataSet.setDrawValues(true)


            bpData.addDataSet(sysDataSet)
            bpData.addDataSet(diaDataSet)
            //bpData.addDataSet(pulseDataSet)
            val data = CombinedData()
            data.setData(bpData)
            //axis
            var xAxis = chartBP.xAxis
            var yAxis = chartBP.axisLeft
            val vAR = chartBP.axisRight
            vAR.isEnabled = false

            xAxis.position = XAxisPosition.BOTTOM
            xAxis.setDrawAxisLine(true)
            xAxis.labelCount = countDaysInMounth
            yAxis.setDrawAxisLine(true)
            step = 0
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val kk = "day"
                    return step++.toString()
                }
            }

            chartBP.data = data
            chartBP.invalidate()

        })


    }

    private fun drawChartInsulin(view: View) {

        //style
        chartI = view.findViewById(R.id.InsulinChart)
        chartI.setBackgroundColor(WHITE)
        chartI.description.isEnabled = false
        chartI.setTouchEnabled(true)
        chartI.setDrawGridBackground(true)
        chartI.setDrawBorders(true)
        chartI.setScaleEnabled(true)
        chartI.setPinchZoom(true)
        chartI.setGridBackgroundColor(WHITE)

        val li = chartI.legend
        li.isWordWrapEnabled = false
        li.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        li.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        li.orientation = Legend.LegendOrientation.HORIZONTAL
        li.setDrawInside(false)


        //  all data
        val insulinData = CombinedData()

        // lists of entries
        val entriesSettle: ArrayList<BarEntry> = ArrayList()
        val entriesFood: ArrayList<BarEntry> = ArrayList()
        // creates Entries
        appViewModel.getSettleInsulin().observe(this, Observer<List<InsulinEntity>> { t ->
            val list = ArrayList(t)
            for (i in 0 until list.size) {
                val amount = list[i].amount .toFloat()
                var date = formatter.parse(list[i].date).time
                entriesSettle.add(BarEntry(date.toFloat(), amount))
            }
        })

        appViewModel.getFoodInsulin().observe(this, Observer<List<InsulinEntity>> { t ->
            val list = ArrayList(t)
            for (i in 0 until list.size) {
                val amount = list[i].amount .toFloat()
                var date = formatter.parse(list[i].date).time
                entriesFood.add(BarEntry(date.toFloat(), amount))
            }
        })
        // stacked

            val settleDataSet = BarDataSet(entriesSettle, "korekta")
            val foodDataSet = BarDataSet(entriesFood, "okołoposiłkowa")

            settleDataSet.axisDependency = YAxis.AxisDependency.LEFT
            settleDataSet.color = Color.rgb(55, 99, 190)
            settleDataSet.setDrawValues(true)
            settleDataSet.valueTextSize = 10f
            settleDataSet.valueTextColor = Color.rgb(55, 99, 190)

            foodDataSet.axisDependency = YAxis.AxisDependency.LEFT
            foodDataSet.color = Color.rgb(61, 165, 255)
            foodDataSet.setDrawValues(true)
            foodDataSet.valueTextSize = 10f
            //foodDataSet.formSize = 100000000000000000f
            foodDataSet.valueTextColor = Color.rgb(61, 165, 255)

            //axis
            var xAxis = chartI.xAxis
            var yAxis = chartI.axisLeft

            xAxis.position = XAxisPosition.BOTTOM
            xAxis.setDrawAxisLine(true)
            yAxis.setDrawAxisLine(true)
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val kk = "day"
                    return kk
                }
            }

        val data = BarData()
        data.addDataSet(settleDataSet)
        data.addDataSet(foodDataSet)
//
//        val groupSpace = 0.06f
//        val barSpace = 0.02f // x2 dataset

        val barWidth = 10000000f // x2 dataset

        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        data.barWidth = barWidth

//        // make this BarData object grouped
//        // make this BarData object grouped
//        data.groupBars(0f, groupSpace, barSpace) // start at x = 0

        insulinData.setData(data)
        chartI.data = insulinData
        chartI.invalidate()
        chartI.notifyDataSetChanged()




    }

    private fun drawSecondInsulin(view: View) {

        //style
        chartI = view.findViewById(R.id.InsulinChart)
        chartI.setBackgroundColor(WHITE)
        chartI.description.isEnabled = false
        chartI.setTouchEnabled(true)
        chartI.setDrawGridBackground(true)
        chartI.setDrawBorders(true)
        chartI.setScaleEnabled(true)
        chartI.setPinchZoom(true)
        chartI.setGridBackgroundColor(WHITE)

        val li = chartI.legend
        li.isWordWrapEnabled = false
        li.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        li.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        li.orientation = Legend.LegendOrientation.HORIZONTAL
        li.setDrawInside(false)


        //  all data
        val insulinData = CombinedData()

        // lists of entries
        val entriesSettle: ArrayList<BarEntry> = ArrayList()
        val entriesFood: ArrayList<BarEntry> = ArrayList()
        val entries2 = java.util.ArrayList<BarEntry>()

        // creates Entries
        for (i in 0 ..  countDaysInMounth){
            var d: String = ""
            if (i < 10) d = currentMonth.substring(0,8)+ "-0" + i.toString() + "%"
            if (i > 9) d = currentMonth.substring(0,8)+ "-" + i.toString() + "%"
            appViewModel.sumAllFoodInsulinForChosenDate(d)
            val amount = appViewModel.sumAllFoodInsulinForChosenDate(d).toFloat()
            var date = i.toFloat()
            entriesFood.add(BarEntry(date, amount))

            val amountd = appViewModel.sumAllSettleInsulinForChosenDate(d).toFloat()
            entriesSettle.add(BarEntry(date, amountd))

            // stacked
            entries2.add(BarEntry(date, floatArrayOf(amount, amountd)))

        }


        // stacked
        val settleDataSet = BarDataSet(entriesSettle, "korekta")
        val foodDataSet = BarDataSet(entriesFood, "okołoposiłkowa")

        val set2 = BarDataSet(entries2, "")
        set2.stackLabels = arrayOf("Stack 1", "Stack 2")
        set2.setColors(
            Color.rgb(61, 165, 255),
            Color.rgb(23, 197, 255)
        )
        set2.valueTextColor = Color.rgb(61, 165, 255)
        set2.valueTextSize = 10f
        set2.axisDependency = YAxis.AxisDependency.LEFT

//        settleDataSet.axisDependency = YAxis.AxisDependency.LEFT
//        settleDataSet.color = Color.rgb(55, 99, 190)
//        settleDataSet.setDrawValues(false)
//        settleDataSet.valueTextSize = 10f
//        settleDataSet.valueTextColor = Color.rgb(55, 99, 190)
//
//        foodDataSet.axisDependency = YAxis.AxisDependency.LEFT
//        foodDataSet.color = Color.rgb(61, 165, 255)
//        foodDataSet.setDrawValues(false)
//        foodDataSet.valueTextSize = 10f
//        //foodDataSet.formSize = 100000000000000000f
//        foodDataSet.valueTextColor = Color.rgb(61, 165, 255)

        //axis
        var xAxis = chartI.xAxis
        var yAxis = chartI.axisLeft
        val vAR = chartI.axisRight
        vAR.isEnabled = false

        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(true)
        xAxis.axisMinimum = 0f

        yAxis.axisMinimum = 0f
        yAxis.setDrawAxisLine(true)



        val data = BarData()
       // data.addDataSet(settleDataSet)
        //data.addDataSet(foodDataSet)
        data.addDataSet(set2)

        val groupSpace = 0.06f
        val barSpace = 0.02f


        data.barWidth = 0.4f
        //data.groupBars(0f, groupSpace, barSpace) // start at x = 0

        insulinData.setData(data)
        chartI.data = insulinData
        chartI.invalidate()
        chartI.notifyDataSetChanged()




    }

    private fun drawChartGlikemiaBetter(view: View) {
        chartG = view.findViewById(R.id.glikemiaChart)

        //style
        chartG.setBackgroundColor(Color.WHITE)
        chartG.setGridBackgroundColor(Color.rgb(255, 175, 0))
        chartG.setDrawGridBackground(true)
        chartG.description.isEnabled = false
        chartG.setTouchEnabled(true)
        chartG.setDrawBorders(true)
        chartG.isDragEnabled = true
        chartG.setScaleEnabled(true)
        chartG.setPinchZoom(true)

        //legend
        val lg = chartG.legend
        lg.isWordWrapEnabled = false
        lg.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        lg.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        lg.orientation = Legend.LegendOrientation.HORIZONTAL
        lg.setDrawInside(false)
        // draw legend entries as lines
        lg.form = LegendForm.LINE

//        val mv = MarkerViewStyle(context, R.layout.custom_marker_view)
//        mv.chartView = chartG
//        chartG.marker = mv

        //Limit Lines
        val highLevelG = LimitLine(180f, "za wysokie>180")
        highLevelG.lineWidth = 1f
        highLevelG.lineColor = Color.rgb(247, 50, 87)
        highLevelG.enableDashedLine(10f, 10f, 0f)
        highLevelG.labelPosition = LimitLabelPosition.RIGHT_TOP
        highLevelG.textSize = 10f

        val lowLevelG = LimitLine(70f, "za niskie>70")
        lowLevelG.lineWidth = 1f
        lowLevelG.lineColor = Color.rgb(230, 100, 63)
        lowLevelG.enableDashedLine(10f, 10f, 0f)
        lowLevelG.labelPosition = LimitLabelPosition.RIGHT_TOP
        lowLevelG.textSize = 10f

        //axis
        val xAxis = chartG.xAxis
        val yAxis = chartG.axisLeft
        val vAR = chartG.axisRight
        vAR.isEnabled = false
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.labelCount = 8
        xAxis.setDrawGridLinesBehindData(false)
        yAxis.setDrawGridLinesBehindData(false)
        xAxis.setDrawAxisLine(true)
        yAxis.setDrawAxisLine(true)
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 400f

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(false)
        xAxis.setDrawLimitLinesBehindData(false)

        // add limit lines
        yAxis.addLimitLine(highLevelG)
        yAxis.addLimitLine(lowLevelG)

        //drawing x axis-format
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return if (value == 0f) "0" + value.toInt().toString() + ":00"
                else value.toInt().toString() + ":00"
            }
        }

        val entriesMedian: ArrayList<Entry> = ArrayList()
        val entries90: ArrayList<Entry> = ArrayList()
        val entries75: ArrayList<Entry> = ArrayList()
        val entries25: ArrayList<Entry> = ArrayList()
        val entries10: ArrayList<Entry> = ArrayList()
        val empty: ArrayList<Entry> = ArrayList()
        val glikemiaData = LineData()
        var currentHour = ""


        // creates Entries
        for (i in 0 ..  23){
            if (i in 0..9) currentHour = currentMonth.substring(0,8)+ "% 0" + i.toString() + "%"
            if (i > 9) currentHour = currentMonth.substring(0,8)+ "% " + i.toString() + "%"
            val hour = i.toFloat()
            var medianPerHour = appViewModel.showMedianPerHourForThisMonth(currentHour)
            if (medianPerHour != 0.0) {
                entriesMedian.add(Entry(hour, medianPerHour.toFloat()))
                entries90.add(Entry(hour, (medianPerHour*1.8).toFloat()))
                entries10.add(Entry(hour, (medianPerHour*0.2).toFloat()))
            }
            empty.add(Entry(hour, 100f))
            var upperIQR = appViewModel.showIQRupper(currentHour)
            if (upperIQR != 0.0) entries75.add(Entry(hour, (upperIQR).toFloat()))
            var lowerIQR = appViewModel.showIQRlower(currentHour)
            if (lowerIQR != 0.0) entries25.add(Entry(hour, (lowerIQR).toFloat()))
        }

        val setMedian = LineDataSet(entriesMedian, "mediana 50%")
        setMedian.axisDependency = YAxis.AxisDependency.LEFT
        setMedian.color = Color.rgb(55, 99, 190)
        setMedian.setCircleColor(Color.rgb(55, 99, 190))
        setMedian.setDrawCircles(false)
        setMedian.lineWidth = 2.5f
        setMedian.setDrawCircleHole(false)
        setMedian.mode = LineDataSet.Mode.CUBIC_BEZIER
        setMedian.setDrawValues(false)
        setMedian.valueTextSize = 10f
        setMedian.valueTextColor = Color.rgb(55, 99, 190)

        val set = LineDataSet(empty, "")
        set.enableDashedLine(0f,0f,0f)
        set.setDrawCircles(false)
        set.setDrawValues(false)
        set.color = Color.TRANSPARENT

        val set90 = LineDataSet(entries90, "90%")
        val set75 = LineDataSet(entries75, "IQR75%")
        val set25 = LineDataSet(entries25, "IQR25%")
        val set10 = LineDataSet(entries10, "10%")

        set90.axisDependency = YAxis.AxisDependency.LEFT
        set90.color = Color.RED
        set90.setCircleColor(Color.rgb(55, 99, 190))
        set90.setDrawCircles(false)
        set90.lineWidth = 0f
        set90.setDrawCircleHole(false)
        set90.mode = LineDataSet.Mode.CUBIC_BEZIER
        set90.setDrawValues(false)
        set90.fillAlpha = 255
        set90.setDrawFilled(true)
        set90.fillColor = Color.WHITE
        set90.fillFormatter = IFillFormatter { _, _ -> chartG.axisLeft.axisMaximum }

        set75.axisDependency = YAxis.AxisDependency.LEFT
        set75.color = Color.rgb(55, 99, 190)
        set75.setCircleColor(Color.rgb(55, 99, 190))
        set75.setDrawCircles(false)
        set75.lineWidth = 0f
        set75.setDrawCircleHole(false)
        set75.mode = LineDataSet.Mode.CUBIC_BEZIER
        set75.setDrawValues(false)
        set75.fillAlpha = 100
        set75.setDrawFilled(true)
        set75.fillColor = Color.WHITE
        set75.fillFormatter = IFillFormatter { _, _ -> chartG.axisLeft.axisMaximum }

        set25.axisDependency = YAxis.AxisDependency.LEFT
        set25.color = Color.rgb(55, 99, 190)
        set25.setCircleColor(Color.rgb(55, 99, 190))
        set25.setDrawCircles(false)
        set25.lineWidth = 0f
        set25.setDrawCircleHole(false)
        set25.mode = LineDataSet.Mode.CUBIC_BEZIER
        set25.setDrawValues(false)
        set25.fillAlpha = 100
        set25.setDrawFilled(true)
        set25.fillColor = Color.WHITE
        set25.fillFormatter = IFillFormatter { _, _ -> chartG.axisLeft.axisMinimum }


        set10.axisDependency = YAxis.AxisDependency.LEFT
        set10.color = Color.RED
        set10.setCircleColor(Color.rgb(55, 99, 190))
        set10.setDrawCircles(false)
        set10.lineWidth = 0f
        set10.setDrawCircleHole(false)
        set10.mode = LineDataSet.Mode.CUBIC_BEZIER
        set10.setDrawValues(false)
        set10.fillAlpha = 255
        set10.setDrawFilled(true)
        set10.fillColor = Color.WHITE
        set10.fillFormatter = IFillFormatter { _, _ -> chartG.axisLeft.axisMinimum }


        if (entriesMedian.size < 4 ) {
            chartG.setGridBackgroundColor(Color.WHITE)

            setMedian.setDrawCircles(true)

            set10.setDrawCircles(false)
            set10.setDrawValues(false)
            set10.color = Color.TRANSPARENT

            set25.setDrawCircles(false)
            set25.setDrawValues(false)
            set25.color = Color.TRANSPARENT

            set75.setDrawCircles(false)
            set75.setDrawValues(false)
            set75.color = Color.TRANSPARENT

            set90.setDrawCircles(false)
            set90.setDrawValues(false)
            set90.color = Color.TRANSPARENT

        }

        glikemiaData.addDataSet(setMedian)
        glikemiaData.addDataSet(setMedian)
        glikemiaData.addDataSet(set)
        glikemiaData.addDataSet(set75)
        glikemiaData.addDataSet(set25)
        glikemiaData.addDataSet(set90)
        glikemiaData.addDataSet(set10)

        chartG.invalidate()
        chartG.data = glikemiaData
    }








    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    class Value(value: Float, time: Float)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RaportFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RaportFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onNothingSelected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
