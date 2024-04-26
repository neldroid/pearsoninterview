package com.uforpablo.pearsoninterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uforpablo.data.util.NetworkMonitor
import com.uforpablo.home.HomeScreen
import com.uforpablo.pearsoninterview.ui.PearsonInterviewApp
import com.uforpablo.designsystem.theme.PearsonInterviewTheme
import com.uforpablo.pearsoninterview.ui.rememberAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PearsonInterviewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val appState = rememberAppState(networkMonitor = networkMonitor)

                    PearsonInterviewApp(appState)
                }
            }
        }
    }
}