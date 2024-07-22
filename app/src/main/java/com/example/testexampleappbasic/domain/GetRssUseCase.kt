package com.example.testexampleappbasic.domain

import com.example.testexampleappbasic.data.repo.RssRepo
import com.example.testexampleappbasic.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRssUseCase @Inject constructor(private val rssRepo: RssRepo):RssUseCase {

    override suspend operator fun invoke():Flow<List<Item>> = flow {
        rssRepo.getRssFeed().map {
             it.channel?.items ?: emptyList()

        }.collect{
            emit(it)
        }
    }
}