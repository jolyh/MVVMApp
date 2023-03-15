package com.example.testmvvm.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testmvvm.viewModel.ChatViewModel
import com.example.testmvvm.screen.HomeScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.testmvvm.screen.ChatScreen
import com.example.testmvvm.screen.ConversationListScreen
import com.example.testmvvm.screen.SettingsScreen
import com.example.testmvvm.viewModel.ConversationListViewModel
import com.example.testmvvm.viewModel.SettingsViewModel

@Composable
fun AppNavigation(
    navController : NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.HOME_ROUTE,
    routes: AppDestinations = AppDestinations
){

    val LOG_TAG = "[AppNavigation]"

    val actions = remember(navController) {
        AppActions(navController, routes)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            AppDestinations.HOME_ROUTE
        ) {
            HomeScreen(
                onSettingsClick = { actions.navigateToSettings() },
                onChatClick = { chatId -> actions.navigateToChat(chatId) },
                onConversationListClick = { userId ->
                    Log.d(LOG_TAG, "userID : $userId")
                    actions.navigateToConversationList(userId)
                }
            )
        }

        composable(
            route = "${AppDestinations.CONVERSATION_LIST_ROUTE}/{${AppDestinations.USER_ID}}",
            arguments = listOf(navArgument(AppDestinations.USER_ID) { type = NavType.StringType })
        ){
            val conversationListViewModel = hiltViewModel<ConversationListViewModel>()
            ConversationListScreen(
                viewModel = conversationListViewModel,
                onConversationClick = { actions.navigateToChat(it) }
            )
        }

        composable(
            route = "${AppDestinations.CHAT_ROUTE}/{${AppDestinations.CHAT_ID}}",
            arguments = listOf(navArgument(AppDestinations.CHAT_ID) { type = NavType.StringType })
        ){
            val chatViewModel = hiltViewModel<ChatViewModel>()
            ChatScreen(viewModel = chatViewModel)
        }

        composable(
            route = AppDestinations.SETTINGS_ROUTE
        ){
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            SettingsScreen(viewModel = settingsViewModel)
        }

    }
}