package com.uforpablo.home

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.uforpablo.data.repository.ItemRepository
import com.uforpablo.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val trigger = MutableSharedFlow<Unit>(replay = 1)

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    internal val homeUiState: StateFlow<HomeUiState> = _homeUiState

    @OptIn(ExperimentalCoroutinesApi::class)
    val itemList: StateFlow<List<Item>> = trigger.flatMapLatest {
        _homeUiState.tryEmit(HomeUiState.Loading)

        itemRepository.getItems({ _homeUiState.tryEmit(HomeUiState.Idle) },
            { error -> _homeUiState.tryEmit(HomeUiState.Error(error)) })
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            trigger.emit(Unit)
        }
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data object Idle : HomeUiState

    data class Error(val message: String?) : HomeUiState
}