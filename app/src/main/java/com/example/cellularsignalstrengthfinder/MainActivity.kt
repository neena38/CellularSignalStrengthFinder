package com.example.cellularsignalstrengthfinder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService


const val permissionCode = 200
val permissions: Array<String> =
    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE)

class MainActivity : AppCompatActivity() {
    lateinit var cellularService: CellularService
    lateinit var telephonyManager: TelephonyManager
    lateinit var MyListener: MyPhoneStateListener

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyListener = MyPhoneStateListener(TELECOM_SERVICE)
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

    private fun getSignalStrength() {
        try {
            val type = telephonyManager.networkType
            Log.e("tag", " $type")
            val cellinfo = telephonyManager.allCellInfo[0]

            /* val req = if (cellinfo is CellInfoLte) {
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
            }*/

        } catch (e: SecurityException) {
            Log.e("tag123", e.toString())
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

    @RequiresApi(Build.VERSION_CODES.Q)
    class MyPhoneStateListener(context: Context) : PhoneStateListener() {
        /* Get the Signal strength from the provider, each time there is an update */
        override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
            super.onSignalStrengthsChanged(signalStrength)
         //   val signalST = signalStrength.CellSignalStrengthGSM
         //   Log.d("tags", "getSignalStrength:k  $signalST ")
            val telephonyManager = getSystemService(context) as TelephonyManager
            val type = if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            telephonyManager.networkType
            val cellinfo = telephonyManager.allCellInfo[0]
            val req1 = when (cellinfo) {
                is CellInfoLte -> cellinfo.cellSignalStrength.asuLevel
                is CellInfoGsm -> cellinfo.cellSignalStrength.asuLevel
                is CellInfoCdma -> cellinfo.cellSignalStrength.asuLevel
                is CellInfoWcdma -> cellinfo.cellSignalStrength.asuLevel
                else -> 0
            }
            Log.d("tags", "getSignalStrength: asulevel $req1 ")

        }
    }
}
