package com.alemz.dailydiabetic1.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alemz.dailydiabetic1.AppViewModel
import com.alemz.dailydiabetic1.R
import com.alemz.dailydiabetic1.data.entities.ProfileEntity
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    //val db = AppDataBase(context)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var name: TextView
    private lateinit var type: TextView
    private lateinit var wage: TextView
    private lateinit var height: TextView
    private lateinit var email: TextView
    private lateinit var maxDose: TextView
    private lateinit var doctorEmail: TextView


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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
        view.findViewById<Button>(R.id.buttonEditProfile).setOnClickListener {
        }

        // viewModel
        val appViewModel: AppViewModel by lazy{ ViewModelProviders.of(this).get(AppViewModel::class.java)}
        //appViewModel.insertProfile(ProfileEntity(1, "Monika", "Zioło", "abc@gmail.com","2",52.0,1.62, 350.0, "doctor@gmail.com"))
        //list = listOf(element = appViewModel.getProfile())
        val profile = appViewModel.getOneProfile("abc@gmail.com")
////        var profile: ProfileEntity = appViewModel.getProfile()[0]

        name = view.findViewById(R.id.textName)
        type = view.findViewById(R.id.textTypeDiabetic)
        wage = view.findViewById(R.id.textWeight)
        height = view.findViewById(R.id.textHeight)
        email = view.findViewById(R.id.textEmail)
        maxDose = view.findViewById(R.id.textMaxInsulin)
        doctorEmail = view.findViewById(R.id.textDoctor)

        name.text = profile.first_name
        type.text = profile.type_of_diabetes
        email.text = profile.email
        doctorEmail.text = profile.doctor_email
        height.text = profile.height.toString()
        maxDose.text = profile.max_dose_of_insulin.toString()
        wage.text = profile.height.toString()

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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
