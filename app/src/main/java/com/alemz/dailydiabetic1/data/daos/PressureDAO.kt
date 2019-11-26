package com.alemz.dailydiabetic1.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alemz.dailydiabetic1.data.entities.PressureEntity

@Dao

interface PressureDAO {

    @Query("SELECT * FROM pressure ORDER BY date")
    fun getAll(): LiveData<List<PressureEntity>>

    @Query("SELECT * FROM pressure WHERE date LIKE :date ORDER BY date")
    fun findByDate(date: String): LiveData<List<PressureEntity>>

    @Insert
    fun insertAll(vararg pressure: PressureEntity)

    @Delete
    fun delete(pressure: PressureEntity)

    @Update
    fun updatePressure(vararg pressureUpd: PressureEntity)
}