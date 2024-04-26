package com.uforpablo.designsystem.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.uforpablo.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PITopBar(){
    TopAppBar(
        title = { Text(stringResource(R.string.topbar_title), fontWeight = FontWeight.Bold) }
    )
}