package com.uforpablo.data

import app.cash.turbine.test
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.retrofit.responseOf
import com.uforpablo.data.repository.ItemRepositoryImpl
import com.uforpablo.network.datasource.ItemsRemoteDataSourceImpl
import com.uforpablo.network.model.Attributes
import com.uforpablo.network.model.DataCollection
import com.uforpablo.network.model.ImageInfo
import com.uforpablo.network.model.ItemAPI
import com.uforpablo.network.model.ItemObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class ItemRepositoryTest {

    private lateinit var repository: ItemRepositoryImpl
    private val dataSource: ItemsRemoteDataSourceImpl = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        repository = ItemRepositoryImpl(dataSource, dispatcher = UnconfinedTestDispatcher())
    }

    @Test
    fun fetchItemInformationTest() = runTest {
        val mockData = mockData()
        whenever(dataSource.getItems()).thenReturn(
            ApiResponse.responseOf {
                Response.success(
                    mockData
                )
            }
        )

        repository.getItems(onComplete = {}, onError = {}).test {
            val actualItem = awaitItem()
            assert(actualItem.isNotEmpty())
            assertEquals(mockData.dataCollection[0].item.id, actualItem[0].id)
            assertEquals(mockData.dataCollection[0].item.attributes.name, actualItem[0].name)
            assertEquals(
                mockData.dataCollection[0].item.attributes.description,
                actualItem[0].description
            )
            assertEquals(
                mockData.dataCollection[0].item.attributes.imageInfo.imageUrl,
                actualItem[0].imageUrl
            )
            awaitComplete()
        }
    }

}

private fun mockData() = DataCollection(
    listOf(
        ItemObject(
            ItemAPI(
                id = "1",
                attributes = Attributes(
                    name = "name",
                    description = "description",
                    imageInfo = ImageInfo(
                        imageUrl = "imageUrl"
                    )
                )
            )
        )
    )
)



