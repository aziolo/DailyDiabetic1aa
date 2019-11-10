package com.alemz.dailydiabetic1.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alemz.dailydiabetic1.data.entities.PeriodicTestsEntity

@Dao

interface PeriodicTestsDAO {
    @Query("SELECT * FROM periodic_tests")
    fun getAll(): LiveData<List<PeriodicTestsEntity>>

    @Query("SELECT * FROM periodic_tests WHERE date LIKE :date")
    fun findByDate(date: String): LiveData<List<PeriodicTestsEntity>>

    @Query("SELECT * FROM periodic_tests WHERE examination_name LIKE :name")
    fun findByName(name: String): LiveData<List<PeriodicTestsEntity>>

    @Insert
    fun insertAll(vararg test: PeriodicTestsEntity)

    @Delete
    fun delete(test: PeriodicTestsEntity)

    @Update
    fun updateTest(vararg testUpd: PeriodicTestsEntity)
}
