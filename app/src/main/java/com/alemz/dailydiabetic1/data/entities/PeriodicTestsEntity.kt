package com.alemz.dailydiabetic1.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Periodic_tests")
data class PeriodicTestsEntity(
    @PrimaryKey val id1: Long, val id2: Long,
    @ColumnInfo(name = "examination_name") var examination_name: String,
    @ColumnInfo(name = "result") var result: Double,
    @ColumnInfo(name = "date") var date: String
)