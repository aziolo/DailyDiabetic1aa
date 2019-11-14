package com.alemz.dailydiabetic1.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.sql.Date
import java.sql.Time


@Entity(tableName = "Glikemia")
data class GlikemiaEntity(
    @PrimaryKey val id1: Long, val id2: Long,
    @ColumnInfo(name = "amount") var amount: Double,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "time") var time: Time

)