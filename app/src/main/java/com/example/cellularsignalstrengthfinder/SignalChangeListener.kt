package com.example.cellularsignalstrengthfinder

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.*
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

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
                    Log.d("tags", "lte ${cellInfo.isRegistered}")
                    Log.d("tags", "lte")
                }
                is CellInfoGsm -> {
                    type = "GSM"
                    strength = cellInfo.cellSignalStrength.dbm
                    level = cellInfo.cellSignalStrength.level
                    asuLevel = cellInfo.cellSignalStrength.asuLevel
                    Log.d("tags", "gsm ${cellInfo.isRegistered}")
                    Log.d("tags", "gsm")
                }
                is CellInfoCdma -> {
                    type = "CDMA"
                    strength = cellInfo.cellSignalStrength.dbm
                    level = cellInfo.cellSignalStrength.level
                    Log.d("tags", "cdma ${cellInfo.isRegistered}")
                    asuLevel = cellInfo.cellSignalStrength.asuLevel
                    Log.d("tags", "cdma")
                }
                is CellInfoWcdma -> {
                    type = "WCDMA"
                    strength = cellInfo.cellSignalStrength.dbm
                    level = cellInfo.cellSignalStrength.level
                    Log.d("tags", "wcdma ${cellInfo.isRegistered}")
                    asuLevel = cellInfo.cellSignalStrength.asuLevel
                    Log.d("tags", "wcdma")
                }
                is CellInfoNr -> {
                    type = "NR"
                    strength = cellInfo.cellSignalStrength.dbm
                    level = cellInfo.cellSignalStrength.level
                    Log.d("tags", "nr ${cellInfo.isRegistered}")
                    asuLevel = cellInfo.cellSignalStrength.asuLevel
                    Log.d("tags", "nr")
                }
                is CellInfoTdscdma -> {
                    type = "TDSCDMA"
                    strength = cellInfo.cellSignalStrength.dbm
                    level = cellInfo.cellSignalStrength.level
                    asuLevel = cellInfo.cellSignalStrength.asuLevel
                    Log.d("tags", "tdscdma ${cellInfo.isRegistered}")
                    Log.d("tags", "tdscdma")
                }
                else -> {
                    strength = 0
                    level = 0
                }
            }
        } catch (e: SecurityException) {
        } catch (e: Exception) {
            //  to find signal strength from SignalStrength
            val ssignal = signalStrength.toString()
            val parts = ssignal.split(" ", "=", ",").toList()
            var n = 0
            for (i in parts) {
                Log.d("test$n", "onSignalStrengthsChanged: $i")
                n += 1
            }

            level = parts[13].toInt()
            if(level>0) {
                type="CDMA"
                strength = parts[3].toInt()
            }
            level = parts[23].toInt()
            if(level>0) {
                type="GSM"
                strength = parts[17].toInt()
            }
            level = parts[35].toInt()
            if(level>0) {
                type="WCDMA"
                strength = parts[31].toInt()
            }
            level = parts[45].toInt()
            if(level>0) {
                type="TDSCDMA"
                strength = parts[43].toInt()
            }
            level = parts[61].toInt()
            if(level>0) {
                type="LTE"
                strength = parts[51].toInt()
            }


            Log.e("parse", "$level")
            Log.d("test", "onSignalStrengthsChanged: $e")
        }
        Log.d("tag", "data: $type, $strength, $level, $asuLevel")


        /* // get values directly from signalStrength
          level = signalStrength.level
          strength = signalStrength.cdmaDbm*/

        /*   cellularRaw = CellularRaw(
               System.currentTimeMillis(), type, strength, level, asuLevel
           )
           GlobalScope.launch {
               signalDB.cellularDao().insertAll(cellularRaw)
           }*/
    }

}