package com.example.cellularsignalstrengthfinder

import androidx.room.*

@Entity
data class SignalRaw(
    @PrimaryKey val timeStamp: Long,
    @ColumnInfo(name = "connectivityType") val connectivityType: String?,
    @ColumnInfo(name = "signalStrength") val signalStrength: Int?,
    @ColumnInfo(name = "level") val level: Int?
)

@Dao
interface SignalDao {
    @Query("SELECT * FROM signalraw")
    fun getAll(): List<SignalRaw>

    @Insert
    fun insertAll(vararg signalRaw: SignalRaw)

    @Delete
    fun delete(signalRaw: SignalRaw)
}