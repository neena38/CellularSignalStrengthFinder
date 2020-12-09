package com.example.cellularsignalstrengthfinder

import android.content.Context
import android.net.wifi.WifiManager
import android.telephony.*
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room


class signalChange(private val context: Context) : PhoneStateListener() {

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "signalDatabase"
    ).build()

    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
        var signalRaw: SignalRaw
        var level = 0
        var wifiLevel = 0
        var dbmStrength = 0
        var extStrength = 0
        var type = ""
        var wifiStrength = 0


        try {

            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val cellinfogsm = telephonyManager.allCellInfo[0]
            when (cellinfogsm) {
                is CellInfoLte -> {
                    dbmStrength = cellinfogsm.cellSignalStrength.dbm
                    level = cellinfogsm.cellSignalStrength.level
                    extStrength=cellinfogsm.cellSignalStrength.asuLevel
                    type = "lte"
                    Log.d("tags", "lte")
                }
                is CellInfoGsm -> {
                    type = "gsm"
                    dbmStrength = cellinfogsm.cellSignalStrength.dbm
                    level = cellinfogsm.cellSignalStrength.level
                    extStrength=cellinfogsm.cellSignalStrength.asuLevel
                    Log.d("tags", "gsm")
                }
                is CellInfoCdma -> {
                    type = "cdma"
                    dbmStrength = cellinfogsm.cellSignalStrength.dbm
                    level = cellinfogsm.cellSignalStrength.level
                    extStrength=cellinfogsm.cellSignalStrength.asuLevel
                    Log.d("tags", "cdma")
                }
                is CellInfoWcdma -> {
                    type = "wcdma"
                    dbmStrength = cellinfogsm.cellSignalStrength.dbm
                    level = cellinfogsm.cellSignalStrength.level
                    extStrength=cellinfogsm.cellSignalStrength.asuLevel
                    Log.d("tags", "wcdma")
                }
                else -> {
                    dbmStrength=9
                    level=9
                    extStrength=9
                }
            }

        } catch (e: SecurityException) {
            Log.e("security", "$e")
        }// catch (e: Exception) {
        //    Log.e("exception", "$e")
        //}
        Log.e("cellular", "$dbmStrength,$level,$extStrength")
        val wifiManager =
            context.applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        wifiStrength = wifiManager.connectionInfo.rssi
        wifiLevel = getLevel(wifiStrength)
        Log.e("wifi", "$wifiStrength\n$wifiLevel")

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            level = signalStrength.level
//        } else {
//            signalStrength.evdoSnr
//            level=getCellularLevel(dbmStrength)
//        }


        Toast.makeText(
            context,
            "Wifi Signal Strength in dB  $wifiStrength,$wifiLevel\n" +
                    "Cellulat Signal strength in dB  $dbmStrength,$extStrength,$level,$type",
            Toast.LENGTH_SHORT
        ).show()

    }

    private fun getLevel(strength: Int): Int {
        return when {
            strength > -50 -> 3
            strength > -60 -> 2
            strength > -70 -> 1
            else -> 0
        }
    }
    fun getCellularLevel(db:Int):Int{
        if(db<=-120) return 0
        else if (db<=-111)return 1
        else if (db<=-106) return  2
        else if (db<=-91)return 3
        else return  4
    }
}