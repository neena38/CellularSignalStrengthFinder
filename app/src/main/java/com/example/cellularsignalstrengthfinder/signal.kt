package com.example.cellularsignalstrengthfinder

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class signalChange(private val context: Context) : PhoneStateListener() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
        super.onSignalStrengthsChanged(signalStrength)
        val mSignalStrength = signalStrength.gsmSignalStrength

        // mSignalStrength = 2 * mSignalStrength - 113 // -> dBm
        val v=signalStrength.level
        val v1=signalStrength.cdmaDbm
        val v2=signalStrength.toString()
        Log.e("tagsignal","$mSignalStrength,$v,$v1,$v2")

        val wifiManager = context.applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        val ws=wifiManager.connectionInfo.rssi
        Log.e("wifi","$ws")


    }
}