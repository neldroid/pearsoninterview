package com.uforpablo.pearsoninterview.ui

import android.app.Instrumentation.ActivityMonitor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.uforpablo.data.util.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): PearsonInterviewAppState {

    return remember(
        networkMonitor,
        coroutineScope
    ) {
        PearsonInterviewAppState(networkMonitor, coroutineScope)
    }

}

@Stable
class PearsonInterviewAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope
) {

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

}