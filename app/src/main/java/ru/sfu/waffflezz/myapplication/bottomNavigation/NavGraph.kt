package ru.sfu.waffflezz.myapplication.bottomNavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.sfu.waffflezz.myapplication.viewmodels.CartViewModel
import ru.sfu.waffflezz.myapplication.viewmodels.MapViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController,
    innerPadding: PaddingValues,
    cartViewModel: CartViewModel,
    mapViewModel: MapViewModel,
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.HomeScreenRoute,
        Modifier.padding(innerPadding)
    ) {
        composable(Routes.HomeScreenRoute) {
            HomeScreen(
                navHostController,
                cartViewModel
            )
        }
        composable(Routes.CartScreenRoute) {
            CartScreen(
                navHostController,
                cartViewModel
            )
        }
        composable(Routes.MapScreenRoute) {
            MapScreen(
                navHostController,
                mapViewModel,
                cartViewModel
            )
        }
        composable(Routes.HistoryScreenRoute) {
            HistoryScreen(
                navHostController,
                cartViewModel
            )
        }
        composable(Routes.ConfirmTheOrderRoute) {
            ConfirmTheOrderScreen(
                navHostController,
                cartViewModel
            )
        }
        composable(
            Routes.HistoryEditScreenRouteGraph,
            arguments = listOf(navArgument(Routes.HistoryEditRouteArgument) { type = NavType.StringType })
        ) { backStackEntry ->
            HistoryEditScreen(
                backStackEntry.arguments?.getString(Routes.HistoryEditRouteArgument)!!.toInt(),
                cartViewModel,
                navHostController
            )
        }
    }
}