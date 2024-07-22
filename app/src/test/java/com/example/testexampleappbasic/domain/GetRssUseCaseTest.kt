package com.example.testexampleappbasic.domain

import com.example.testexampleappbasic.data.repo.FakeRssRepo
import com.example.testexampleappbasic.model.Channel
import com.example.testexampleappbasic.model.Item
import com.example.testexampleappbasic.model.RssResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetRssUseCaseTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var getRssUseCase: GetRssUseCase
    private lateinit var fakeRssRepo: FakeRssRepo

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRssRepo = FakeRssRepo()
        getRssUseCase = GetRssUseCase(fakeRssRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should emit items from rssRepo`() = runTest {
        val items = listOf(Item("Title1", "Description1"), Item("Title2", "Description2"))
        val rssResponse = RssResponse(channel = Channel("","",items))
        val flow: Flow<RssResponse> = flowOf(rssResponse)

        fakeRssRepo.setFlow(flow)

        val expectedItems = listOf(items)
        val actualItems = getRssUseCase().toList()

        assertEquals(expectedItems, actualItems)
    }

    @Test
    fun `invoke should emit empty list when rssRepo returns null items`() = runTest {
        val rssResponse = RssResponse(channel = Channel(null))
        val flow: Flow<RssResponse> = flowOf(rssResponse)

        fakeRssRepo.setFlow(flow)

        val expectedItems = listOf(emptyList<Item>())
        val actualItems = getRssUseCase().toList()

        assertEquals(expectedItems, actualItems)
    }

    @Test
    fun `invoke should handle empty feed`() = runTest {
        val rssResponse = RssResponse(channel = Channel("","",emptyList()))
        val flow: Flow<RssResponse> = flowOf(rssResponse)

        fakeRssRepo.setFlow(flow)

        val expectedItems = listOf( emptyList<Item>())
        val actualItems = getRssUseCase().toList()

        assertEquals(expectedItems, actualItems)
    }
}