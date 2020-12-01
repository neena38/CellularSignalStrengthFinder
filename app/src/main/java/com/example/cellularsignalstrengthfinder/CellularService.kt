/*
package com.example.cellularsignalstrengthfinder

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CellularService(private var context: Context, private var textView: TextView) {

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).build()

    private var batteryBroadcastReceiver: BroadcastReceiver
    lateinit var batteryRaw: BatteryRaw
    private var batteryCapacity: Double
    private var batteryManager: BatteryManager =
        context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    init {
        batteryCapacity = getBatteryCapacity()*1000
        batteryBroadcastReceiver = object : BroadcastReceiver() {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            override fun onReceive(ctxt: Context?, intent: Intent) {
                batteryRaw = BatteryRaw(
                    System.currentTimeMillis(),
                    intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0),
                    intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0),
                    intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10,
                    intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0),
                    batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER),
                    intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0),
                )
                GlobalScope.launch { db.batteryInfoDao().insertAll(batteryRaw) }
//                log()
                if(batteryRaw.estimatedAccuracy!! >90 && batteryRaw.plugged!! >0){
                    Log.d(TAG, "LastGoodEstCap: ${batteryRaw.estimatedCapacity} ")
                    Log.d(TAG, "Battery Health: ${(batteryRaw.estimatedCapacity/batteryCapacity)*100} ")
                }
            }
        }
    }

    @SuppressLint("PrivateApi")
    fun getBatteryCapacity(): Double {
        var powerProfile: Any? = null
        val profileClass = "com.android.internal.os.PowerProfile"
        try {
            powerProfile = Class.forName(profileClass)
                .getConstructor(Context::class.java).newInstance(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            return Class
                .forName(profileClass)
                .getMethod("getAveragePower", String::class.java)
                .invoke(powerProfile, "battery.capacity") as Double
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0.0
    }

    fun registerListener() {
        this.context.registerReceiver(
            batteryBroadcastReceiver,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
    }


    fun unRegisterListener() {
        this.context.unregisterReceiver(batteryBroadcastReceiver)
    }

    @SuppressLint("SetTextI18n")
    fun log() {
        textView.text = ""
        GlobalScope.launch {
            for (info in db.batteryInfoDao().getAll()) {
                textView.post {
                    textView.text = textView.text.toString() +
                            "\n------------\nTimeStamp: ${Utils.getDateTime(info.timeStamp)}\n level: ${info.level}\n plugged: ${info.plugged}\n temp: ${info.temp} health: ${info.health}\n estimatedCapacity: ${info.estimatedCapacity} mAh\n estimatedAccuracy: ${info.estimatedAccuracy}"
                }
            }
        }

    }

}*/
