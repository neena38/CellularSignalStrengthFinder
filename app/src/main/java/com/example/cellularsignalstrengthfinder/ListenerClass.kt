package com.example.cellularsignalstrengthfinder

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.CellInfoGsm
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class signalChange(private val context: Context) : PhoneStateListener() {

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "signalDatabase"
    ).build()


    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
        var signalRaw: SignalRaw
        var level: Int
        var strength: Int
        super.onSignalStrengthsChanged(signalStrength)
        val mSignalStrength = signalStrength.gsmSignalStrength
        level = signalStrength.level
        strength = signalStrength.cdmaDbm
        // Log.e("tagsignal", "$mSignalStrength,$v,$v1")
        signalRaw = SignalRaw(
            System.currentTimeMillis(), "CELLULAR", strength, level
        )
        GlobalScope.launch {
            db.signalDao().insertAll(signalRaw)
        }

        /*  try {
              val telephonyManager =
                  context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
              val cellinfogsm = telephonyManager.allCellInfo[0] as CellInfoGsm
          } catch (e: SecurityException) {
          }*/

        val wifiManager =
            context.applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        strength = wifiManager.connectionInfo.rssi
        level = getLevel(strength)
        signalRaw = SignalRaw(
            System.currentTimeMillis(), "WIFI", strength, level
        )
        GlobalScope.launch {
            db.signalDao().insertAll(signalRaw)
        }
        //Log.e("wifi", "$ws")
        log()
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

    fun log() {
        GlobalScope.launch {
            for (info in db.signalDao().getAll()) {
                Log.d("tag", "data: $info")
            }
        }
    }
}