package com.uforpablo.data.repository

import com.uforpablo.model.Item
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestItemRepository : ItemRepository {

    private val itemsFlow: MutableSharedFlow<List<Item>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    fun sendItems(items: List<Item>) {
        itemsFlow.tryEmit(items)
    }

    override fun getItems(onComplete: () -> Unit, onError: (String?) -> Unit): Flow<List<Item>> =
        itemsFlow

}