package com.alemz.dailydiabetic1.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Profile")
data class ProfileEntity(
    @PrimaryKey val id1: Long, val id2: Long,
    @ColumnInfo(name = "first_name") var first_name: String,
    @ColumnInfo(name = "last_name") var last_name: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "type_of_diabetes") var type_of_diabetes: String,
    @ColumnInfo(name = "wage") var wage: Double,
    @ColumnInfo(name = "height") var height: Double,
    @ColumnInfo(name = "max_dose_of_insulin") var max_dose_of_insulin: Double,
    @ColumnInfo(name = "doctor_email") var doctor_email: String,
    @ColumnInfo(name = "rule_for_insulin") var rule_for_insulin: Int

)