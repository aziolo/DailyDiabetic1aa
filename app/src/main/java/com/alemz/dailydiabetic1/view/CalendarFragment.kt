package com.alemz.dailydiabetic1.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alemz.dailydiabetic1.*
import com.alemz.dailydiabetic1.data.entities.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.time.MonthDay
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CalendarFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var listener: OnFragmentInteractionListener? = null

    private val adapterG = GlikemiaAdapter()
    private val adapterBP = PressureAdapter()
    private val adapterI = InsulinAdapter()
    private val adapterM = MedAdapter()

    @Inject lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    lateinit var  selectedDate: String
    lateinit var selectedTime: String
    lateinit var list: ArrayList<String>

    private lateinit var nothingG: TextView
    private lateinit var nothingI: TextView
    private lateinit var nothingBP: TextView
    private lateinit var nothingMed: TextView


    // viewModel
    val appViewModel: AppViewModel by lazy{ViewModelProviders.of(this).get(AppViewModel::class.java)}


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
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_calendar, container, false)

        view.findViewById<Button>(R.id.buttonAddNewMeasurement).setOnClickListener {
            val intent = Intent(context, NewMeasureActivity::class.java)
            intent.putStringArrayListExtra("lista", list)
            //intent.putS

            //intent.putExtra(date, selectedDate)
            //intent.putExtras(intent.putExtra(date, selectedDate))
            //Toast.makeText(context, list , Toast.LENGTH_SHORT).show()
            startActivity(intent)
            //Navigation.findNavController(view).navigate(R.id.action_calendarFragment_to_addMeasurementFragment)

        }


        //calendar
        val cv = view.findViewById<CalendarView>(R.id.calendarView)

        selectedDate = getInstanceDate()
        selectedTime = getInstanceTime()

        Toast.makeText(context, selectedDate+ selectedTime, Toast.LENGTH_SHORT).show()
        list = arrayListOf()
        list.add(0,selectedDate)
        list.add(1,selectedTime)

        //recyclerViews
        val recyclerViewG = view.findViewById<RecyclerView>(R.id.glikemiaRecyclerView)
        recyclerViewG.layoutManager = LinearLayoutManager(context)
        recyclerViewG.adapter = adapterG

        val recyclerViewBP = view.findViewById<RecyclerView>(R.id.pressureRecyclerView)
        recyclerViewBP.layoutManager = LinearLayoutManager(context)
        recyclerViewBP.adapter = adapterBP

        val recyclerViewI = view.findViewById<RecyclerView>(R.id.insulinRecyclerView)
        recyclerViewI.layoutManager = LinearLayoutManager(context)
        recyclerViewI.adapter = adapterI

        val recyclerViewM = view.findViewById<RecyclerView>(R.id.medRecyclerView)
        recyclerViewM.layoutManager = LinearLayoutManager(context)
        recyclerViewM.adapter = adapterM

        nothingG = view.findViewById(R.id.nothing_glikemia_text)
        nothingI = view.findViewById(R.id.nothing_insulin_text)
        nothingBP = view.findViewById(R.id.nothing_pressure_text)
        nothingMed = view.findViewById(R.id.nothing_med_text)

        //for begining- when user has not chosen the date yet.
        DrawHistoryCalebdar()



        cv.setOnDateChangeListener{
                _, year, month, dayOfMonth->  selectedDate = "$dayOfMonth.${month+1}.$year"

            var day = dayOfMonth.toString()
            var mon = (month+1).toString()
            if(day.length == 1) day = "0" + day
            if(mon.length == 1) mon = "0" + mon
            selectedDate = day + "." + mon + "." + "$year"
            Toast.makeText(context, selectedDate, Toast.LENGTH_SHORT).show()

            selectedTime = getInstanceTime()
            DrawHistoryCalebdar()

            list[0] = selectedDate
            list[1] = selectedTime
        }


        return view
    }

    private fun getInstanceTime() : String {
        var min = Calendar.getInstance().get(Calendar.MINUTE).toString()
        var hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
        if (min.length == 1) min = "0" + min
        val time =  hour + "." + min
        return time
    }

    private fun getInstanceDate(): String {
        var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        var mon: String = month.toString()
        if (day.length == 1) day = "0" + day
        if (mon.length == 1) mon = "0" + mon
        val today = day + "." + mon + "." + Calendar.getInstance().get((Calendar.YEAR))
        return today
    }

    private fun DrawHistoryCalebdar() {
        appViewModel.getGlikemiaForChosenDate(selectedDate)
            .observe(this, Observer<List<GlikemiaEntity>> { t ->
                adapterG.setGlikemia(t!!)
                if (adapterG.itemCount > 0) nothingG.isVisible = false
                if (adapterG.itemCount == 0) nothingG.isVisible = true
            })
        appViewModel.getBPForChosenDate(selectedDate)
            .observe(this, Observer<List<PressureEntity>> { t ->
                adapterBP.setPressure(t!!)
                if (adapterBP.itemCount > 0) nothingBP.isVisible = false
                if (adapterBP.itemCount == 0) nothingBP.isVisible = true
            })
        appViewModel.getInsulinForChosenDate(selectedDate)
            .observe(this, Observer<List<InsulinEntity>> { t ->
                adapterI.setInsulin(t!!)
                if (adapterI.itemCount > 0) nothingI.isVisible = false
                if (adapterI.itemCount == 0) nothingI.isVisible = true
            })
        appViewModel.getMedForChosenDate(selectedDate)
            .observe(this, Observer<List<MedicineEntity>> { t ->
                adapterM.setMed(t!!)
                if (adapterM.itemCount > 0) nothingMed.isVisible = false
                if (adapterM.itemCount == 0) nothingMed.isVisible = true
            })
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
         * @return A new instance of fragment CalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
