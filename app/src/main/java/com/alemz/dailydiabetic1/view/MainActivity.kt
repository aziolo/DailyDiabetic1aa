package com.alemz.dailydiabetic1.view
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.alemz.dailydiabetic1.*
import com.alemz.dailydiabetic1.data.AppDataBase
import com.alemz.dailydiabetic1.data.entities.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), FirstFragment.OnFragmentInteractionListener,
    SecondFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener, CalendarFragment.OnFragmentInteractionListener,
    RaportFragment.OnFragmentInteractionListener, AddMeasurementFragment.OnFragmentInteractionListener{

    private lateinit var navController: NavController
    private lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        val db = AppDataBase(this)
        //val db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "baza.db").build()
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)



        //ONLY FOR TESTS !!!!
        appViewModel.insertProfile(ProfileEntity(1, 2,"Monika", "Zioło", "abc@gmail.com","2",52.0,1.62, 350.0, "doctor@gmail.com", 1800))

        appViewModel.insertGlikemia(GlikemiaEntity(1,1,100.0,"1.11.2019", "13:13"))
        appViewModel.insertGlikemia(GlikemiaEntity(2,1,120.0,"11.11.2019", "14:13"))
        appViewModel.insertGlikemia(GlikemiaEntity(3,1,130.0,"12.11.2019", "15:13"))
        appViewModel.insertGlikemia(GlikemiaEntity(4,1,140.0,"12.11.2019", "16:13"))
        appViewModel.insertGlikemia(GlikemiaEntity(5,1,150.0,"13.11.2019", "15:13"))
        appViewModel.insertGlikemia(GlikemiaEntity(6,1,160.0,"13.11.2019", "16:13"))
        appViewModel.insertGlikemia(GlikemiaEntity(7,1,170.0,"14.11.2019", "15:13"))
        appViewModel.insertGlikemia(GlikemiaEntity(8,1,180.0,"14.11.2019", "16:13"))

        appViewModel.insertBP(PressureEntity(1,1,120,80,100, "9.11.2019", "11:53"))
        appViewModel.insertBP(PressureEntity(2,1,120,80,100, "11.11.2019", "11:54"))
        appViewModel.insertBP(PressureEntity(3,1,120,80,100, "12.11.2019", "11:55"))
        appViewModel.insertBP(PressureEntity(4,1,120,80,100, "12.11.2019", "11:56"))
        appViewModel.insertBP(PressureEntity(5,1,120,80,100, "13.11.2019", "11:57"))
        appViewModel.insertBP(PressureEntity(6,1,120,80,100, "28.11.2019", "11:58"))
        appViewModel.insertBP(PressureEntity(7,1,120,80,100, "14.11.2019", "11:59"))


        appViewModel.insertInsulin(InsulinEntity(1,1,23.0,"wyrównanie","28.11.2019", "12:53"))
        appViewModel.insertInsulin(InsulinEntity(2,1,11.0,"wyrównanie","10.11.2019", "19:53"))
        appViewModel.insertInsulin(InsulinEntity(3,1,233.0,"wyrównanie","11.11.2019", "18:53"))
        appViewModel.insertInsulin(InsulinEntity(4,1,67.0,"wyrównanie","11.11.2019", "17:53"))
        appViewModel.insertInsulin(InsulinEntity(5,1,90.0,"wyrównanie","12.11.2019", "16:53"))
        appViewModel.insertInsulin(InsulinEntity(6,1,243.0,"wyrównanie","12.11.2019", "15:53"))
        appViewModel.insertInsulin(InsulinEntity(7,1,230.0,"wyrównanie","12.11.2019", "14:53"))
        appViewModel.insertInsulin(InsulinEntity(8,1,111.0,"wyrównanie","12.11.2019", "13:53"))
        appViewModel.insertInsulin(InsulinEntity(9,1,67.0,"wyrównanie","13.11.2019", "11:53"))
        appViewModel.insertInsulin(InsulinEntity(10,1,345.0,"wyrównanie","14.11.2019", "12:53"))

        appViewModel.insertMed(MedicineEntity(1, 1,"apap", 1.0, "10.11.2019", "08:12" ))
        appViewModel.insertMed(MedicineEntity(12, 1,"ibuprom", 2.0, "10.11.2019", "08:12" ))
        appViewModel.insertMed(MedicineEntity(13, 1,"apap", 1.0, "11.11.2019", "08:12" ))
        appViewModel.insertMed(MedicineEntity(14, 1,"apap", 2.0, "11.11.2019", "08:12" ))
        appViewModel.insertMed(MedicineEntity(15, 1,"ibuprom", 1.0, "11.11.2019", "08:12" ))
        appViewModel.insertMed(MedicineEntity(16, 1,"MagB6", 2.0, "13.11.2019", "08:12" ))
        appViewModel.insertMed(MedicineEntity(17, 1,"MagB6", 1.0, "13.11.2019", "08:12" ))
        appViewModel.insertMed(MedicineEntity(18, 1,"apap", 2.0, "12.11.2019", "08:12" ))
        appViewModel.insertMed(MedicineEntity(19, 1,"ibuprom", 1.0, "14.11.2019", "08:12" ))



//           appViewModel.getAllGlicemia().observe(this, Observer<List<GlikemiaEntity>> {
//               t ->  test222.setText(t[0].amount.toString() !!)
//           })
//        }

//
//            GlobalScope.launch {
//                db.insulinDAO().insertAll(InsulinEntity(3,20.0,"wyrównanie","23.11.2019", "13:18"))
//                var dat = db.insulinDAO().getAll()
//                dat?.forEach{
//                    runOnUiThread{
//                        test222.setText(it.date+it.amount)
//
//                    }
//
//                }
//            }
//
//        }


    }

    //przycisk powrotu na gornym toolbar
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

    override fun onAttachFragment(fragment: Fragment) {

    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}

