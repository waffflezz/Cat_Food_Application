package ru.sfu.waffflezz.myapplication.bottomNavigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ru.sfu.waffflezz.myapplication.viewmodels.CartViewModel
import ru.sfu.waffflezz.myapplication.R
import ru.sfu.waffflezz.myapplication.components.CartCard
import ru.sfu.waffflezz.myapplication.components.CatFoodCard
import ru.sfu.waffflezz.myapplication.components.HistoryCard
import ru.sfu.waffflezz.myapplication.viewmodels.MapViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    cartViewModel.changeTitleInfo("Главная", R.drawable.home)

    val allCards by cartViewModel.allCards.collectAsState()
    val catFact by cartViewModel.catFact.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .background(Color.White),
            text = catFact,
            fontSize = 4.em,
            color = colorResource(id = R.color.bold_pink)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.pink))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp, start = 15.dp, end = 15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(allCards) { card ->
                    CatFoodCard(card, cartViewModel)
                }
            }
        }
    }
}

@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    cartViewModel.changeTitleInfo("Корзина", R.drawable.shopping_cart)

    val allCarts by cartViewModel.allCartItems.collectAsState()

    val totalPrice by cartViewModel.totalPrice.collectAsState()
    val totalPackagePrice by cartViewModel.totalPackagePrice.collectAsState()
    val totalPriceWithPackage by cartViewModel.totalPriceWithPackage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp),
            text = "Ваш заказ:",
            color = colorResource(id = R.color.bold_pink)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.7f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(allCarts) { cartItem ->
                CartCard(cartEntity = cartItem, cartViewModel = cartViewModel)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp),
                color = Color.Black
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Промежуточный итог", color = colorResource(id = R.color.bold_pink))
                Text(text = String.format("%.2f", totalPrice), color = colorResource(id = R.color.bold_pink))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Доставка", color = colorResource(id = R.color.bold_pink))
                Text(text = "0", color = colorResource(id = R.color.bold_pink))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Упаковка", color = colorResource(id = R.color.bold_pink))
                Text(text = String.format("%.2f", totalPackagePrice), color = colorResource(id = R.color.bold_pink))
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp),
                color = Color.Black
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Итого",
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.bold_pink)
                )
                Text(
                    text = String.format("%.2f", totalPriceWithPackage),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.bold_pink)
                )
            }

            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(16.dp),
                onClick = { navController.navigate(Routes.ConfirmTheOrderRoute) }
            ) {
                Text(text = "Оформить заказ")
            }
        }
    }
}

@Composable
fun MapScreen(
    navController: NavController,
    mapViewModel: MapViewModel,
    cartViewModel: CartViewModel
) {
    cartViewModel.changeTitleInfo("Мы на карте", R.drawable.map)

    val markers by mapViewModel.markers.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(56.0153, 92.8932), 12f)
    }

    GoogleMap(
        cameraPositionState = cameraPositionState
    ) {
        markers.forEach { place ->
            val position = LatLng(place.lat, place.lng)
            Marker(
                state = MarkerState(position = position),
                title = "Кошкин Дом"
            )
        }
    }
}

@Composable
fun HistoryScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    cartViewModel.changeTitleInfo("История", R.drawable.history)

    val allOrders by cartViewModel.allOrders.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 15.dp, end = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(allOrders) { orderItem ->
                HistoryCard(
                    orderEntity = orderItem,
                    navController
                )
            }
        }
    }
}

@Composable
fun ConfirmTheOrderScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    cartViewModel.changeTitleInfo("Корзина", R.drawable.shopping_cart)

    val phone = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val comment = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        val rowsModifier = Modifier
            .fillMaxWidth()
        Column(
            modifier = Modifier
        ) {
            Row(
                modifier = rowsModifier,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Телефон:", color = colorResource(id = R.color.bold_pink))
                TextField(value = phone.value, onValueChange = {newText -> phone.value = newText})
            }
            Row(
                modifier = rowsModifier,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Имя:", color = colorResource(id = R.color.bold_pink))
                TextField(value = name.value, onValueChange = {newText -> name.value = newText})
            }
            Row(
                modifier = rowsModifier,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Комментарий:", color = colorResource(id = R.color.bold_pink))
                TextField(
                    value = comment.value,
                    onValueChange = {newText -> comment.value = newText},
                    minLines = 3
                )
            }
        }

        Button(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                cartViewModel.placeOrder(phone.value, name.value, comment.value)
                cartViewModel.clearCart()
                navController.navigateUp()
            }
        ) {
            Text(text = "Подтвердить заказ")
        }
    }
}

@Composable
fun HistoryEditScreen(
    orderId: Int,
    cartViewModel: CartViewModel,
    navController: NavController
) {
    cartViewModel.changeTitleInfo("Заказ $orderId", R.drawable.history)

    val orderEntity by cartViewModel.orderEntity.collectAsState()
    val orderCardsEntities by cartViewModel.orderCardsEntities.collectAsState()

    val comment = remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        cartViewModel.fetchOrderById(orderId)
    }

    LaunchedEffect(key1 = orderEntity) {
        if (orderEntity != null) {
            comment.value = orderEntity!!.comment
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        orderEntity?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Комментарий:", color = colorResource(id = R.color.bold_pink))
                TextField(value = comment.value, onValueChange = {newText -> comment.value = newText})
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .padding(top = 24.dp, start = 15.dp, end = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(orderCardsEntities) {
                CatFoodCard(cardEntity = it, cartViewModel = cartViewModel, true)
            }
        }

        Button(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                cartViewModel.updateCommentOrder(orderId, comment.value)
                navController.navigateUp()
            }
        ) {
            Text(text = "Изменить комментарий")
        }
    }
}