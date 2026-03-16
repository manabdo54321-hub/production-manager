package com.abdo.core.data.di

import com.abdo.core.data.dao.*
import com.abdo.core.data.repository.*
import com.abdo.core.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides @Singleton
    fun provideProductionRepository(
        productDao: ProductDao,
        productionEntryDao: ProductionEntryDao
    ): ProductionRepository = ProductionRepositoryImpl(productDao, productionEntryDao)

    @Provides @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository =
        TaskRepositoryImpl(taskDao)

    @Provides @Singleton
    fun provideFinanceRepository(
        expenseDao: ExpenseDao,
        incomeDao: IncomeDao
    ): FinanceRepository = FinanceRepositoryImpl(expenseDao, incomeDao)
}
