package ru.sfu.waffflezz.myapplication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import ru.sfu.waffflezz.myapplication.bottomNavigation.Routes
import ru.sfu.waffflezz.myapplication.data.entities.OrderEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryCard(
    orderEntity: OrderEntity,
    navController: NavController
) {
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    val currentDate = sdf.format(Date())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = currentDate)
                Text(text = "Заказ №${orderEntity.id}", fontSize = 5.em)
            }
            Button(
                modifier = Modifier
                    .width(180.dp)
                    .align(Alignment.CenterVertically),
                onClick = {
                    navController.navigate("${Routes.HistoryEditScreenRoute}/${orderEntity.id}")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    color = Color.Black,
                    text = "Редактировать комментарий",
                    fontSize = 4.em
                )
            }
        }
    }
}