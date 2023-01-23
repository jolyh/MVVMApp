package com.example.testmvvm

import android.Manifest
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.testmvvm.screen.ChatScreen
import com.example.testmvvm.screen.SettingsScreen
import com.example.testmvvm.ui.theme.TestMVVMTheme
import com.example.testmvvm.viewModel.ChatViewModel
import com.example.testmvvm.viewModel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TestMVVMApplication : Application() {}

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requiredPermissions =
            arrayOf<String>(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE
            )
        ActivityCompat.requestPermissions(this, requiredPermissions, 0)

        val screenViewModel: SettingsViewModel by viewModels()
        val chatViewModel : ChatViewModel by viewModels()

        setContent {
            TestMVVMTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    //SettingsScreen(screenViewModel)
                    ChatScreen(chatViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestMVVMTheme {

    }
}