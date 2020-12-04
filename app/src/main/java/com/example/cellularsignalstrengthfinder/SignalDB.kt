package com.example.cellularsignalstrengthfinder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SignalRaw::class], version = 1)
abstract class SignalDatabase : RoomDatabase() {
    abstract fun signalDao(): SignalDao

    companion object {
        @Volatile
        private var INSTANCE: SignalDatabase? = null
        fun getDatabase(context: Context): SignalDatabase? {
            if (INSTANCE == null) {
                synchronized(SignalDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        SignalDatabase::class.java, "Signal_Strength.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}