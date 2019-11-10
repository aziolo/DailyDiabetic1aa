 package com.alemz.dailydiabetic1


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar



import com.alemz.dailydiabetic1.view.AddMeasurementFragment
import com.alemz.dailydiabetic1.view.CalendarFragment


 class NewMeasureActivity : AppCompatActivity(), AddMeasurementFragment.OnFragmentInteractionListener {
     private lateinit var dateText: TextView
     private lateinit var timeText: TextView
     private lateinit var date: String
     private lateinit var time: String
     private lateinit var list: ArrayList<String>
     override fun onFragmentInteraction(uri: Uri) {
         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
     }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_measure)

         var toolbar = findViewById<Toolbar>(R.id.toolbar_new_measure)
         setSupportActionBar(toolbar)
         //setActionBar(toolbar)
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // toolbar.setDisplayHomeAsUpEnabled(true)
        // actionBar.setDisplayHomeAsUpEnabled(true)
         //actionBar.setDisplayShowHomeEnabled(true)

         dateText = findViewById(R.id.text_show_date)
         timeText = findViewById(R.id.text_show_time)
         list = intent.getStringArrayListExtra("lista")
         timeText.text = list[1]
         dateText.text = list[0]



         Toast.makeText(this, " ${list.size}", Toast.LENGTH_SHORT).show()



    }

     override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         var id: Int = item!!.itemId
         when(item.itemId){
             R.id.item_settings -> {}
             R.id.home -> {
                 onBackPressed()
                 return true
             }
         }
//         if(id == android.R.id.home) this.finish()
//         if(id)
         return super.onOptionsItemSelected(item)
     }

     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.new_measurement_menu, menu)
         menu!!.findItem(R.id.item_settings).isVisible = true
         menu.findItem(R.id.item_title_fragment).isVisible = true

         return super.onCreateOptionsMenu(menu)
     }
//     fun onCheckBoxClicked(view: View){
//         if(view is CheckBox){
//             val checked: Boolean = view.isChecked
//
//             when(view.id){
//                 R.id.glikemia_chech_box ->{
//                     if(checked){
//                         params.height = ViewGroup.LayoutParams.WRAP_CONTENT
//                         //invalidateOptionsMenu()
//
//                     }
//                     else{
//                         params.height =(0)
//                         invalidateOptionsMenu()
//                     }
//                     vg.invalidate()
//                 }
//
//                 R.id.pressure_check_box ->{
//                     if(checked){
//
//
//                     }
//                     else{
//
//                     }
//                 }
//             }
//         }
//     }
}
