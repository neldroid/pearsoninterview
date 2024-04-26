package com.uforpablo.network.datasource

import com.skydoves.sandwich.ApiResponse
import com.uforpablo.network.model.DataCollection

interface ItemsRemoteDataSource {

    suspend fun getItems(): ApiResponse<DataCollection>

}