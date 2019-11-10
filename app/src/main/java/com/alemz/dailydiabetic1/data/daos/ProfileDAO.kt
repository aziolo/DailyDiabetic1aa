package com.alemz.dailydiabetic1.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alemz.dailydiabetic1.data.entities.ProfileEntity

@Dao
interface ProfileDAO{

    @Query("SELECT * FROM profile")
    fun getAll(): LiveData<List<ProfileEntity>>

    @Query("SELECT * FROM profile WHERE email LIKE :uid")
    fun getOne(uid: String): ProfileEntity

    @Insert
    fun insertAll(vararg profile: ProfileEntity)

    @Delete
    fun delete(profile: ProfileEntity)

    @Update
    fun updateProfile(vararg profileUpd: ProfileEntity)

    //vararg we can put more than one value
}