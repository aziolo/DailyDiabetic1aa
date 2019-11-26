package com.alemz.dailydiabetic1.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Periodic_tests", primaryKeys = arrayOf("id1", "id2"))
data class PeriodicTestsEntity(
    @ColumnInfo(name = "id1") val id1: Long,
    @ColumnInfo(name = "id2")val id2: Long,
    @ColumnInfo(name = "examination_name") var examination_name: String,
    @ColumnInfo(name = "result") var result: Double,
    @ColumnInfo(name = "date") var date: String
)