package com.alemz.dailydiabetic1.view

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.alemz.dailydiabetic1.AppViewModel
import com.alemz.dailydiabetic1.R
import com.alemz.dailydiabetic1.data.AppDataBase
import com.alemz.dailydiabetic1.data.entities.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


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
//
//        appViewModel.insertGlikemia(GlikemiaEntity(1,1,100.0, Date(2019,11,11), Time(13,12,0)))
        var dd = Date(2019-1900,11-1,20, 11,11,11)
       val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var parsedDate: String
        parsedDate = formatter.format(dd)

//        try {
//           // d1 = sdf3.parse("Wed Mar 30 00:00:00 GMT+05:30 2016")
//            parsedDate = formatter.format(dd)
//            Toast.makeText(this, parsedDate, Toast.LENGTH_SHORT).show()
//        } catch (e: Exception) {
//            Toast.makeText(this, "hahaha", Toast.LENGTH_SHORT).show()
//            e.printStackTrace()
//
//        }
// var parseee: Date
//        try {
//            parsedDate = "2016-11-19 12:11:10"
//            parseee = formatter.parse(parsedDate)
//            Toast.makeText(this, parseee.toString(), Toast.LENGTH_SHORT).show()
//        } catch (e: Exception) {
        val check = "%"+parsedDate+"%"
        //Toast.makeText(this, Toast.LENGTH_SHORT).show()
//            e.printStackTrace()
//
//        }

        appViewModel.insertGlikemia(GlikemiaEntity(2,1,100.0,parsedDate, "12:11"))
        appViewModel.insertGlikemia(GlikemiaEntity(8,1,900.0,formatter.format(Date(2019-1900,11-1,25)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(9,1,104.0,formatter.format(Date(2019-1900,10-1,21)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(10,1,108.0,formatter.format(Date(2019-1900,10-1,22)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(11,1,100.0,formatter.format(Date(2019-1900,11-1,23)), "13,12,0"))

        appViewModel.insertGlikemia(GlikemiaEntity(3,1,80.0,formatter.format(Date(2019-1900,9-1,21)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(4,1,30.0,formatter.format(Date(2019-1900,9-1,22)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(5,1,30.0,formatter.format(Date(2019-1900,11-1,23)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(6,1,102.0,formatter.format(Date(2019-1900,11-1,23)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(7,1,111.0,formatter.format(Date(2019-1900,11-1,24)), "13,12,0"))

        appViewModel.insertGlikemia(GlikemiaEntity(1,2,100.0,formatter.format(Date(2019-1900,11-1,21)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(2,2,90.0,formatter.format(Date(2019-1900,11-1,22)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(3,2,90.0,formatter.format(Date(2019-1900,11-1,23)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(4,2,120.0,formatter.format(Date(2019-1900,11-1,23)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(5,2,106.0,formatter.format(Date(2019-1900,11-1,24)), "13,12,0"))

////
        appViewModel.insertGlikemia(GlikemiaEntity(8,2,80.0,formatter.format(Date(2019-1900,9-1,21, 2,2)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(9,2,103.0,formatter.format(Date(2019-1900,9-1,22,3,3)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(10,2,83.0,formatter.format(Date(2019-1900,11-1,23, 4,4)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(11,2,92.0,formatter.format(Date(2019-1900,11-1,23, 5,5)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(12,2,111.0,formatter.format(Date(2019-1900,11-1,24,6,6)), "13,12,0"))

        appViewModel.insertGlikemia(GlikemiaEntity(13,2,100.0,formatter.format(Date(2019-1900,11-1,21,7,7)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(14,2,90.0,formatter.format(Date(2019-1900,11-1,22,8,8)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(15,2,90.0,formatter.format(Date(2019-1900,11-1,23,9,9)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(16,2,120.0,formatter.format(Date(2019-1900,11-1,23,13,13)), "13,12,0"))
        appViewModel.insertGlikemia(GlikemiaEntity(17,2,110.0,formatter.format(Date(2019-1900,11-1,24,18,18)), "13,12,0"))


        appViewModel.insertBP(PressureEntity(1,1,120,80,70, "2019-11-09 12:11:00", "11:53"))
        appViewModel.insertBP(PressureEntity(2,1,130,90,60, "2019-11-10 13:11:00", "11:54"))
        appViewModel.insertBP(PressureEntity(3,1,140,70,77, "2019-11-12 14:11:00", "11:55"))
        appViewModel.insertBP(PressureEntity(4,1,150,80,86, "2019-11-12 15:11:00", "11:56"))
        appViewModel.insertBP(PressureEntity(5,1,160,90,97, "2019-11-13 16:11:00", "11:57"))
        appViewModel.insertBP(PressureEntity(6,1,170,70,100, "2019-11-14 17:11:00", "11:58"))
        appViewModel.insertBP(PressureEntity(7,1,180,100,95, "2019-11-14 18:11:00", "11:59"))

        appViewModel.insertBP(PressureEntity(1,2,120,80,70, "2019-11-15 12:11:00", "11:53"))
        appViewModel.insertBP(PressureEntity(2,2,130,90,60, "2019-11-16 13:11:00", "11:54"))
        appViewModel.insertBP(PressureEntity(3,2,140,70,77, "2019-11-17 14:11:00", "11:55"))
        appViewModel.insertBP(PressureEntity(4,2,150,80,86, "2019-11-18 15:11:00", "11:56"))
        appViewModel.insertBP(PressureEntity(5,2,160,90,97, "2019-11-19 16:11:00", "11:57"))
        appViewModel.insertBP(PressureEntity(6,2,170,70,100, "2019-11-20 17:11:00", "11:58"))
        appViewModel.insertBP(PressureEntity(7,2,180,100,95, "2019-11-21 18:11:00", "11:59"))


        appViewModel.insertInsulin(InsulinEntity(1,1,23.0,"korekta","2019-11-14 18:11:00", "12:53"))
        appViewModel.insertInsulin(InsulinEntity(2,1,11.0,"korekta","2019-11-13 18:11:00", "19:53"))
        appViewModel.insertInsulin(InsulinEntity(3,1,233.0,"okołoposiłkowa","2019-11-14 18:11:00", "18:53"))
        appViewModel.insertInsulin(InsulinEntity(4,1,67.0,"korekta","2019-11-15 18:11:00", "17:53"))
        appViewModel.insertInsulin(InsulinEntity(5,1,90.0,"okołoposiłkowa","2019-11-15 18:11:00", "16:53"))
        appViewModel.insertInsulin(InsulinEntity(6,1,243.0,"korekta","2019-11-16 18:11:00", "15:53"))
        appViewModel.insertInsulin(InsulinEntity(7,1,230.0,"korekta","2019-11-16 18:11:00", "14:53"))
        appViewModel.insertInsulin(InsulinEntity(8,1,111.0,"korekta","2019-11-17 18:11:00", "13:53"))
        appViewModel.insertInsulin(InsulinEntity(9,1,67.0,"korekta","2019-11-19 18:11:00", "11:53"))
        appViewModel.insertInsulin(InsulinEntity(10,1,345.0,"korekta","2019-11-18 18:11:00", "12:53"))

        appViewModel.insertMed(MedicineEntity(1, 1,"apap", 1.0, "2019-11-14 18:11:00", "08:12" ))
        appViewModel.insertMed(MedicineEntity(12, 1,"ibuprom", 2.0, "2019-11-15 18:11:00", "08:12" ))
        appViewModel.insertMed(MedicineEntity(13, 1,"apap", 1.0, "2019-11-12 12:11:00", "08:12" ))
        appViewModel.insertMed(MedicineEntity(14, 1,"apap", 2.0, "2019-11-16 18:11:00", "08:12" ))
        appViewModel.insertMed(MedicineEntity(15, 1,"ibuprom", 1.0, "2019-11-16 18:11:00", "08:12" ))
        appViewModel.insertMed(MedicineEntity(16, 1,"MagB6", 2.0, "2019-11-17 18:11:00", "08:12" ))
        appViewModel.insertMed(MedicineEntity(17, 1,"MagB6", 1.0, "2019-11-17 18:11:00", "08:12" ))
        appViewModel.insertMed(MedicineEntity(18, 1,"apap", 2.0, "2019-11-18 18:11:00", "08:12" ))
        appViewModel.insertMed(MedicineEntity(19, 1,"ibuprom", 1.0, "2019-11-19 18:11:00", "08:12" ))



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

