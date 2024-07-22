package com.example.testexampleappbasic.data.repo

import com.example.testexampleappbasic.model.RssResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRssRepo : RssRepo {
    private var flow: Flow<RssResponse> = flowOf(RssResponse()) // Default empty flow

    fun setFlow(flow: Flow<RssResponse>) {
        this.flow = flow
    }

    override suspend fun getRssFeed(): Flow<RssResponse> = flow
}