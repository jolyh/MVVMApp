package com.example.testmvvm.navigation

import androidx.navigation.NavHostController

/**
 * @property navController helps us navigate by performing action.
 * @property routes destinations to navigate once action is triggered.
 */
class AppActions(
    private val navController: NavHostController,
    private val routes: AppDestinations
) {

    val navigateToHome: () -> Unit = {
        navController.navigate(routes.HOME_ROUTE)
    }

    val navigateToConversationList: (String) -> Unit = { userId: String ->
        navController.navigate("${routes.CONVERSATION_LIST_ROUTE}/$userId")
    }

    // Triggered when user tries to navigate to a chat
    val navigateToChat: (String) -> Unit = { chatId: String ->
        navController.navigate("${routes.CHAT_ROUTE}/$chatId")
    }

    val navigateToSettings: () -> Unit = {
        navController.navigate(routes.SETTINGS_ROUTE)
    }

}