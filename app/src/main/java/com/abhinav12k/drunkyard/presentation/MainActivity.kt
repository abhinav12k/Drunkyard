package com.abhinav12k.drunkyard.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhinav12k.drunkyard.presentation.drinkList.DrinkListScreen
import com.abhinav12k.drunkyard.presentation.ui.theme.DrunkyardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrunkyardTheme {
                drunkyardMainScreen()
            }
        }
    }
}

@Composable
fun drunkyardMainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.DrinkListScreen.route) {

        composable(route = NavScreen.DrinkListScreen.route) {
            DrinkListScreen(viewModel = hiltViewModel())
        }

        composable(route = NavScreen.DrinkDetailScreen.route) {

        }

    }

}

sealed class NavScreen(val route: String) {
    object DrinkListScreen : NavScreen("DrinkListScreen")
    object DrinkDetailScreen : NavScreen("DrinkDetailScreen") {
        const val routeWithArgument: String = "DrinkDetailScreen/{drinkId}"
        const val argument0: String = "drinkId"
    }
}