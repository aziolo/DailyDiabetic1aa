package com.alemz.dailydiabetic1.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alemz.dailydiabetic1.data.entities.InsulinEntity

@Dao

interface InsulinDAO {

    @Query("SELECT * FROM insulin")
    fun getAll(): LiveData<List<InsulinEntity>>

    @Query("SELECT * FROM insulin WHERE date LIKE :date")
    fun findByDate(date: String): LiveData<List<InsulinEntity>>

    @Insert
    fun insertAll(vararg insulin: InsulinEntity)

    @Delete
    fun delete(insulin: InsulinEntity)

    @Update
    fun updateInsulin(vararg insulinUpd: InsulinEntity)
}