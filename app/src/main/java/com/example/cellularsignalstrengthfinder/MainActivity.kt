package com.example.cellularsignalstrengthfinder

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

const val permissionCode = 200
val permissions: Array<String> = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE)

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
        getSignalStrength()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                permissions,
                permissionCode
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getSignalStrength(){

        try {
            val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            val type=telephonyManager.dataNetworkType
            Log.e("tag", "$type")

            when (type) {
                18 -> { val cellinfogsm = telephonyManager.allCellInfo[0]as CellInfoGsm
                    var req=cellinfogsm.cellSignalStrength
                    Log.e("tag", "$req")}
                13 -> { val cellinfogsm = telephonyManager.allCellInfo[0]as CellInfoLte
                    var req=cellinfogsm.cellSignalStrength
                    Log.e("tag", "$req")}
            }

        }
        catch (e: SecurityException){
            Log.e("tag123", e.toString())
        }
    }

}
