package com.example.cellularsignalstrengthfinder

import android.Manifest
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.telephony.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


const val permissionCode = 200
val permissions: Array<String> =
    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE)

class MainActivity : AppCompatActivity() {
    lateinit var cellularService: CellularService
    lateinit var telephonyManager:TelephonyManager
    lateinit var MyListener: MyPhoneStateListener

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyListener= MyPhoneStateListener()
        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)

        setContentView(R.layout.activity_main)
        cellularService = CellularService(this)
        cellularService.check()
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
    private fun getSignalStrength() {
        /*try {
            val type = telephonyManager.networkType
            Log.e("tag", " $type")
            val cellinfo = telephonyManager.allCellInfo[0]

            *//* val req = if (cellinfo is CellInfoLte) {
                 cellinfo.cellSignalStrength.dbm
                 Log.d("tags", "lte")
             } else if (cellinfo is CellInfoGsm) {
                 cellinfo.cellSignalStrength.rssi
                 Log.d("tags", "gsm")
             } else if (cellinfo is CellInfoCdma) {
                 cellinfo.cellSignalStrength.cdmaDbm
                 Log.d("tags", "cdma")
             } else if (cellinfo is CellInfoWcdma) {
                 cellinfo.cellSignalStrength.ecNo
                 Log.d("tags", "wcdma")
             } else {
                 0
             }
             Log.d("tags", "getSignalStrength dbm: $req ")

             val req1 = when (cellinfo) {
                 is CellInfoLte -> cellinfo.cellSignalStrength.asuLevel
                 is CellInfoGsm -> cellinfo.cellSignalStrength.asuLevel
                 is CellInfoCdma -> cellinfo.cellSignalStrength.asuLevel
                 is CellInfoWcdma -> cellinfo.cellSignalStrength.asuLevel
                 else -> 0
             }
             Log.d("tags", "getSignalStrength: asulevel $req1 ")
             val req2 = when (cellinfo) {
                 is CellInfoLte -> cellinfo.cellSignalStrength.level
                 is CellInfoGsm -> cellinfo.cellSignalStrength.level
                 is CellInfoCdma -> cellinfo.cellSignalStrength.level
                 is CellInfoWcdma -> cellinfo.cellSignalStrength.level
                 else -> 0
             }*//*
            //Log.d("tags", "getSignalStrength: level $req2 ")

            *//*when (type) {
                18,1 -> {
                    val cellinfogsm = telephonyManager.allCellInfo[0] as CellInfoGsm
                    var req = cellinfogsm.cellSignalStrength
                    Log.e("tag18", "$req")
                }
                13 -> {
                    val cellinfogsm = telephonyManager.allCellInfo[0] as CellInfoGsm
                    var req = cellinfogsm.cellSignalStrength
                    Log.e("tag13", "$req")
                }
            }*//*

        } catch (e: SecurityException) {
            Log.e("tag123", e.toString())
        }
*/
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val Info = cm.activeNetworkInfo
        if (Info == null ) {
            Log.i(TAG, "No connection")
        } else {
            val netType = Info.type
            val netSubtype = Info.subtype
            if (netType == ConnectivityManager.TYPE_WIFI) {
                Log.i(TAG, "Wifi connection")
                val wifiManager = application.getSystemService(WIFI_SERVICE) as WifiManager
                val scanResult: List<ScanResult> = wifiManager.scanResults
                for (i in scanResult.indices) {
                    Log.d(
                        "scanResult",
                        "Speed of wifi" + scanResult[i].level
                    ) //The db level of signal
                }


                // Need to get wifi strength
            } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                Log.i(TAG, "GPRS/3G connection")
                // Need to get differentiate between 3G/GPRS
            }
        }
    }

    override fun onPause() {
        super.onPause()
        telephonyManager.listen(MyListener, PhoneStateListener.LISTEN_NONE)
    }

    override fun onResume() {
        super.onResume()
        telephonyManager.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)
    }

    class MyPhoneStateListener : PhoneStateListener() {
        /* Get the Signal strength from the provider, each time there is an update */
        override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
            super.onSignalStrengthsChanged(signalStrength)
            val req2 = when () {
                is CellInfoLte -> cellinfo.cellSignalStrength.level
                is CellInfoGsm -> cellinfo.cellSignalStrength.level
                is CellInfoCdma -> cellinfo.cellSignalStrength.level
                is CellInfoWcdma -> cellinfo.cellSignalStrength.level
                else -> 0
            }
            val signalST = signalStrength.level
            Log.d("tags", "getSignalStrength:k  $signalST ")
        }
    }
}
