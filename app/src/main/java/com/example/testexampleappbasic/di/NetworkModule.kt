package com.example.testexampleappbasic.di

import com.example.testexampleappbasic.network.APIHelper
import com.example.testexampleappbasic.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): APIHelper {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(networkLoggingInterceptor())
        @Suppress("DEPRECATION for Java")
        return Retrofit.Builder()
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(okHttpClient.build())
            .baseUrl(BASE_URL)
            .build()
            .create(APIHelper::class.java)
    }
    private fun networkLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor
    }

}