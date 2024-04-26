package com.uforpablo.home

import app.cash.turbine.test
import com.uforpablo.data.repository.TestItemRepository
import com.uforpablo.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockRepository = TestItemRepository()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(mockRepository)
    }

    @Test
    fun testLoadingState() = runTest {
        mockRepository.sendItems(emptyList())

        viewModel.homeUiState.test {
            viewModel.refresh()

            expectMostRecentItem() is HomeUiState.Loading

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testIdleState() = runTest {
        val items = listOf(Item("", "", "", ""), Item("", "", "", ""))
        mockRepository.sendItems(items)

        viewModel.homeUiState.test {
            viewModel.refresh()

            expectMostRecentItem() is HomeUiState.Loading

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testLoadingItems() = runTest {
        val items = listOf(
            Item("1", "name", "description", "imageUrl"),
            Item("2", "name", "description", "imageUrl")
        )
        mockRepository.sendItems(items)

        viewModel.refresh()

        val itemList = viewModel.itemList.first()

        assert(itemList.size == 2)
        assertEquals(itemList[0].id, "1")
        assertEquals(itemList[1].id, "2")
    }

}