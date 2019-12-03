package com.alemz.dailydiabetic1

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.alemz.dailydiabetic1.data.AppDataBase
import com.alemz.dailydiabetic1.data.daos.*
import com.alemz.dailydiabetic1.data.entities.*
import kotlinx.android.synthetic.main.fragment_add_measurement.view.*
import java.lang.reflect.Array
import com.alemz.dailydiabetic1.data.entities.MedicineEntity
import java.util.*


class Repository(application: Application) {
    private var glikemiaDao: GlikemiaDAO
    private var allGlikemia: LiveData<List<GlikemiaEntity>>

    private var pressureDao: PressureDAO
    private var allPressure: LiveData<List<PressureEntity>>

    private var insulinDao: InsulinDAO
    private var allInsulin: LiveData<List<InsulinEntity>>
    private var settleInsulin: LiveData<List<InsulinEntity>>
    private var foodInsulin: LiveData<List<InsulinEntity>>

    private var medicineDao: MedicineDAO
    private var allMedicine: LiveData<List<MedicineEntity>>

//    private var periodicDao: PeriodicTestsDAO
//    private var allPeriodic: LiveData<List<PeriodicTestsEntity>>
//
    private var profileDao: ProfileDAO
    private var profil: LiveData<List<ProfileEntity>>

    init {
        val db: AppDataBase = AppDataBase.invoke(application.applicationContext)!!

        glikemiaDao = db.glikemiaDAO()
        allGlikemia = glikemiaDao.getAll()

        pressureDao = db.pressureDAO()
        allPressure = pressureDao.getAll()


        insulinDao = db.insulinDAO()
        allInsulin = insulinDao.getAll()
        settleInsulin = insulinDao.getAllSettle()
        foodInsulin = insulinDao.getAllFood()

        medicineDao = db.medicineDAO()
        allMedicine = medicineDao.getAll()
//
//        periodicDao = db.periodicTestsDAO()
//        allPeriodic = periodicDao.getAll()
//
//
        profileDao = db.profileDAO()
        profil = profileDao.getAll()

    }

    //GET ALL
    fun getAllGlikemia(): LiveData<List<GlikemiaEntity>>{
        return allGlikemia
    }
    fun getAllBP(): LiveData<List<PressureEntity>>{
        return allPressure
    }
    fun getAllInsulin(): LiveData<List<InsulinEntity>> {
        return allInsulin
    }
    fun getSettleInsulin(): LiveData<List<InsulinEntity>> {
        return allInsulin
    }
    fun getFoodInsulin(): LiveData<List<InsulinEntity>> {
        return allInsulin
    }
    fun getProfil(): LiveData<List<ProfileEntity>>{
        return profil
    }
    fun getAllMedicines(): LiveData<List<MedicineEntity>>{
        return allMedicine
    }




    //INSERT
    fun insertGlikemia(glikemia: GlikemiaEntity){
        val insertedGlikemia = InsertGlikemiaAsyncTask(glikemiaDao).execute(glikemia)
    }
    fun insertBP(bp: PressureEntity){
        val insertedBP = InsertBPAsyncTask(pressureDao).execute(bp)
    }
    fun insertInsulin(insulin: InsulinEntity){
        val insertedInsulin = InsertInsulinAsyncTask(insulinDao).execute(insulin)
    }
    fun insertProfile(profil: ProfileEntity){
        val newProfile = InsertNewProfileAsyncTask(profileDao).execute(profil)
    }
    fun insertMedicine(med: MedicineEntity){
        val insertedMed = InsertNewMedicineAsyncTask(medicineDao).execute(med)
    }


    //DELETE
    fun deleteGlikemia(glikemia: GlikemiaEntity){
    }
    fun deleteBP(bp: PressureEntity){
    }
    fun deleteInsulin(insulin: InsulinEntity){
    }
    fun deleteProfile(profile: ProfileEntity){
    }
    fun deleteMedicine(med: MedicineEntity){
    }


    //UPDATE
    fun updateGlikemia(glikemia: GlikemiaEntity){
    }
    fun updateBP(bp: PressureEntity){
    }
    fun updateInsulin(insulin: InsulinEntity){
    }
    fun updateProfile(profile: ProfileEntity){
    }
    fun updateMedicine(med: MedicineEntity){
    }



    // GET FOR CHOSEN DATE
    fun getBPForChosenDate(d:String) :LiveData<List<PressureEntity>>{
        val dao = pressureDao
        val list = GetBPForChosenDateAsyncTask(d,dao).execute(d).get()
        return list
    }
    fun getGlikemiaForChosenDate(d: String) :LiveData<List<GlikemiaEntity>>{
        val dao = glikemiaDao
        val list = GetGlikemiaForChosenDateAsyncTask(d,dao).execute(d).get()
        return list
    }
    fun getInsulinForChosenDate(d:String) : LiveData<List<InsulinEntity>>{
        val dao = insulinDao
        val list = GetInsulinForChosenDateAsyncTask(d,dao).execute(d).get()
        return list
    }

    fun getOneProfil(uid: String): ProfileEntity{
        val dao = profileDao
        val profil = GetOneProfilAsyncTask(uid,dao).execute(uid).get()
        return profil
    }

    fun getMedForChosenDate(d:String): LiveData<List<MedicineEntity>>{
        val dao = medicineDao
        val list = GetMedForChosenDateAsyncTask(d,dao).execute(d).get()
        return list
    }

    fun findNamesOfMeds(): List<String> {
        val dao = medicineDao
        val list = FindNamesOfMedsAsyncTask(dao).execute().get()

        return list
    }


    //statistic
    fun sumAllFood(d: String): Double{
        val dao = insulinDao
        val list = SumAllFoodForChosenDateAsyncTask(d,dao).execute(d).get()
        return list
    }
    fun sumAllSettle(d: String): Double{
        val dao = insulinDao
        val list = SumAllSettleForChosenDateAsyncTask(d,dao).execute(d).get()
        return list
    }
    fun showMedianPerHourForThisMonth(d: String): Double {
        val dao = glikemiaDao
        val median = ShowMedianPerHourForThisMonthAsyncTask(d, dao).execute(d).get()
        return median
    }
    fun showIQRupper(date: String): Double{
        val dao = glikemiaDao
        return ShowIQRupperAsyncTask(date,dao).execute(date).get()
    }

    fun showMIQRlower(date: String): Double{
        val dao = glikemiaDao
        return ShowIQRlowerAsyncTask(date,dao).execute(date).get()
    }
    fun monthlyAverage(date: String): Double{
        val dao= glikemiaDao
        return MonthlyAverageAsyncTask(date, dao ).execute(date).get()
    }
    fun monthlyInDomain(date: String, low: String, high: String): Double{
        val dao = glikemiaDao
        return MonthlyInDomainAsyncTask(date, low, high,dao).execute(date).get()
    }
    fun monthlyCountAll(date: String): Double{
        val dao = glikemiaDao
        return  MonthlyCountAllAsyncTask(date, dao).execute(date).get()
    }




    // ASYNC TASKS get for chosen date
    private class GetGlikemiaForChosenDateAsyncTask(d: String, dao: GlikemiaDAO): AsyncTask<String, Unit,LiveData<List<GlikemiaEntity>>>() {
        val dao = dao
        val check = d
        override fun doInBackground(vararg params: String?): LiveData<List<GlikemiaEntity>> {
            return dao.findByDate(check)
        }
    }
    private class GetBPForChosenDateAsyncTask(d: String, dao: PressureDAO): AsyncTask<String, Unit,LiveData<List<PressureEntity>>>() {
        val dao = dao
        val d = d
        override fun doInBackground(vararg params: String?): LiveData<List<PressureEntity>> {
            return dao.findByDate(d)
        }
    }
    private class GetInsulinForChosenDateAsyncTask(d: String, dao: InsulinDAO): AsyncTask<String, Unit,LiveData<List<InsulinEntity>>>() {
        val dao = dao
        val d = d
        override fun doInBackground(vararg params: String?): LiveData<List<InsulinEntity>> {
            return dao.findByDate(d)
        }
    }

    private class GetOneProfilAsyncTask(id: String, dao:ProfileDAO):
        AsyncTask<String, Unit, ProfileEntity>() {
        val dao = dao
        val id = id
        override fun doInBackground(vararg params: String?): ProfileEntity {
            return dao.getOne(id)
        }
    }
    private class GetMedForChosenDateAsyncTask(d: String, dao: MedicineDAO): AsyncTask<String, Unit,LiveData<List<MedicineEntity>>>(){
        val dao = dao
        val d = d
        override fun doInBackground(vararg params: String?): LiveData<List<MedicineEntity>> {
            return  dao.findByDate(d)
        }
    }
    private class FindNamesOfMedsAsyncTask(dao: MedicineDAO): AsyncTask<String, Unit, List<String>>(){
        val dao = dao
        override fun doInBackground(vararg params: String?): List<String> {
            return dao.findNamesOfMeds()
        }

    }

    // Async task- statistic
    private class SumAllFoodForChosenDateAsyncTask(d: String, dao: InsulinDAO): AsyncTask<String, Unit, Double>(){
        val dao = dao
        val d = d
        override fun doInBackground(vararg params: String?): Double {
            return dao.sumAllFood(d)
        }
    }
    private class SumAllSettleForChosenDateAsyncTask(d: String, dao: InsulinDAO): AsyncTask<String, Unit, Double>(){
        val dao = dao
        val d = d
        override fun doInBackground(vararg params: String?): Double {
            return dao.sumAllSettle(d)
        }
    }

    private class ShowMedianPerHourForThisMonthAsyncTask(d:String, dao:GlikemiaDAO): AsyncTask<String, Unit, Double>(){
        val dao = dao
        val d = d
        override fun doInBackground(vararg params: String?): Double {
            return dao.showMedianPerHourForThisMonth(d)
        }
    }
    private class ShowIQRlowerAsyncTask(d: String, dao: GlikemiaDAO): AsyncTask<String, Unit, Double>(){
        val dao = dao
        val d = d
        override fun doInBackground(vararg params: String?): Double {
            return dao.showIQRlower(d)
        }

    }
    private class ShowIQRupperAsyncTask(d: String, dao: GlikemiaDAO): AsyncTask<String, Unit, Double>(){
        val dao = dao
        val d = d
        override fun doInBackground(vararg params: String?): Double {
            return dao.showIQRupper(d)
        }

    }
    private class MonthlyInDomainAsyncTask(date:String, low:String, high: String,dao: GlikemiaDAO): AsyncTask<String, Unit, Double>(){
        val dao = dao
        val d = date
        val low = low
        val hiigh = high
        override fun doInBackground(vararg params: String?): Double {
            return dao.monthlyInDomain(d, low, hiigh)
        }
    }
    private class MonthlyCountAllAsyncTask(date: String, dao: GlikemiaDAO): AsyncTask<String, Unit, Double>(){
        val dao = dao
        val d = date
        override fun doInBackground(vararg params: String?): Double {
            return dao.monthlyCountAll(d)
        }
    }
    private class MonthlyAverageAsyncTask(date:String, dao: GlikemiaDAO ): AsyncTask<String, Unit, Double>(){
        val dao = dao
        val date = date
        override fun doInBackground(vararg params: String?): Double {
            return dao.monthlyAverage(date)
        }
    }





    //ASYNC TASKS insert
    private class InsertGlikemiaAsyncTask(glikemiaDao: GlikemiaDAO): AsyncTask<GlikemiaEntity,Unit, Unit>(){
        val glikemiaDao = glikemiaDao
        override fun doInBackground(vararg params: GlikemiaEntity) {
            glikemiaDao.insertAll(params[0]!!)
        }
    }
    private class InsertBPAsyncTask(dao: PressureDAO): AsyncTask<PressureEntity,Unit, Unit>(){
        val dao = dao
        override fun doInBackground(vararg params: PressureEntity) {
            dao.insertAll(params[0]!!)
        }
    }
    private class InsertInsulinAsyncTask(dao: InsulinDAO): AsyncTask<InsulinEntity, Unit, Unit>(){
        val dao = dao
        override fun doInBackground(vararg params: InsulinEntity) {
            dao.insertAll(params[0]!!)
        }
    }
    private class InsertNewProfileAsyncTask(dao: ProfileDAO): AsyncTask<ProfileEntity, Unit, Unit>(){
        val dao = dao
        override fun doInBackground(vararg params: ProfileEntity) {
            dao.insertAll(params[0]!!)
        }
    }

    private class InsertNewMedicineAsyncTask(dao: MedicineDAO): AsyncTask<MedicineEntity, Unit, Unit>(){
        val dao = dao
        override fun doInBackground(vararg params: MedicineEntity) {
            dao.insertAll(params[0]!!)
        }
    }

}