package com.example.testexampleappbasic.domain

import com.example.testexampleappbasic.model.Item
import kotlinx.coroutines.flow.Flow

class FakeRssUseCase : RssUseCase {
    private lateinit var flow: Flow<List<Item>>

    fun setFlow(flow: Flow<List<Item>>) {
        this.flow = flow
    }

    override suspend fun invoke(): Flow<List<Item>> {
        return flow
    }
}