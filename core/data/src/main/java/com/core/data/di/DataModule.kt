package com.core.data.di

import com.core.data.repository.SportsRepositoryImpl
import com.core.domain.repository.SportsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindSportsRepository(sportsRepositoryImpl: SportsRepositoryImpl): SportsRepository
}
