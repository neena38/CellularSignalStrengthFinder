package com.example.cellularsignalstrengthfinder

import android.telephony.PhoneStateListener
import android.telephony.SignalStrength


class MyPhoneStateListener : PhoneStateListener() {
    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
        super.onSignalStrengthsChanged(signalStrength)
         var mSignalStrength = signalStrength.gsmSignalStrength
        mSignalStrength = 2 * mSignalStrength - 113 // -> dBm
    }
}