package com.core.domain.di

import com.core.domain.usecase.GetLeaguesUseCase
import com.core.domain.usecase.GetLeaguesUseCaseImpl
import com.core.domain.usecase.GetTeamDetailsUseCase
import com.core.domain.usecase.GetTeamDetailsUseCaseImpl
import com.core.domain.usecase.GetTeamsUseCase
import com.core.domain.usecase.GetTeamsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DomainModule2 {

    @Binds
    @Singleton
    abstract fun bindGetLeaguesUseCase(getLeaguesUseCaseImpl: GetLeaguesUseCaseImpl): GetLeaguesUseCase

    @Binds
    @Singleton
    abstract fun bindGetTeamsUseCase(getTeamsUseCaseImpl: GetTeamsUseCaseImpl): GetTeamsUseCase

    @Binds
    @Singleton
    abstract fun bindGetTeamDetailsUseCase(getTeamDetailsCaseImpl: GetTeamDetailsUseCaseImpl): GetTeamDetailsUseCase
}