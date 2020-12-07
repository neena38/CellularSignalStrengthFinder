package com.example.cellularsignalstrengthfinder

import androidx.room.*

@Entity
data class WifiRaw(
    @PrimaryKey val timeStamp: Long,
    @ColumnInfo(name = "strength") val strength: Int?,
    @ColumnInfo(name = "level") val level: Int?
)

@Dao
interface WifiDao {
    @Query("SELECT * FROM wifiraw")
    fun getAll(): List<WifiRaw>

    @Insert
    fun insertAll(vararg wifiRaw: WifiRaw)

    @Delete
    fun delete(wifiRaw: WifiRaw)
}

@Entity
data class CellularRaw(
    @PrimaryKey val timeStamp: Long,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "strength") val strength: Int?,
    @ColumnInfo(name = "level") val level: Int?,
    @ColumnInfo(name = "asuLevel") val asuLevel: Int?
)

@Dao
interface CellularDao {
    @Query("SELECT * FROM cellularraw")
    fun getAll(): List<CellularRaw>

    @Insert
    fun insertAll(vararg cellularRaw: CellularRaw)

    @Delete
    fun delete(cellularRaw: CellularRaw)
}