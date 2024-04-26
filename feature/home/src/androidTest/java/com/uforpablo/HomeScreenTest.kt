package com.uforpablo

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.uforpablo.home.HomeErrorComponent
import com.uforpablo.home.ListItemComponent
import com.uforpablo.home.LoadingItemComponent
import com.uforpablo.home.R
import com.uforpablo.home.RefreshButton
import com.uforpablo.model.Item
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testLoadingComponentShowCircularLoadingComponent() {
        composeTestRule.setContent {
            LoadingItemComponent()
        }

        composeTestRule.onNodeWithTag("LoadingComponent").assertExists()
    }

    @Test
    fun testErrorComponentShowErrorTitleAndMessage() {
        val error = "Error message"
        composeTestRule.setContent { HomeErrorComponent(message = error) }

        val errorTitle = composeTestRule.activity.getString(R.string.error_message_title)
        composeTestRule.onNodeWithText(errorTitle).assertExists()

        val errorMessage = String.format(composeTestRule.activity.getString(R.string.error_message_description), error)
        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }

    @Test
    fun testListComponentWithEmptyListShowMessage() {
        composeTestRule.setContent { ListItemComponent(itemList = emptyList())}

        val emptyMessage = composeTestRule.activity.getString(R.string.empty_list_message)
        composeTestRule.onNodeWithText(emptyMessage).assertExists()
    }

    @Test
    fun testRefreshButtonCallbackAction() {
        var clicked = false
        val callback: () -> Unit = { clicked = true}
        composeTestRule.setContent { RefreshButton(isOffline = false, refreshItems = callback) }

        val button = composeTestRule.activity.getString(R.string.refresh_action)
        composeTestRule.onNodeWithText(button).assertExists().performClick()

        assertTrue(clicked)
    }

    @Test
    fun testListComponentWithItemsShowLazyColumnComponent(){
        composeTestRule.setContent { ListItemComponent(itemList = listOf(Item("", "", "", ""))) }

        composeTestRule.onNodeWithTag("LazyColumn").assertExists()
    }

}