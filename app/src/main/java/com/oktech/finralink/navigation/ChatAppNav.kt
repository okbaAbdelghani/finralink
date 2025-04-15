package com.oktech.finralink.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oktech.finralink.ui.screens.ChatDetailsScreen
import com.oktech.finralink.ui.screens.ChatListScreen

@Composable
fun ChatAppNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "chat_list") {
        composable("chat_list") {
            ChatListScreen (navController)
        }

        composable(
            route = "chat_details/{contactName}",
            arguments = listOf(navArgument("contactName") {type = NavType.StringType})
        ) { backStackEntry ->
            val contactName = backStackEntry.arguments?.getString("contactName") ?: ""
            ChatDetailsScreen(contactName)
        }
    }
}