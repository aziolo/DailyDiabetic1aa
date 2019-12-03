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

    //statystyka
    @Query("SELECT avg(amount) FROM (SELECT amount FROM glikemia WHERE date LIKE :date ORDER BY amount LIMIT 2 - (SELECT count(amount) FROM glikemia WHERE date like:date) % 2 OFFSET (SELECT (count(*) - 1) / 2 FROM glikemia WHERE date like :date))")
    fun showMedianPerHourForThisMonth(date: String): Double

    @Query("SELECT 1.5*(amount) FROM (SELECT amount FROM glikemia WHERE date LIKE :date ORDER BY amount LIMIT 2 - (SELECT count(amount) FROM glikemia WHERE date like:date) % 2 OFFSET (SELECT (count(*) - 1) / 2 FROM glikemia WHERE date like :date))")
    fun showIQRupper(date: String): Double

    @Query("SELECT 0.5*(amount) FROM (SELECT amount FROM glikemia WHERE date LIKE :date ORDER BY amount LIMIT 2 - (SELECT count(amount) FROM glikemia WHERE date like:date) % 2 OFFSET (SELECT (count(*) - 1) / 2 FROM glikemia WHERE date like :date))")
    fun showIQRlower(date: String): Double

    @Query("SELECT avg(amount) FROM glikemia WHERE date LIKE :date")
    fun monthlyAverage(date: String): Double

    @Query("SELECT count(amount) FROM glikemia WHERE date LIKE :date AND amount BETWEEN :low AND :high")
    fun monthlyInDomain(date: String, low: String, high: String): Double

    @Query("SELECT count(amount) FROM glikemia WHERE date LIKE :date")
    fun monthlyCountAll(date: String): Double
    /// nie wiem co napisać

    @Query("SELECT avg(amount) FROM glikemia WHERE date LIKE :date")
    fun monthlyVariation(date: String): Double

    @Query("SELECT avg(amount) FROM glikemia WHERE date LIKE :date")
    fun monthlySTD(date: String): Double
}
