package com.example.testexampleappbasic.ui.screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testexampleappbasic.domain.FakeRssUseCase
import com.example.testexampleappbasic.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var rssUseCase: FakeRssUseCase
    private lateinit var viewModel: MainViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        rssUseCase = FakeRssUseCase()
        rssUseCase.setFlow(flow { emit(emptyList<Item>()) })
        viewModel = MainViewModel(rssUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetching RSS data, state should be empty initially`() = runTest {
        // Given
        val emptyFlow = flow<List<Item>> { emit(emptyList()) }
        rssUseCase.setFlow(emptyFlow)

        // When
        viewModel.fetchRssData()

        // Then
        assertEquals(MainUIState.Success(emptyList()), viewModel.mRssList.value)
    }
    @Test
    fun `fetchRssData sets loading state then error state when an error occurs`() = runTest {
        // Given
        val errorFlow = flow<List<Item>> { throw RuntimeException("Test Exception") }
        rssUseCase.setFlow(errorFlow)

        // When
        viewModel.fetchRssData()



        // Then - Loading State
        assertEquals(MainUIState.Success(emptyList()), viewModel.mRssList.value)

        // Advance dispatcher to complete coroutines
        advanceUntilIdle()

        // Then - Error State
        assertTrue(viewModel.mRssList.value is MainUIState.Error)
    }

    @Test
    fun `fetchRssData sets loading state then success state when data is successfully fetched`() = runTest {
        // Given
        val items = listOf(Item(title = "Title", link = "http://example.com"))
        val successFlow = flow { emit(items) }
        rssUseCase.setFlow(successFlow)

        // When
        viewModel.fetchRssData()

        // Then - Loading State
        assertEquals(MainUIState.Success(emptyList()), viewModel.mRssList.value)

        // Advance dispatcher to complete coroutines
        advanceUntilIdle()

        // Then - Success State
        assertTrue(viewModel.mRssList.value is MainUIState.Success)
        assertEquals(items, (viewModel.mRssList.value as MainUIState.Success).data)
    }
}