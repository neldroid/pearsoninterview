package com.uforpablo.network

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.message
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import com.skydoves.sandwich.retrofit.responseOf
import com.uforpablo.model.Item
import com.uforpablo.network.mapper.ItemMapper.toDomain
import com.uforpablo.network.service.ItemService
import com.uforpablo.pearsoninterview.network.BuildConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemServiceTest {

    private lateinit var server: MockWebServer
    private lateinit var service: ItemService

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(BuildConfig.API_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(
                ApiResponseCallAdapterFactory.create(
                    coroutineScope = TestScope(UnconfinedTestDispatcher()),
                ),
            )
            .build().create(ItemService::class.java)
    }

    @Test
    fun testItemServiceResponse() = runTest {
        val response = service.listItems()
        val responseBody = requireNotNull((response as ApiResponse.Success).data)

        assertEquals(responseBody.dataCollection.size,100)
        val items = responseBody.dataCollection.toDomain()
        assertEquals(items[0].id, "1")
        assertEquals(items[0].name, "Item Name 1")
        assertEquals(items[0].description, "Description of Item 1")
        assertEquals(items[0].imageUrl, "https://picsum.photos/id/101/300/200")
    }

    @After
    fun finish() {
        server.shutdown()
    }

}
