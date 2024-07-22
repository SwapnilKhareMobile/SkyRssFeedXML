package com.example.testexampleappbasic.data.resource

import com.example.testexampleappbasic.model.RssResponse
import kotlinx.coroutines.flow.Flow

interface RssRemoteSource {

    suspend fun getFeed():Flow<RssResponse>
}