package ru.sfu.waffflezz.myapplication.data

import kotlinx.coroutines.flow.Flow
import ru.sfu.waffflezz.myapplication.data.dao.CardDao
import ru.sfu.waffflezz.myapplication.data.dao.CartDao
import ru.sfu.waffflezz.myapplication.data.dao.OrderDao
import ru.sfu.waffflezz.myapplication.data.entities.CardEntity
import ru.sfu.waffflezz.myapplication.data.entities.CartEntity
import ru.sfu.waffflezz.myapplication.data.entities.OrderEntity
import ru.sfu.waffflezz.myapplication.data.entities.OrderItemEntity

class Repository(
    private val cardDao: CardDao,
    private val cartDao: CartDao,
    private val orderDao: OrderDao
) {
    val allCards: Flow<List<CardEntity>> = cardDao.getAllCards()

    fun getCardById(cardId: Int): Flow<CardEntity?> {
        return cardDao.getCardById(cardId)
    }

    suspend fun insertCard(card: CardEntity) {
        cardDao.insertItem(card)
    }

    suspend fun updateCard(card: CardEntity) {
        cardDao.updateCard(card)
    }

    suspend fun deleteCard(card: CardEntity) {
        cardDao.deleteCard(card)
    }

    val allCartItems: Flow<List<CartEntity>> = cartDao.getAllCartItems()
    val allOrders: Flow<List<OrderEntity>> = orderDao.getAllOrders()

    fun getAllCartCards(): Flow<List<CardEntity>> {
        return cartDao.getAllCartCards()
    }

    fun getCardByCartId(cartId: Int): Flow<CardEntity?> {
        return cartDao.getCardByCartId(cartId)
    }

    suspend fun insertCartItem(cartItem: CartEntity) {
        cartDao.insertCartItem(cartItem)
    }

    suspend fun deleteCartItem(cartItemId: Int) {
        cartDao.deleteCartItem(cartItemId)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun updateCartItemQuantity(cartItemId: Int, newQuantity: Int) {
        cartDao.updateCartItemQuantity(cartItemId, newQuantity)
    }

    suspend fun placeOrder(phone: String, name: String, comment: String, cartItems: List<CartEntity>) {
        val orderId = orderDao.insertOrder(OrderEntity(phone = phone, name = name, comment = comment))
        val orderItems = cartItems.map { cartItem ->
            OrderItemEntity(orderId = orderId.toInt(), cardId = cartItem.cardId, quantity = cartItem.quantity)
        }
        orderDao.insertOrderItems(orderItems)
        cartDao.clearCart()
    }

    fun getOrderById(orderId: Int): Flow<OrderEntity> {
        return orderDao.getOrderById(orderId)
    }

    fun getCardsByOrderId(orderId: Int): Flow<List<CardEntity>> {
        return cardDao.getCardsByOrderId(orderId)
    }

    suspend fun updateCommentOrder(orderId: Int, comment: String) {
        orderDao.updateCommentOrder(orderId, comment)
    }

    suspend fun updateQuantity(cartEntity: CartEntity) {
        cartDao.updateQuantity(cartEntity)
    }

    fun getQuantity(id: Int): Flow<Int> {
        return cartDao.getQuantity(id)
    }
}