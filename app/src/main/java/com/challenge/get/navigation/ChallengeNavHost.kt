package com.challenge.get.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.challenge.get.home.HomeScreen
import com.challenge.get.detail.DetailScreen
import com.challenge.get.detail.LocationScreen

@Composable
fun ChallengeNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = NavRoutes.List.path) {
        composable(NavRoutes.List.path) {
            HomeScreen(hiltViewModel(), onItemClicked = {
                navController.navigate(NavRoutes.Details.build(it.toString()))
            })
        }

        composable(NavRoutes.Details.path) { backStackEntry ->
            backStackEntry.arguments?.getString(NavRoutes.DETAILS_ID_KEY)?.let {
                DetailScreen(detailViewModel(id = it), onItemClicked = { id ->
                    navController.navigate(NavRoutes.RecipeMap.build(id.toString()))
                })
            }
        }

        composable(NavRoutes.RecipeMap.path) { backStackEntry ->
            backStackEntry.arguments?.getString(NavRoutes.DETAILS_ID_KEY)?.let {
                LocationScreen(detailViewModel(id = it))
            }
        }
    }
}