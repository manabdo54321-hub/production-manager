package com.abdo.core.data.di

import com.abdo.core.data.dao.ProductDao
import com.abdo.core.data.dao.ProductionEntryDao
import com.abdo.core.data.dao.TaskDao
import com.abdo.core.data.repository.ProductionRepositoryImpl
import com.abdo.core.data.repository.TaskRepositoryImpl
import com.abdo.core.domain.repository.ProductionRepository
import com.abdo.core.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductionRepository(
        productDao: ProductDao,
        productionEntryDao: ProductionEntryDao
    ): ProductionRepository {
        return ProductionRepositoryImpl(productDao, productionEntryDao)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskDao: TaskDao
    ): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }
}
