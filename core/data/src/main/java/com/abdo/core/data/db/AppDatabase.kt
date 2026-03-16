package com.abdo.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdo.core.data.dao.*
import com.abdo.core.data.entity.*

@Database(
    entities = [
        ProductEntity::class,
        ProductionEntryEntity::class,
        TaskEntity::class,
        ExpenseEntity::class,
        IncomeEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productionEntryDao(): ProductionEntryDao
    abstract fun taskDao(): TaskDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun incomeDao(): IncomeDao
}
