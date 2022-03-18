package com.nikosnockoffs.android.hydration

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao // data access object
interface WaterDao {

    // no code, just functions and then Room writes code
    @Insert(onConflict = OnConflictStrategy.REPLACE) // to make testing easier - if try to insert 2 records for a day, 2nd will replace 1st
    suspend fun insert(record: WaterRecord) // room will create a class that implements this interface

    @Update
    suspend fun update(record: WaterRecord)

    @Delete
    suspend fun delete(record: WaterRecord)

    @Query("SELECT * FROM WaterRecord WHERE day = :day LIMIT 1")
    fun getRecordForDay(day: String): Flow<WaterRecord>

    @Query("SELECT * FROM WaterRecord")
    fun getAllRecords(): Flow<List<WaterRecord>>

}