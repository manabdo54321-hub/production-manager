package com.abdo.core.data.dao

import androidx.room.*
import com.abdo.core.data.entity.ProductionEntryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ProductionEntryDao {

    @Query("""
        SELECT * FROM production_entries 
        WHERE date = :date 
        ORDER BY id DESC
    """)
    fun getEntriesByDate(date: LocalDate): Flow<List<ProductionEntryEntity>>

    @Query("""
        SELECT * FROM production_entries 
        WHERE date BETWEEN :from AND :to 
        ORDER BY date DESC
    """)
    fun getEntriesBetween(from: LocalDate, to: LocalDate): Flow<List<ProductionEntryEntity>>

    @Query("""
        SELECT * FROM production_entries 
        ORDER BY date DESC, id DESC
    """)
    fun getAllEntries(): Flow<List<ProductionEntryEntity>>

    @Query("""
        SELECT SUM(quantity) FROM production_entries 
        WHERE date = :date
    """)
    suspend fun getTotalQuantityByDate(date: LocalDate): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: ProductionEntryEntity): Long

    @Update
    suspend fun updateEntry(entry: ProductionEntryEntity)

    @Delete
    suspend fun deleteEntry(entry: ProductionEntryEntity)
}
