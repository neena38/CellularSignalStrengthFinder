package com.example.cellularsignalstrengthfinder

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.*
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignalChangeListener(private val context: Context) : PhoneStateListener() {

    private var signalDB: SignalDatabase = SignalDatabase.getDatabase(context)!!

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
        val cellularRaw: CellularRaw
        var level = 0
        var strength = 0
        var type = ""
        var asuLevel = 0

        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val cellInfo = telephonyManager.allCellInfo[0]
            Log.d("test", "onSignalStrengthsChanged: ")
            when (cellInfo) {
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
    }
}