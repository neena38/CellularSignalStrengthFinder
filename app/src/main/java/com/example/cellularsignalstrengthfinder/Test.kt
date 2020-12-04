package com.example.cellularsignalstrengthfinder

import android.content.Context
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Test(private val context: Context) {
    var signalDB: SignalDatabase = SignalDatabase.getDatabase(context)!!
    fun log() {
        GlobalScope.launch {
            for (info in signalDB.signalDao().getAll()) {
                Log.d("tag", "data: $info")
            }
        }
    }
}