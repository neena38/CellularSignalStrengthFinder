package com.example.cellularsignalstrengthfinder


import androidx.room.*

@Entity
data class CellularSignalRaw (
    @PrimaryKey val timeStamp: Long,
    @ColumnInfo(name = "level") val level: Int?,
    @ColumnInfo(name = "plugged") val plugged: Int?,
    @ColumnInfo(name = "temp") val temp: Int?,
    @ColumnInfo(name = "health") val health: Int?,
    @ColumnInfo(name = "estimatedCapacity") val estimatedCapacity: Int,
    @ColumnInfo(name = "estimatedAccuracy") val estimatedAccuracy: Int?
)

@Dao
interface CellularSignalDao {
    @Query("SELECT * FROM cellularsignalraw")
    fun getAll(): List<CellularSignalRaw>

    @Insert
    fun insertAll(vararg cellularSignalRaw: CellularSignalRaw)

    @Delete
    fun delete(cellularSignalRaw: CellularSignalRaw)
}


@Database(entities = [CellularSignalRaw::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cellularSignalDao(): CellularSignalDao
}