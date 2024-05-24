package ru.sfu.waffflezz.myapplication.bottomNavigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ru.sfu.waffflezz.myapplication.data.entities.CardEntity
import ru.sfu.waffflezz.myapplication.viewmodels.CartViewModel
import ru.sfu.waffflezz.myapplication.viewmodels.MapViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    cartViewModel: CartViewModel = viewModel(factory = CartViewModel.factory),
    mapViewModel: MapViewModel = viewModel(factory = MapViewModel.factory)
) {

    LaunchedEffect(key1 = true) {
        cartViewModel.insertCard(
            CardEntity(
                1,
                "Сухой корм",
                "ROYAL CANIN REGULAR",
                "https://static1.nordic.pictures/24415612-thickbox_default/royal-canin-sterilised-37-cats-dry-food-adult-poultry-400-g.jpg",
                98.23,
                12.34
            )
        )
        cartViewModel.insertCard(
            CardEntity(
                2,
                "Консервы",
                "PRO PLAN DELICATE",
                "https://питомец16.рф/image/cache/catalog/tovari/PRO-PLAN-DELICATE-zh-b-Indejka-85g-800x800.jpg",
                48.44,
                5.34
            )
        )
        cartViewModel.insertCard(
            CardEntity(
                3,
                "Наполнители",
                "PI-PI BENT",
                "https://6kcmxu3d7l.a.trbcdn.net/upload/files-new/47/aa/69/585206_1600x1600.jpg",
                121.12,
                23.34
            )
        )
        cartViewModel.insertCard(
            CardEntity(
                4,
                "Когтеточки",
                "ECOPET",
                "https://petscage.ru/wa-data/public/shop/products/11/49/4911/images/26434/26434.970.jpg",
                98.23,
                12.34
            )
        )
    }

    val navController = rememberNavController()
    Scaffold (
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.White
                ),
                title = {
                },
            )
        },
        bottomBar = {
            BottomNavigation(navController = navController) 
        }
    ) {innerPadding ->
        NavGraph(
            navHostController = navController,
            innerPadding,
            cartViewModel,
            mapViewModel
        )
    }
}