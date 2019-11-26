package com.alemz.dailydiabetic1.view

import android.content.Context
import android.graphics.Color
import android.graphics.Color.WHITE
import android.graphics.DashPathEffect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alemz.dailydiabetic1.AppViewModel
import com.alemz.dailydiabetic1.MarkerViewStyle
import com.alemz.dailydiabetic1.R
import com.alemz.dailydiabetic1.data.entities.GlikemiaEntity
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
import com.github.mikephil.charting.utils.Utils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
        val t = view.findViewById<TextView>(R.id.texhahaha)


        //Toast.makeText(context, calendar.minDate.toString(), Toast.LENGTH_LONG)
        val c = Calendar.getInstance()
        currentMonth = "%"+formMonth.format(c.time)+"%"
        countDaysInMounth = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        monthTxt.text = months[c.get(Calendar.MONTH)] +" "+ c.get(Calendar.YEAR).toString()
        cvbp.isVisible = false
        cvi.isVisible = false
        cvg.isVisible = true

        drawChartGlikemia(view)
        drawChartBP(view)
        drawSecondInsulin(view)



        next.setOnClickListener {
            c.add(Calendar.MONTH, 1)
            val m = c.get(Calendar.MONTH)
            monthTxt.text = "${months[m]}"+" "+"${c.get(Calendar.YEAR)}"
            t.text = c.getActualMaximum(Calendar.DAY_OF_MONTH).toString()
            currentMonth = "%"+formMonth.format(c.time)+"%"
            countDaysInMounth = c.getActualMaximum(Calendar.DAY_OF_MONTH)
            drawChartBP(view)
            drawChartGlikemia(view)
            //drawChartInsulin(view)
            drawSecondInsulin(view)
        }
        previous.setOnClickListener {
            c.add(Calendar.MONTH, -1)
            val date = c.time
            val m = c.get(Calendar.MONTH)
            monthTxt.text = "${months[m]}"+" "+"${c.get(Calendar.YEAR)}"
            t.text = formMonth.format(date)
            currentMonth = "%"+formMonth.format(c.time)+"%"
            countDaysInMounth = c.getActualMaximum(Calendar.DAY_OF_MONTH)

            drawChartGlikemia(view)
            drawChartBP(view)
           // drawChartInsulin(view)
            drawSecondInsulin(view)


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



    private fun drawChartGlikemia(view: View) {

        //style
        chartG = view.findViewById(R.id.glikemiaChart)
        chartG.setBackgroundColor(WHITE)
        chartG.description.isEnabled = false
        chartG.setTouchEnabled(true)
        chartG.setDrawGridBackground(false)
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

        val valuesG: ArrayList<Entry> = ArrayList()
        val glikemiaData = LineData()

        appViewModel.getGlikemiaForChosenDate(currentMonth).observe(this, Observer<List<GlikemiaEntity>> { t ->
            val list = ArrayList(t)

            for (i in 0 until list.size) {
                val amount = list[i].amount.toFloat()
                val date = formatter.parse(list[i].date).time

                valuesG.add(Entry(date.toFloat(), amount))


            }
            val set1 = LineDataSet(valuesG, "glikemia")


            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { _, _ -> chartG.axisLeft.axisMinimum }
            if (Utils.getSDKInt() >= 18) { // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(context!!, R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }


            //set drawing style
            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.enableDashedLine(10f, 5f, 0f)
            set1.color = Color.BLACK
            set1.lineWidth = 1f
            set1.setDrawCircleHole(false)
            set1.setCircleColor(Color.BLACK)
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)

            set1.mode = LineDataSet.Mode.LINEAR
            set1.setDrawValues(true)
            set1.valueTextSize = 10f
            set1.valueTextColor = Color.rgb(0, 0, 0)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            //axis
            val xAxis = chartG.xAxis
            val yAxis = chartG.axisLeft
            xAxis.position = XAxisPosition.BOTTOM
            xAxis.labelCount = countDaysInMounth
            xAxis.mAxisMaximum = countDaysInMounth.toFloat()

            xAxis.setDrawAxisLine(true)
            yAxis.setDrawAxisLine(true)

            //drawing x axis-format
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {

                    var mDataFormat = SimpleDateFormat("dd")
                    var mDate: Date
                    val original = value.toLong()
                    mDate = Date(original)
                    return mDataFormat.format(mDate)
                }
            }

            //marker
            val mv = MarkerViewStyle(context, R.layout.custom_marker_view)
            mv.chartView = chartG
            chartG.marker = mv


            //Limit Lines
            val highLevelG = LimitLine(126f, "niebezpieczne >125")
            highLevelG.lineWidth = 2f
            highLevelG.lineColor =Color.rgb(247,50,87)
            highLevelG.enableDashedLine(10f, 10f, 0f)
            highLevelG.labelPosition = LimitLabelPosition.RIGHT_TOP
            highLevelG.textSize = 10f

            val lowLevelG = LimitLine(100f, "ostrzeżenie >100")
            lowLevelG.lineWidth = 2f
            lowLevelG.lineColor =Color.rgb(230,100,63)
            lowLevelG.enableDashedLine(10f, 10f, 0f)
            lowLevelG.labelPosition = LimitLabelPosition.RIGHT_TOP
            lowLevelG.textSize = 10f


            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true)
            xAxis.setDrawLimitLinesBehindData(true)
            // add limit lines
            yAxis.addLimitLine(highLevelG)
            yAxis.addLimitLine(lowLevelG)
            //xAxis.addLimitLine(llXAxis);


            glikemiaData.addDataSet(set1)
            chartG.data = glikemiaData
            chartG.invalidate()

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

        settleDataSet.axisDependency = YAxis.AxisDependency.LEFT
        settleDataSet.color = Color.rgb(55, 99, 190)
        settleDataSet.setDrawValues(false)
        settleDataSet.valueTextSize = 10f
        settleDataSet.valueTextColor = Color.rgb(55, 99, 190)

        foodDataSet.axisDependency = YAxis.AxisDependency.LEFT
        foodDataSet.color = Color.rgb(61, 165, 255)
        foodDataSet.setDrawValues(false)
        foodDataSet.valueTextSize = 10f
        //foodDataSet.formSize = 100000000000000000f
        foodDataSet.valueTextColor = Color.rgb(61, 165, 255)

        //axis
        var xAxis = chartI.xAxis
        var yAxis = chartI.axisLeft

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













    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

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
