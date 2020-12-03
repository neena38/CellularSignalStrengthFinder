package com.example.cellularsignalstrengthfinder

import android.annotation.SuppressLint
import android.content.*
import android.content.ContentValues.TAG
import android.os.BatteryManager
import android.util.Log
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CellularService(private var context: Context) {

    val TAG = "tag"
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).build()

    lateinit var cellularSignalRaw: CellularSignalRaw

    fun check() {

        cellularSignalRaw = CellularSignalRaw(
            System.currentTimeMillis(),
            1,
            2,
            3,
            4,
            5,
            6,
            7
        )
        GlobalScope.launch {
            db.cellularSignalDao().insertAll(cellularSignalRaw)
        }

       log()
    }


    @SuppressLint("SetTextI18n")
    fun log() {
        GlobalScope.launch {
            for (info in db.cellularSignalDao().getAll()) {
                Log.d(TAG, "data: $info")
            }
        }
    }

}



