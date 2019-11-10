package com.alemz.dailydiabetic1.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alemz.dailydiabetic1.data.entities.GlikemiaEntity

@Dao
interface GlikemiaDAO {

    @Query("SELECT * FROM glikemia")
    fun getAll(): LiveData<List<GlikemiaEntity>>

    @Query("SELECT * FROM glikemia WHERE date LIKE :date")
    fun findByDate(date: String): LiveData<List<GlikemiaEntity>>

    @Insert
    fun insertAll(vararg glikemia: GlikemiaEntity)

    @Delete
    fun delete(glikemia: GlikemiaEntity)

    @Update
    fun updateGlikemia(vararg glikemiaUpd: GlikemiaEntity)
}