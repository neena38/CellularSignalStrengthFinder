package com.example.cellularsignalstrengthfinder


import androidx.room.*

@Entity
data class CellularSignalRaw (
    @PrimaryKey val timeStamp: Long,
    @ColumnInfo(name = "mCqi") val mCqi: Int?,
    @ColumnInfo(name = "mLevel") val mLevel: Int?,
    @ColumnInfo(name = "mRsrp") val mRsrp: Int?,
    @ColumnInfo(name = "mRsrq") val mRsrq: Int?,
    @ColumnInfo(name = "mRssi") val mRssi: Int?,
    @ColumnInfo(name = "mRssnr") val mRssnr: Int?,
    @ColumnInfo(name = "mTimingAdvance") val mTimingAdvance: Int?
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