package ru.sfu.waffflezz.myapplication.bottomNavigation

class Routes {
    companion object {
        const val HomeScreenRoute = "home_screen"
        const val CartScreenRoute = "cart_screen"
        const val MapScreenRoute = "map_screen"
        const val HistoryScreenRoute = "history_screen"

        const val ConfirmTheOrderRoute = "confirm_the_order"

        const val HistoryEditRouteArgument = "orderId"
        const val HistoryEditScreenRoute = "history_edit_screen"
        const val HistoryEditScreenRouteGraph = "${HistoryEditScreenRoute}/{${HistoryEditRouteArgument}}"
    }
}