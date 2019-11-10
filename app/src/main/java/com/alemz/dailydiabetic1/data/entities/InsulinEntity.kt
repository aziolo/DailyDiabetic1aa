package com.alemz.dailydiabetic1.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Insulin")
data class InsulinEntity(
    @PrimaryKey val id1: Long, val id2: Long,
    @ColumnInfo(name = "amount") var amount: Double,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "time") var time: String
)