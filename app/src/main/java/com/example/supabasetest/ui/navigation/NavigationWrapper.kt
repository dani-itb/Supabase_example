package com.example.supabasetest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.supabasetest.ui.screens.StudentDetailScreen
import com.example.supabasetest.ui.screens.StudentsScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController, List) {
        composable<List> {
            StudentsScreen { idStudent ->
                navController.navigate(Detail(idStudent))
            }
        }
        composable<Detail> { backStackEntry ->
            val detalle = backStackEntry.toRoute<Detail>()
            StudentDetailScreen(detalle.id) {
                navController.navigate(List) {
                    popUpTo<List> { inclusive = true }
                }
            }
        }
    }
}