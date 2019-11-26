package com.alemz.dailydiabetic1.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*


@Entity(tableName = "Glikemia", primaryKeys = arrayOf("id1", "id2"))
data class GlikemiaEntity(
    @ColumnInfo(name = "id1") val id1: Long,
    @ColumnInfo(name = "id2")val id2: Long,
    @ColumnInfo(name = "amount") var amount: Double,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "time") var time: String

)