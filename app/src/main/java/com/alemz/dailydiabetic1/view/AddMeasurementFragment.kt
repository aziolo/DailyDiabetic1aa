package com.alemz.dailydiabetic1.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alemz.dailydiabetic1.AppViewModel
import com.alemz.dailydiabetic1.R
import com.alemz.dailydiabetic1.data.entities.*
import java.util.*
import javax.inject.Inject
import kotlin.reflect.typeOf

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddMeasurementFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddMeasurementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddMeasurementFragment : Fragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var datePicker: Button
    private lateinit var timePicker: Button
    private lateinit var save: Button

    private lateinit var choserDate: DatePickerDialog
    private lateinit var choserTime: TimePickerDialog
    private lateinit var date: TextView
    private lateinit var time: TextView

    private lateinit var inputGlikemia: EditText
    private lateinit var inputInsulin: EditText
    private lateinit var inputSystolic: EditText
    private lateinit var inputDiastolic: EditText
    private lateinit var inputPulse: EditText
    private lateinit var inputNameMed: EditText
    private lateinit var inputDoseMed: EditText
    private lateinit var spinnerInsulin: Spinner
    private lateinit var spinnerMed: Spinner

    private lateinit var glikemiaCheck: CheckBox
    private lateinit var insulinCheck: CheckBox
    private lateinit var pressureCheck: CheckBox
    private lateinit var medicineCheck: CheckBox

    private lateinit var glikemiaCV: CardView
    private lateinit var insulinCV: CardView
    private lateinit var pressureCV: CardView
    private lateinit var medicineCV: CardView

    private lateinit var listMed: List<String>
    private var medName: String = ""
    private var medDose: Double = 1.0

    @Inject lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    val appViewModel: AppViewModel by lazy{ ViewModelProviders.of(this).get(AppViewModel::class.java)}



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
        val view = inflater.inflate(R.layout.fragment_add_measurement, container, false)


        datePicker = view.findViewById(R.id.button_date)
        timePicker = view.findViewById(R.id.button_time)
        save = view.findViewById(R.id.button_save_measure)

        date = view.findViewById(R.id.text_show_date)
        time = view.findViewById(R.id.text_show_time)

        //date.text = intent

        glikemiaCheck = view.findViewById(R.id.CB_glikemia)
        insulinCheck = view.findViewById(R.id.CB_insulin)
        pressureCheck = view.findViewById(R.id.CB_pressure)
        medicineCheck = view.findViewById(R.id.CB_medicine)

        glikemiaCV = view.findViewById(R.id.glikemia_CV)
        glikemiaCV.isVisible = false
        insulinCV = view.findViewById(R.id.insulin_CV)
        insulinCV.isVisible = false
        pressureCV = view.findViewById(R.id.pressure_CV)
        pressureCV.isVisible = false
        medicineCV = view.findViewById(R.id.medicines_CV)
        medicineCV.isVisible = false

        inputGlikemia = view.findViewById(R.id.ET_glikemia_input)
        inputInsulin = view.findViewById(R.id.ET_insulin_input)
        inputSystolic = view.findViewById(R.id.ET_systolicBP_input)
        inputPulse = view.findViewById(R.id.ET_pulse_input)
        inputDiastolic = view.findViewById(R.id.ET_diastolicBP_input)
        inputNameMed = view.findViewById(R.id.ET_med_name_input)
        inputNameMed.isVisible = false
        inputDoseMed = view.findViewById(R.id.ET_dose_med_input)
        spinnerInsulin = view.findViewById(R.id.spinner_type_insulin)
        spinnerInsulin.onItemSelectedListener = this
        spinnerMed = view.findViewById(R.id.spinner_medicines)
        spinnerMed.onItemSelectedListener = this
        listMed = appViewModel.getNamesOfMeds()
        (listMed as MutableList<String>).plusAssign("inny")







        // spinner insulin
        ArrayAdapter.createFromResource(
            context,
            R.array.type_insulin,
            android.R.layout.simple_spinner_item
        ).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerInsulin.adapter = adapter
        }

        val medAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, listMed)
        spinnerMed.adapter = medAdapter
        spinnerMed.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                medName = spinnerMed.selectedItem.toString()
                if(medName =="inny"){
                    inputNameMed.isVisible = true

                    //Toast.makeText(context, medName, Toast.LENGTH_SHORT).show()
                    //medName = inputNameMed.text.toString()
                }
                if(medName.equals("inny").not()) {
                    inputNameMed.isVisible = false
                    //medName = spinnerMed.selectedItem.toString()
                    //Toast.makeText(context, medName, Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(context, medName, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }



        datePicker.setOnClickListener {
            val c = java.util.Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            choserDate = DatePickerDialog(context,DatePickerDialog.OnDateSetListener { view, y, m, d -> date.text="$d.${m+1}.$y" },year, month, day )
            choserDate.show()
        }


        timePicker.setOnClickListener {

            val c = java.util.Calendar.getInstance()
            var hour = c.get(Calendar.HOUR)
            var minute = c.get(Calendar.MINUTE)
           // if(minute.length == 1) (minute = "0" + minute)
            //selectedTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()+ "." +  Calendar.getInstance().get(Calendar.MINUTE).toString()
            //selectedTime = hour + "." + minute
            if (minute<10) choserTime = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener(function= { view, h, m -> time.text="$h:0$m" }), hour, minute,true)
            else choserTime = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener(function= { view, h, m -> time.text="$h:$m" }), hour, minute,true)
            choserTime.show()

            ///var inputTypeInsulin = spinnerInsulin.selectedItem.toString()
            //Toast.makeText(context, "$inputTypeInsulin", Toast.LENGTH_SHORT).show()
        }

        save.setOnClickListener {
            if(glikemiaCheck.isChecked){
                var amountG = inputGlikemia.text.toString().toDouble()
                if(inputGlikemia.text.isNotEmpty()) {
                    createGlikemiaEntity(amountG, date.text.toString(), time.text.toString())
                    // Toast.makeText(context, "$amountG amou", Toast.LENGTH_SHORT).show()

                }
            }

            else{}

            if(insulinCheck.isChecked){
                var typeI = spinnerInsulin.selectedItem.toString()
                var amountI = inputInsulin.text.toString().toDouble()
                if(inputInsulin.text.isNotEmpty()){
                    createInsulinEntity(amountI, typeI, date.text.toString(), time.text.toString())
                    Toast.makeText(context, "$amountI amou", Toast.LENGTH_SHORT).show()
                }
            }
            else{}

            if (pressureCheck.isChecked){
                var levelSystolic = inputSystolic.text.toString().toInt()
                var levelDiastolic = inputDiastolic.text.toString().toInt()
                var pulse = inputPulse.text.toString().toInt()

                if(inputSystolic.text.isNotEmpty() && inputDiastolic.text.isNotEmpty() && inputPulse.text.isNotEmpty()){
                    createPressureEntity(levelSystolic, levelDiastolic, pulse, date.text.toString(), time.text.toString())
                    //Toast.makeText(context, "$levelDiastolic amou", Toast.LENGTH_SHORT).show()
                }
            }
            else{}
            if(medicineCheck.isChecked){
                if(inputDoseMed.text.isNotEmpty()) {
                    medDose = inputDoseMed.text.toString().toDouble()
                    if(inputNameMed.isVisible) medName = inputNameMed.text.toString()
                    createMedicineEntity(medName, medDose, date.text.toString(), time.text.toString())
                    //Toast.makeText(context, medName+medDose, Toast.LENGTH_SHORT).show()
                }

            }
            else{}


        }

        glikemiaCheck.setOnClickListener {
            // glikemiaCV.isVisible = false
            if (glikemiaCheck.isChecked) glikemiaCV.isVisible = true
            if (!glikemiaCheck.isChecked) glikemiaCV.isVisible = false
        }

        insulinCheck.setOnClickListener {
            //insulinCV.isVisible = false
            if (insulinCheck.isChecked) insulinCV.isVisible = true
            if (!insulinCheck.isChecked) insulinCV.isVisible = false

        }

        pressureCheck.setOnClickListener {
           // pressureCV.isVisible = false
            if(pressureCheck.isChecked) pressureCV.isVisible = true
            if(!pressureCheck.isChecked) pressureCV.isVisible = false

        }

        medicineCheck.setOnClickListener {
            //medicineCV.isVisible = false
            if (medicineCheck.isChecked) medicineCV.isVisible = true
            if (medicineCheck.isChecked.not()) medicineCV.isVisible = false
        }


        return view
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
         * @return A new instance of fragment AddMeasurementFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddMeasurementFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



    fun createGlikemiaEntity(amount: Double, date: String, time: String){
        val amount = amount
        val date = date
        val time = time
        val uuid: UUID = UUID.randomUUID()
        val id1: Long = uuid.mostSignificantBits
        val id2: Long = uuid.leastSignificantBits
        val item:GlikemiaEntity = GlikemiaEntity(id1,id2 ,amount, date, time)

        appViewModel.insertGlikemia(item)

    }

    fun createInsulinEntity(amount: Double, type: String, date: String, time: String){
        val amount = amount
        val type = type
        val date = date
        val time = time
        val uuid: UUID = UUID.randomUUID()
        val id1: Long = uuid.mostSignificantBits
        val id2: Long = uuid.leastSignificantBits
        val item:InsulinEntity = InsulinEntity(id1, id2, amount, type, date, time)

        appViewModel.insertInsulin(item)

    }

    fun createPressureEntity(levelSystolic: Int, levelDiastolic: Int, pulse: Int, date: String, time: String){
        val levelSystolic = levelSystolic
        val levelDiastolic = levelDiastolic
        val pulse = pulse
        val date = date
        val time = time

        val uuid: UUID = UUID.randomUUID()
        val id1: Long = uuid.mostSignificantBits
        val id2: Long = uuid.leastSignificantBits
        val item:PressureEntity = PressureEntity(id1, id2, levelSystolic, levelDiastolic, pulse, date, time)

        appViewModel.insertBP(item)

    }

    fun createMedicineEntity(name:String, dose: Double, date: String, time: String){
        val name = name
        val dose = dose
        val date = date
        val time = time
        val uuid: UUID = UUID.randomUUID()
        val id1: Long = uuid.mostSignificantBits
        val id2: Long = uuid.leastSignificantBits
        val item:MedicineEntity = MedicineEntity(id1, id2, name, dose, date, time)
        appViewModel.insertMed(item)
        Toast.makeText(context, date+time, Toast.LENGTH_SHORT).show()
        ///////////////////
    }

    fun createPeriodicTestsEntity(name:String, result: Double, date: String, time: String){
        val name = name
        val result = result
        val date = date

        val uuid: UUID = UUID.randomUUID()
        val id1: Long = uuid.mostSignificantBits
        val id2: Long = uuid.leastSignificantBits
        val item:PeriodicTestsEntity = PeriodicTestsEntity(id1, id2, name, result,date)

        ////////////////////////
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        if (view is Spinner){
//            when(view){
//                spinnerMed ->{
//
//                    medName = spinnerMed.selectedItem.toString()
//                    medDose = inputDoseMed.text.toString().toDouble()
//                    if(medName.equals("inny")) {
//                        inputNameMed.isVisible = true
//                        var name2 = inputNameMed.text.toString()
//                        //createMedicineEntity(name2, medDose, date.text.toString(), time.text.toString())
//                    }
//                    if(medName.equals("inny").not()) {
//                        inputNameMed.isVisible = false
//                        //createMedicineEntity(name, dose, date.text.toString(), time.text.toString())
//                    }
//                    Toast.makeText(context, medName+ {medDose.toString()} , Toast.LENGTH_SHORT).show()
//                }
//            }
//
//
//        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

//         fun onCheckBoxClicked(view: View){
//         if(view is CheckBox){
//             val checked: Boolean = view.isChecked
//
//             when(view.id){
//                 R.id.CB_glikemia ->{
//                     if(checked) glikemiaCV.isVisible = true
//                     else glikemiaCV.isVisible = false
//                 }
//
//                 R.id.CB_insulin ->{
//                     if(checked) insulinCV.isVisible = true
//                     else insulinCV.isVisible = false
//
//                 }
//                 R.id.CB_pressure ->{
//                     if(checked) pressureCV.isVisible = true
//                     else pressureCV.isVisible = false
//
//                 }
//                 R.id.CB_medicine ->{
//                     if(checked) medicineCV.isVisible = true
//                     else medicineCV.isVisible = false
//
//                 }
//             }
//         }
//     }

}




