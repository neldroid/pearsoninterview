package com.uforpablo.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uforpablo.designsystem.components.CenteredComponent
import com.uforpablo.home.ui.HomeItemComponent
import com.uforpablo.model.Item

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    isOffline: Boolean
) {
    val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val itemList by homeViewModel.itemList.collectAsStateWithLifecycle()

    HomeScreen(
        homeUiState = homeUiState,
        itemList = itemList,
        isOffline = isOffline
    ) {
        homeViewModel.fetchItemList()
    }
}

@Composable
internal fun HomeScreen(
    homeUiState: HomeUiState,
    itemList: List<Item>,
    isOffline: Boolean,
    refreshItems: () -> Unit
) {
    if ((homeUiState is HomeUiState.Error || homeUiState is HomeUiState.Idle) && itemList.isEmpty()) {
        RefreshButton(isOffline = isOffline) {
            refreshItems()
        }
    }
    when (homeUiState) {
        is HomeUiState.Error -> HomeErrorComponent(homeUiState.message)
        is HomeUiState.Idle -> ListItemComponent(itemList)
        HomeUiState.Loading -> LoadingItemComponent()
    }

}

@Composable
internal fun LoadingItemComponent() {
    CenteredComponent {
        CircularProgressIndicator(modifier = Modifier.testTag("LoadingComponent"))
    }
}

@Composable
internal fun ListItemComponent(itemList: List<Item>) {
    if (itemList.isEmpty()) {
        CenteredComponent {
            Text(text = stringResource(id = R.string.empty_list_message))
        }
    } else {
        LazyColumn(modifier = Modifier.testTag("LazyColumn")) {
            items(itemList) { item ->
                HomeItemComponent(item = item)
            }
        }
    }
}

@Composable
internal fun RefreshButton(isOffline: Boolean, refreshItems: () -> Unit) {
    if (!isOffline) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
            onClick = { refreshItems() }) {
            Text(text = stringResource(id = R.string.refresh_action))
        }
    }
}

@Composable
internal fun HomeErrorComponent(message: String?) {
    CenteredComponent {
        Text(
            text = stringResource(id = R.string.error_message_title),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Red
        )
        Text(
            text = String.format(stringResource(id = R.string.error_message_description), message),
            fontSize = 8.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}
