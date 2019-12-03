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
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alemz.dailydiabetic1.*
import com.alemz.dailydiabetic1.adapters.GlikemiaAdapter
import com.alemz.dailydiabetic1.adapters.InsulinAdapter
import com.alemz.dailydiabetic1.adapters.MedAdapter
import com.alemz.dailydiabetic1.adapters.PressureAdapter
import com.alemz.dailydiabetic1.data.entities.*
import java.text.SimpleDateFormat

import java.util.*
import javax.inject.Inject

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
    private lateinit var  selectedTime: String

    private lateinit var nothingG: TextView
    private lateinit var nothingI: TextView
    private lateinit var nothingBP: TextView
    private lateinit var nothingMed: TextView


    // viewModel
    private val appViewModel: AppViewModel by lazy{ViewModelProviders.of(this).get(AppViewModel::class.java)}
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


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
            intent.putExtra( "selectedTime", selectedTime)
           // intent.putStringArrayListExtra("lista", list)
            startActivity(intent)
            //Navigation.findNavController(view).navigate(R.id.action_calendarFragment_to_addMeasurementFragment)
        }


        //calendar
        val cv = view.findViewById<CalendarView>(R.id.calendarView)
        //selectedTime = getInstanceTime()

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
        selectedTime = getInstanceTime()
        Toast.makeText(context, selectedTime, Toast.LENGTH_SHORT).show()

        DrawHistoryCalendar()



        cv.setOnDateChangeListener{
                _, year, month, dayOfMonth->
            val min = Calendar.getInstance().get(Calendar.MINUTE)
            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val chosen = Date(year-1900,month,dayOfMonth, hour, min, 0)
            selectedTime = formatter.format(chosen)
            Toast.makeText(context, selectedTime, Toast.LENGTH_SHORT).show()
            DrawHistoryCalendar()

        }



        return view
    }


    private fun getInstanceTime(): String {
        val d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val m = Calendar.getInstance().get(Calendar.MONTH)
        val y = Calendar.getInstance().get(Calendar.YEAR) - 1900
        val min = Calendar.getInstance().get(Calendar.MINUTE)
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val today = Date(y,m,d,hour,min,0)
        selectedTime = formatter.format(today)
        return selectedTime
    }

    private fun DrawHistoryCalendar() {
        var k = formatter.parse(selectedTime).time
        Toast.makeText(context, k.toString(), Toast.LENGTH_LONG).show()
        selectedTime = "%"+selectedTime.substring(0,10)+"%"
        appViewModel.getGlikemiaForChosenDate(selectedTime)
            .observe(this, Observer<List<GlikemiaEntity>> { t ->
                adapterG.setGlikemia(t!!)
                if (adapterG.itemCount > 0) nothingG.isVisible = false
                if (adapterG.itemCount == 0) nothingG.isVisible = true
            })
        appViewModel.getBPForChosenDate(selectedTime)
            .observe(this, Observer<List<PressureEntity>> { t ->
                adapterBP.setPressure(t!!)
                if (adapterBP.itemCount > 0) nothingBP.isVisible = false
                if (adapterBP.itemCount == 0) nothingBP.isVisible = true
            })
        appViewModel.getInsulinForChosenDate(selectedTime)
            .observe(this, Observer<List<InsulinEntity>> { t ->
                adapterI.setInsulin(t!!)
                if (adapterI.itemCount > 0) nothingI.isVisible = false
                if (adapterI.itemCount == 0) nothingI.isVisible = true
            })
        appViewModel.getMedForChosenDate(selectedTime)
            .observe(this, Observer<List<MedicineEntity>> { t ->
                adapterM.setMed(t!!)
                if (adapterM.itemCount > 0) nothingMed.isVisible = false
                if (adapterM.itemCount == 0) nothingMed.isVisible = true
            })
        selectedTime = getInstanceTime()
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
