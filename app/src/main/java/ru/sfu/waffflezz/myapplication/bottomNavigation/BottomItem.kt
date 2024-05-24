package ru.sfu.waffflezz.myapplication.bottomNavigation


import ru.sfu.waffflezz.myapplication.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    object HomeScreenItem: BottomItem("Home", R.drawable.home, Routes.HomeScreenRoute)
    object CartScreenItem: BottomItem("Cart", R.drawable.shopping_cart, Routes.CartScreenRoute)
    object MapScreenItem: BottomItem("Map", R.drawable.map, Routes.MapScreenRoute)
    object HistoryScreenItem: BottomItem("History", R.drawable.history, Routes.HistoryScreenRoute)
}