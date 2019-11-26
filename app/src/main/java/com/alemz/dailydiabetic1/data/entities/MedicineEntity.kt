package com.alemz.dailydiabetic1.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Medicine", primaryKeys = arrayOf("id1", "id2"))
data class MedicineEntity(
    @ColumnInfo(name = "id1") val id1: Long,
    @ColumnInfo(name = "id2")val id2: Long,
    @ColumnInfo(name = "medicine_name") var medicine_name: String,
    @ColumnInfo(name = "dose") var dose: Double,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "time") var time: String
)