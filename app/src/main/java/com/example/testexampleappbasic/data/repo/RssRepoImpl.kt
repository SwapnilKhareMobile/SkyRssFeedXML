package com.example.testexampleappbasic.data.repo

import com.example.testexampleappbasic.data.resource.RssRemoteSource
import com.example.testexampleappbasic.model.RssResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RssRepoImpl @Inject constructor(private val rssRemoteDataSource: RssRemoteSource):RssRepo {
    override suspend fun getRssFeed(): Flow<RssResponse> {
        return rssRemoteDataSource.getFeed()
    }
}