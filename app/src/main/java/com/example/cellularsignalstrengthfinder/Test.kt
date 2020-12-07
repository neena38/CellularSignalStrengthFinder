package com.example.cellularsignalstrengthfinder

import android.content.Context
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Test(context: Context) {
    private var signalDB: SignalDatabase = SignalDatabase.getDatabase(context)!!
    fun log() {
        GlobalScope.launch {
            for (info in signalDB.wifiDao().getAll()) {
                Log.d("wifi", "$info")
            }
        }
    }

    fun log1() {
        GlobalScope.launch {
            Log.d("cellular","inside cellular")
            for (info in signalDB.cellularDao().getAll()) {
                Log.d("cellular", "$info")
            }
        }
    }
}