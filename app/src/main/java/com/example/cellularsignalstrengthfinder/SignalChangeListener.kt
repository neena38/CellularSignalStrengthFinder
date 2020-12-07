package com.example.cellularsignalstrengthfinder

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignalChangeListener(private val context: Context) : PhoneStateListener() {

    private var signalDB: SignalDatabase = SignalDatabase.getDatabase(context)!!

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("ServiceCast")
    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
        val wifiRaw: WifiRaw
        val cellularRaw: CellularRaw
        var level = 0
        var strength = 0
        var type = ""
        var asuLevel = 0

        super.onSignalStrengthsChanged(signalStrength)
        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            when (val cellInfo = telephonyManager.allCellInfo[0]){
                is CellInfoLte -> {
                    type = "LTE"
                    strength = cellInfo.cellSignalStrength.dbm
                    level = cellInfo.cellSignalStrength.level
                    asuLevel = cellInfo.cellSignalStrength.asuLevel
                    Log.d("tags", "lte")
                }
                is CellInfoGsm -> {
                    type = "GSM"
                    strength = cellInfo.cellSignalStrength.dbm
                    level = cellInfo.cellSignalStrength.level
                    asuLevel = cellInfo.cellSignalStrength.asuLevel
                    Log.d("tags", "gsm")
                }
                is CellInfoCdma -> {
                    type = "CDMA"
                    strength = cellInfo.cellSignalStrength.dbm
                    level = cellInfo.cellSignalStrength.level
                    asuLevel = cellInfo.cellSignalStrength.asuLevel
                    Log.d("tags", "cdma")
                }
                is CellInfoWcdma -> {
                    type = "WCDMA"
                    strength = cellInfo.cellSignalStrength.dbm
                    level = cellInfo.cellSignalStrength.level
                    asuLevel = cellInfo.cellSignalStrength.asuLevel
                    Log.d("tags", "wcdma")
                }
                is CellInfoNr -> {
                    type = "NR"
                    strength = cellInfo.cellSignalStrength.dbm
                    level = cellInfo.cellSignalStrength.level
                    asuLevel = cellInfo.cellSignalStrength.asuLevel
                    Log.d("tags", "wcdma")
                }
                is CellInfoTdscdma -> {
                    type = "TDSCDMA"
                    strength = cellInfo.cellSignalStrength.dbm
                    level = cellInfo.cellSignalStrength.level
                    asuLevel = cellInfo.cellSignalStrength.asuLevel
                    Log.d("tags", "wcdma")
                }
                else -> {
                    strength = 0
                    level = 0
                }
            }
        } catch (e: SecurityException) {
        }
        Log.d("tag", "data: $strength, $level")


        /*//  to find signal strength from SignalStrength
        val ssignal = signalStrength.toString()
        val parts = ssignal.split(" ", "=", ",").toList()
        var n = 0
        for (i in parts) {
            Log.d("test$n", "onSignalStrengthsChanged: $i")
            n += 1
        }
        //  level = parts[61].toInt()
        Log.e("parse", "$level")


       // get values directly from signalStrength
        level = signalStrength.level
        strength = signalStrength.cdmaDbm*/


        cellularRaw = CellularRaw(
            System.currentTimeMillis(), type, strength, level, asuLevel
        )
        GlobalScope.launch {
            signalDB.cellularDao().insertAll(cellularRaw)
        }

        val wifiManager =
            context.applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        strength = wifiManager.connectionInfo.rssi
        level = getLevel(strength)
        wifiRaw = WifiRaw(
            System.currentTimeMillis(), strength, level
        )

        GlobalScope.launch {
            signalDB.wifiDao().insertAll(wifiRaw)
        }

        Log.d("tag", "wifi: $strength, $level")

    }

    private fun getLevel(strength: Int): Int {
        return when {
            strength > -50 -> 3
            strength > -60 -> 2
            strength > -70 -> 1
            else -> 0
        }
    }
}