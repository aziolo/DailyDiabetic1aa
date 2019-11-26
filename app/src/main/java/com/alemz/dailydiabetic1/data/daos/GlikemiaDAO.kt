package com.alemz.dailydiabetic1.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alemz.dailydiabetic1.data.entities.GlikemiaEntity
import java.util.*

@Dao
interface GlikemiaDAO {

    @Query("SELECT * FROM glikemia ORDER BY date")
    fun getAll(): LiveData<List<GlikemiaEntity>>

    @Query("SELECT * FROM glikemia WHERE date LIKE :date ORDER BY date")
    fun findByDate(date: String): LiveData<List<GlikemiaEntity>>

    @Insert
    fun insertAll(vararg glikemia: GlikemiaEntity)

    @Delete
    fun delete(glikemia: GlikemiaEntity)

    @Update
    fun updateGlikemia(vararg glikemiaUpd: GlikemiaEntity)
}