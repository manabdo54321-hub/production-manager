package com.abdo.core.data.dao

import androidx.room.*
import com.abdo.core.data.entity.IncomeEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface IncomeDao {
    @Query("SELECT * FROM incomes ORDER BY date DESC")
    fun getAllIncomes(): Flow<List<IncomeEntity>>

    @Query("SELECT * FROM incomes WHERE date BETWEEN :from AND :to ORDER BY date DESC")
    fun getIncomesBetween(from: LocalDate, to: LocalDate): Flow<List<IncomeEntity>>

    @Query("SELECT SUM(amount) FROM incomes WHERE date BETWEEN :from AND :to")
    suspend fun getTotalBetween(from: LocalDate, to: LocalDate): Double?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: IncomeEntity): Long

    @Delete
    suspend fun deleteIncome(income: IncomeEntity)
}
