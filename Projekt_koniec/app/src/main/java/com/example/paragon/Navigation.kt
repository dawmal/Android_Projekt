package com.example.paragon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Start"
    ) {
        composable("Start"){
            Start(modifier, navController)
        }
        composable("TextOutput") {
            TextOutput(modifier, navController)
        }
        composable("Category"){
            Category(modifier, navController)
        }
    }
}

