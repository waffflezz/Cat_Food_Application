package ru.sfu.waffflezz.myapplication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.sfu.waffflezz.myapplication.viewmodels.CartViewModel
import ru.sfu.waffflezz.myapplication.R
import ru.sfu.waffflezz.myapplication.data.entities.CardEntity
import ru.sfu.waffflezz.myapplication.data.entities.CartEntity

@Composable
fun CatFoodCard(
    cardEntity: CardEntity,
    cartViewModel: CartViewModel,
    inHistory: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(colorResource(id = R.color.white_pink))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 30.dp,
                        start = 14.dp
                        )
            ) {
                Text(
                    text = cardEntity.type
                )
                Text(
                    text = cardEntity.name,
                    fontSize = 4.em
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp)
            ) {
                if (!inHistory) {
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        onClick = {
                            cartViewModel.insertCartItem(
                                CartEntity(cardId = cardEntity.id!!, quantity = 1)
                            )
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = "",
                        )
                    }
                }

                AsyncImage(
                    modifier = Modifier
                        .height(120.dp)
                        .width(90.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(cardEntity.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Meow"
                )
            }
        }
    }
}