package com.example.testexampleappbasic.di

import com.example.testexampleappbasic.data.repo.RssRepo
import com.example.testexampleappbasic.data.repo.RssRepoImpl
import com.example.testexampleappbasic.data.resource.RssRemoteSource
import com.example.testexampleappbasic.data.resource.RssRemoteSourceImpl
import com.example.testexampleappbasic.domain.GetRssUseCase
import com.example.testexampleappbasic.domain.RssUseCase
import com.example.testexampleappbasic.network.APIHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideResource( apiHelper: APIHelper): RssRemoteSource {
        return RssRemoteSourceImpl(apiHelper)
    }

    @Singleton
    @Provides
    fun provideRepository(rssRemoteSource: RssRemoteSource): RssRepo {
        return RssRepoImpl(rssRemoteSource)
    }

    @Singleton
    @Provides
    fun provideUseCase(rssRepo: RssRepo): RssUseCase {
        return GetRssUseCase(rssRepo)
    }
}