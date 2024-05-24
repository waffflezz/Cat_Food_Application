package ru.sfu.waffflezz.myapplication.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.flow.collect
import ru.sfu.waffflezz.myapplication.viewmodels.CartViewModel
import ru.sfu.waffflezz.myapplication.R
import ru.sfu.waffflezz.myapplication.data.entities.CardEntity
import ru.sfu.waffflezz.myapplication.data.entities.CartEntity

@Composable
fun CartCard(
    cartEntity: CartEntity,
    cartViewModel: CartViewModel
) {
    val quantity = remember { mutableIntStateOf(1) }
    var cardEntity by remember { mutableStateOf<CardEntity?>(null) }

    val quantityFlow = cartViewModel.getQuantity(cartEntity.id!!)
    LaunchedEffect(quantityFlow) {
        quantityFlow.collect {
            Log.d("DEBUGGG", it.toString())
            quantity.intValue = it!!
        }
    }

    LaunchedEffect(key1 = quantity.intValue) {
        if (quantity.intValue != 0) {
            cartViewModel.updateCartItemQuantity(cartEntity.id!!, quantity.intValue)
        }
    }

    LaunchedEffect(key1 = true) {
        cartViewModel.getCardById(cartEntity.cardId).collect {
            cardEntity = it
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        cardEntity?.let {
            AsyncImage(
                modifier = Modifier
                    .height(110.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(end = 8.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(it.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Meow"
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = it.name,
                    fontSize = 3.em
                )
                Text(text = "${it.price} ₽")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(onClick = {
                        cartViewModel.updateQuantity(
                            CartEntity(cartEntity.id, cartEntity.cardId, ++quantity.intValue)
                        )
                    }) {
                        Icon(painter = painterResource(id = R.drawable.plus), contentDescription = "plus")
                    }
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        text = quantity.intValue.toString()
                    )
                    IconButton(onClick = {
                        if (--quantity.intValue == 0) {
                            cartViewModel.deleteCartItem(cartEntity.id!!)
                            cartViewModel.updateQuantity(
                                CartEntity(cartEntity.id, cartEntity.cardId, quantity.intValue)
                            )
                        }
                    }) {
                        Icon(painter = painterResource(id = R.drawable.minus), contentDescription = "minus")
                    }
                }
                IconButton(
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        cartViewModel.deleteCartItem(cartEntity.id!!)
                    }) {
                    Icon(painter = painterResource(id = R.drawable.x_symbol), contentDescription = "x_symbol")
                }
            }
        }
    }
}