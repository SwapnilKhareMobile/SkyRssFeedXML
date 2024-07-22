package com.example.testexampleappbasic.domain

import com.example.testexampleappbasic.model.Item
import kotlinx.coroutines.flow.Flow

interface RssUseCase {

    suspend operator fun invoke(): Flow<List<Item>>
}