package com.abdo.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdo.core.data.dao.ProductDao
import com.abdo.core.data.dao.ProductionEntryDao
import com.abdo.core.data.dao.TaskDao
import com.abdo.core.data.entity.ProductEntity
import com.abdo.core.data.entity.ProductionEntryEntity
import com.abdo.core.data.entity.TaskEntity

@Database(
    entities = [
        ProductEntity::class,
        ProductionEntryEntity::class,
        TaskEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productionEntryDao(): ProductionEntryDao
    abstract fun taskDao(): TaskDao
}
