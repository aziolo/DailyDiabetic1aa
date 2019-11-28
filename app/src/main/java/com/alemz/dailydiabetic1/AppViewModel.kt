package com.alemz.dailydiabetic1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.alemz.dailydiabetic1.data.entities.*

class AppViewModel(application: Application): AndroidViewModel(application) {
    //private var date = Calendar.getInstance().toString()
    private var repository: Repository = Repository(application)
    private var allGlikemia: LiveData<List<GlikemiaEntity>> = repository.getAllGlikemia()
    private var allBP: LiveData<List<PressureEntity>> = repository.getAllBP()
    private var allInsulin: LiveData<List<InsulinEntity>> = repository.getAllInsulin()
    private var settleInsulin: LiveData<List<InsulinEntity>> = repository.getAllInsulin()
    private var foodInsulin: LiveData<List<InsulinEntity>> = repository.getAllInsulin()
    private var profile: LiveData<List<ProfileEntity>> = repository.getProfil()
    private var allMedicines: LiveData<List<MedicineEntity>> = repository.getAllMedicines()


    //GET ALL
    fun getAllGlicemia(): LiveData<List<GlikemiaEntity>>{
        return allGlikemia
    }
    fun getAllBP(): LiveData<List<PressureEntity>>{
        return allBP
    }
    fun getAllInsulin(): LiveData<List<InsulinEntity>>{
        return allInsulin
    }
    fun getSettleInsulin(): LiveData<List<InsulinEntity>>{
        return settleInsulin
    }
    fun getFoodInsulin(): LiveData<List<InsulinEntity>>{
        return foodInsulin
    }
    fun getProfile(): LiveData<List<ProfileEntity>>{
        return profile
    }
    fun getAllMedicines(): LiveData<List<MedicineEntity>>{
        return allMedicines
    }
    fun getNamesOfMeds(): List<String>{
        return repository.findNamesOfMeds()
    }



    //GET FOR CHOSEN DATE
    fun getGlikemiaForChosenDate(d:String): LiveData<List<GlikemiaEntity>>{
        return repository.getGlikemiaForChosenDate(d)
    }
    fun getBPForChosenDate(d:String):LiveData<List<PressureEntity>>{
        return repository.getBPForChosenDate(d)
    }
    fun getInsulinForChosenDate(d: String):LiveData<List<InsulinEntity>>{
        return repository.getInsulinForChosenDate(d)

    }
    fun getOneProfile(id: String): ProfileEntity{
        return repository.getOneProfil(id)
    }
    fun getMedForChosenDate(d:String): LiveData<List<MedicineEntity>>{
        return repository.getMedForChosenDate(d)
    }

    //SHOW STATISTIC

    fun sumAllSettleInsulinForChosenDate(d: String): Double{
        return  repository.sumAllSettle(d)
    }
    fun sumAllFoodInsulinForChosenDate(d: String): Double{
        return  repository.sumAllFood(d)
    }
    fun showMedianPerHourForThisMonth(d: String): Double{
        return  repository.showMedianPerHourForThisMonth(d)
    }

    //INSERT
    fun insertGlikemia(glikemia: GlikemiaEntity){
        repository.insertGlikemia(glikemia)
    }
    fun insertBP(bp: PressureEntity){
        repository.insertBP(bp)
    }
    fun insertInsulin(insulin: InsulinEntity){
        repository.insertInsulin(insulin)
    }
    fun insertProfile(profile: ProfileEntity){
        repository.insertProfile(profile)
    }
    fun insertMed(med: MedicineEntity){
        repository.insertMedicine(med)
    }


}