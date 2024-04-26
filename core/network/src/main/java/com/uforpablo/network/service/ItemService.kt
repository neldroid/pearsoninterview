package com.uforpablo.network.service

import com.skydoves.sandwich.ApiResponse
import com.uforpablo.network.model.DataCollection
import retrofit2.http.GET

interface ItemService {

    @GET("mondly_android_code_task_json")
    suspend fun listItems(): ApiResponse<DataCollection>

}