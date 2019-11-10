package com.alemz.dailydiabetic1.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alemz.dailydiabetic1.data.daos.*
import com.alemz.dailydiabetic1.data.entities.*
import java.security.AccessControlContext

@Database(entities = arrayOf(ProfileEntity::class, GlikemiaEntity::class, InsulinEntity::class, MedicineEntity::class, PeriodicTestsEntity::class, PressureEntity::class), version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun glikemiaDAO(): GlikemiaDAO
    abstract fun insulinDAO(): InsulinDAO
    abstract fun medicineDAO(): MedicineDAO
    abstract fun periodicTestsDAO(): PeriodicTestsDAO
    abstract fun pressureDAO(): PressureDAO
    abstract fun profileDAO(): ProfileDAO

    companion object {

        //volatile guarantee that the value that is being read comes from the main memory not the cpu-cache, read the newest value from memory
        //operator ? = safe cals
        //return AppDataBase if not-null otherwise return null
        @Volatile private var instance: AppDataBase? = null

        //Any() returns true if has at least one element
        private val LOCK = Any()


        //synchronized with lock guarantee blocking the room. When we have one thread no one else(other thread) can use the room. reduce the mistakes
        //any thread that reaches the point lock the instance and does the work defined in the code-block:
        //Elvis operator ?:
        //if instance is not-null return instance otherwise return synchronized(LOCK)
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

            //if database does not exist build it
            instance ?: buildDataBase(context).also{ instance = it}
        }

        //prawdziwa baza
        //private fun buildDataBase(context: Context) = Room.databaseBuilder(context, AppDataBase::class.java, "diabetic_daily.db").build()
        //baza do fazy testów, będzie działać tylko temporary
        private fun buildDataBase(context: Context) = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
    }

    private val roomCallback = object : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            PopulateDbAsyncTask(instance).execute()
        }
    }


}

class PopulateDbAsyncTask(db: AppDataBase?): AsyncTask<Unit, Unit,Unit>(){
    private val glikemiaDao = db?.glikemiaDAO()
    override fun doInBackground(vararg params: Unit?) {
//        glikemiaDao?.insertAll(GlikemiaEntity(1,13.0,"12.11.1999", "13:13"))
//        glikemiaDao?.insertAll(GlikemiaEntity(2,14.0,"12.11.1999", "13:13"))
//        glikemiaDao?.insertAll(GlikemiaEntity(3,15.0,"12.11.1999", "13:13"))
//        glikemiaDao?.insertAll(GlikemiaEntity(4,16.0,"12.11.1999", "13:13"))
    }
}