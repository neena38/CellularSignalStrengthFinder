package com.example.cellularsignalstrengthfinder

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SignalChangeListener(private val context: Context) : PhoneStateListener() {

    var signalDB: SignalDatabase = SignalDatabase.getDatabase(context)!!


    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
        var signalRaw: SignalRaw

        var level: Int
        var strength: Int
        super.onSignalStrengthsChanged(signalStrength)
        level = signalStrength.level
        strength = signalStrength.cdmaDbm
        signalRaw = SignalRaw(
            System.currentTimeMillis(), "CELLULAR", strength, level
        )
        GlobalScope.launch {
            signalDB.signalDao().insertAll(signalRaw)
        }

        val wifiManager =
            context.applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        strength = wifiManager.connectionInfo.rssi
        level = getLevel(strength)
        signalRaw = SignalRaw(
            System.currentTimeMillis(), "WIFI", strength, level
        )
        GlobalScope.launch {
            signalDB.signalDao().insertAll(signalRaw)
        }
    }

    private fun getLevel(strength: Int): Int {
        return if (strength > -50)
            3
        else if (strength > -60)
            2
        else if (strength > -70)
            1
        else
            0
    }

}