package com.abdo.core.data.di

import android.content.Context
import androidx.room.Room
import com.abdo.core.data.dao.ProductDao
import com.abdo.core.data.dao.ProductionEntryDao
import com.abdo.core.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "production_manager.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()

    @Provides
    @Singleton
    fun provideProductionEntryDao(db: AppDatabase): ProductionEntryDao = db.productionEntryDao()
}
