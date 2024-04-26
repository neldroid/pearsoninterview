package com.uforpablo.data.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.uforpablo.model.Item
import com.uforpablo.network.datasource.ItemsRemoteDataSource
import com.uforpablo.network.mapper.ItemMapper.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemsRemoteDataSource: ItemsRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ItemRepository {

    @WorkerThread
    override fun getItems(onComplete: () -> Unit, onError: (String?) -> Unit) =
        flow {
            val response = itemsRemoteDataSource.getItems()
            response.suspendOnSuccess {
                emit(data.dataCollection.toDomain())
            }.onFailure {
                onError(message())
            }
        }.onCompletion { onComplete() }.flowOn(dispatcher)
}