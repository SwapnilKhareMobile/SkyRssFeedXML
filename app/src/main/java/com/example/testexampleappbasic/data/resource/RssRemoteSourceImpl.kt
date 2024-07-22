package com.example.testexampleappbasic.data.resource

import com.example.testexampleappbasic.model.RssResponse
import com.example.testexampleappbasic.network.APIHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RssRemoteSourceImpl @Inject constructor (private val apiHelper: APIHelper) : RssRemoteSource {
    override suspend fun getFeed(): Flow<RssResponse> = flow {
        emit(apiHelper.getRssFeed())
    }.flowOn(Dispatchers.IO)
}