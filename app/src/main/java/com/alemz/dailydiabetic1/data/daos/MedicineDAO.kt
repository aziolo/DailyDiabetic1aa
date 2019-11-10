package com.alemz.dailydiabetic1.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alemz.dailydiabetic1.data.entities.MedicineEntity
import java.lang.reflect.Array

@Dao

interface MedicineDAO {

    @Query("SELECT * FROM medicine")
    fun getAll(): LiveData<List<MedicineEntity>>

    @Query("SELECT * FROM medicine WHERE date LIKE :date")
    fun findByDate(date: String): LiveData<List<MedicineEntity>>

    @Query("SELECT * FROM medicine WHERE medicine_name LIKE :medicine")
    fun findByName(medicine: String): LiveData<List<MedicineEntity>>

    @Query("SELECT DISTINCT medicine_name FROM medicine")
    fun findNamesOfMeds(): List<String>

    @Insert
    fun insertAll(vararg med: MedicineEntity)

    @Delete
    fun delete(test: MedicineEntity)

    @Update
    fun updateMedicine(vararg testUpd: MedicineEntity)
}