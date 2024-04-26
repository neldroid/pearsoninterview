package com.uforpablo.network.datasource

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onSuccess
import com.uforpablo.model.Item
import com.uforpablo.network.model.DataCollection
import com.uforpablo.network.service.ItemService
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemsRemoteDataSourceImpl @Inject constructor(
    private val retrofit: Retrofit
) : ItemsRemoteDataSource {

    override suspend fun getItems(): ApiResponse<DataCollection> =
        retrofit.create(ItemService::class.java).listItems()
}