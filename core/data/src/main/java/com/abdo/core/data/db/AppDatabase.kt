package com.abdo.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdo.core.data.dao.ProductDao
import com.abdo.core.data.dao.ProductionEntryDao
import com.abdo.core.data.entity.ProductEntity
import com.abdo.core.data.entity.ProductionEntryEntity

@Database(
    entities = [
        ProductEntity::class,
        ProductionEntryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productionEntryDao(): ProductionEntryDao
}
