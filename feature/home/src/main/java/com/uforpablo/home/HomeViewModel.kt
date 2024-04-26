package com.uforpablo.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uforpablo.data.repository.ItemRepository
import com.uforpablo.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    internal val uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)

    private val itemFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    val itemList: StateFlow<List<Item>> = itemFetchingIndex.flatMapLatest {
        itemRepository.getItems(
            onComplete = { uiState.tryEmit(HomeUiState.Idle) },
            onError = { uiState.tryEmit(HomeUiState.Error(it)) },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    fun fetchItemList() {
        if (uiState.value != HomeUiState.Loading) {
            itemFetchingIndex.value++
        }
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data object Idle : HomeUiState

    data class Error(val message: String?) : HomeUiState
}