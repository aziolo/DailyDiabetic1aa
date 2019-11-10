package com.alemz.dailydiabetic1.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pressure")
data class PressureEntity(
    @PrimaryKey val id1: Long, val id2: Long,
    @ColumnInfo(name = "level_systolic") var level_systolic: Int,
    @ColumnInfo(name = "level_diastolic") var level_diastolic: Int,
    @ColumnInfo(name = "pulse") var pulse: Int,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "time") var time: String
)