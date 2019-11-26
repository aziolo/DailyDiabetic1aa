package com.alemz.dailydiabetic1.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alemz.dailydiabetic1.data.entities.InsulinEntity

@Dao

interface InsulinDAO {

    @Query("SELECT * FROM insulin ORDER BY date")
    fun getAll(): LiveData<List<InsulinEntity>>

    @Query("SELECT * FROM insulin WHERE date LIKE :date ORDER BY date")
    fun findByDate(date: String): LiveData<List<InsulinEntity>>

    @Query("SELECT * FROM insulin WHERE type LIKE :type ORDER BY date")
    fun getAllSettle(type: String= "wyrównanie"): LiveData<List<InsulinEntity>>

    @Query("SELECT * FROM insulin WHERE type LIKE :type ORDER BY date")
    fun getAllFood(type: String = "okołoposiłkowa"): LiveData<List<InsulinEntity>>

    @Query("SELECT SUM(amount) FROM insulin WHERE type LIKE 'okołoposiłkowa' AND date LIKE :d ORDER BY date")
    fun sumAllFood(d: String): Double

    @Query("SELECT SUM(amount) FROM insulin WHERE type LIKE 'korekta' AND date LIKE :d ORDER BY date")
    fun sumAllSettle(d: String): Double

    @Insert
    fun insertAll(vararg insulin: InsulinEntity)

    @Delete
    fun delete(insulin: InsulinEntity)

    @Update
    fun updateInsulin(vararg insulinUpd: InsulinEntity)
}