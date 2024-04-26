package com.uforpablo.data.repository

import androidx.annotation.WorkerThread
import com.uforpablo.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    @WorkerThread
    fun getItems(
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Item>>

}