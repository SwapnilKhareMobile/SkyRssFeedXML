package com.example.testexampleappbasic.data.repo

import com.example.testexampleappbasic.model.RssResponse
import kotlinx.coroutines.flow.Flow

interface RssRepo {
    suspend fun getRssFeed(): Flow<RssResponse>
}