package ru.sfu.waffflezz.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.sfu.waffflezz.myapplication.App
import ru.sfu.waffflezz.myapplication.data.MyDatabase
import ru.sfu.waffflezz.myapplication.data.Repository
import ru.sfu.waffflezz.myapplication.data.api.RetrofitInstance
import ru.sfu.waffflezz.myapplication.data.entities.CardEntity
import ru.sfu.waffflezz.myapplication.data.entities.CartEntity
import ru.sfu.waffflezz.myapplication.data.entities.OrderEntity

class CartViewModel(database: MyDatabase) : ViewModel() {
    private val repository: Repository

    private val _catFact = MutableStateFlow("")
    val catFact: StateFlow<String> = _catFact.asStateFlow()

    private val _allCards = MutableStateFlow<List<CardEntity>>(emptyList())
    val allCards: StateFlow<List<CardEntity>> = _allCards.asStateFlow()

    private val _allCartItems = MutableStateFlow<List<CartEntity>>(emptyList())
    val allCartItems: StateFlow<List<CartEntity>> = _allCartItems.asStateFlow()

    private val _allOrders = MutableStateFlow<List<OrderEntity>>(emptyList())
    val allOrders: StateFlow<List<OrderEntity>> = _allOrders.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    private val _totalPackagePrice = MutableStateFlow(0.0)
    val totalPackagePrice: StateFlow<Double> = _totalPackagePrice.asStateFlow()

    private val _totalPriceWithPackage = MutableStateFlow(0.0)
    val totalPriceWithPackage: StateFlow<Double> = _totalPriceWithPackage.asStateFlow()

    private val _orderEntity = MutableStateFlow<OrderEntity?>(null)
    val orderEntity: StateFlow<OrderEntity?> = _orderEntity

    private val _orderCardsEntities = MutableStateFlow<List<CardEntity>>(emptyList())
    val orderCardsEntities: StateFlow<List<CardEntity>> = _orderCardsEntities

    init {
        val cardDao = database.cardDao
        val cartDao = database.cartDao
        val orderDao = database.orderDao
        repository = Repository(cardDao, cartDao, orderDao)

        viewModelScope.launch {
            repository.allCards.collect { cards ->
                _allCards.value = cards
                recalculateCartValues()
            }
        }

        viewModelScope.launch {
            repository.allCartItems.collect { cartItems ->
                _allCartItems.value = cartItems
                recalculateCartValues()
            }
        }

        viewModelScope.launch {
            repository.allOrders.collect { orders ->
                _allOrders.value = orders
            }
        }

        viewModelScope.launch {
            val response = RetrofitInstance.api.getFact()
            _catFact.value = response.fact
        }
    }

    fun insertCard(card: CardEntity) = viewModelScope.launch {
        repository.insertCard(card)
    }

    fun updateCard(card: CardEntity) = viewModelScope.launch {
        repository.updateCard(card)
    }

    fun deleteCard(card: CardEntity) = viewModelScope.launch {
        repository.deleteCard(card)
    }

    fun getCardById(cardId: Int): StateFlow<CardEntity?> = flow {
        repository.getCardById(cardId).collect {
            emit(it)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun insertCartItem(cartItem: CartEntity) = viewModelScope.launch {
        repository.insertCartItem(cartItem)
    }

    fun deleteCartItem(cartItemId: Int) = viewModelScope.launch {
        repository.deleteCartItem(cartItemId)
    }

    fun clearCart() = viewModelScope.launch {
        repository.clearCart()
    }

    fun updateCartItemQuantity(cartItemId: Int, newQuantity: Int) = viewModelScope.launch {
        repository.updateCartItemQuantity(cartItemId, newQuantity)
    }

    fun updateQuantity(cartEntity: CartEntity) {
        viewModelScope.launch {
            repository.updateQuantity(cartEntity)
        }
    }

    fun getQuantity(cartId: Int): StateFlow<Int?> = flow {
        repository.getQuantity(cartId).collect {
            emit(it)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun placeOrder(phone: String, name: String, comment: String) = viewModelScope.launch {
        val cartItems = _allCartItems.value
        repository.placeOrder(phone, name, comment, cartItems)
    }

    fun updateCommentOrder(orderId: Int, comment: String) {
        viewModelScope.launch {
            repository.updateCommentOrder(orderId, comment)
        }
    }

    val allCartCards: StateFlow<List<CardEntity>> = repository.getAllCartCards()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getCardByCartId(cartId: Int): StateFlow<CardEntity?> = flow {
        repository.getCardByCartId(cartId).collect {
            emit(it)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun getOrderById(orderId: Int): LiveData<OrderEntity> {
        return repository.getOrderById(orderId).asLiveData()
    }

    fun fetchOrderById(orderId: Int) {
        viewModelScope.launch {
            repository.getOrderById(orderId).collect { order ->
                _orderEntity.value = order
                fetchOrderCards(order)
            }
        }
    }

    private fun fetchOrderCards(orderEntity: OrderEntity) {
        viewModelScope.launch {
            repository.getCardsByOrderId(orderEntity.id!!).collect { cards ->
                _orderCardsEntities.value = cards
            }
        }
    }

    private fun recalculateCartValues() {
        calculateTotalPrice()
        calculateTotalPackagePrice()
        calculateTotalPriceWithPackage()
    }

    private fun calculateTotalPrice() {
        viewModelScope.launch {
            val cardMap = _allCards.value.associateBy { it.id }
            val total = _allCartItems.value.sumOf { cartItem ->
                val card = cardMap[cartItem.cardId]
                (card?.price ?: 0.0) * cartItem.quantity
            }
            _totalPrice.value = total
        }
    }

    // Method to calculate total package price of items in the cart
    private fun calculateTotalPackagePrice() {
        viewModelScope.launch {
            val cardMap = _allCards.value.associateBy { it.id }
            val totalPackagePrice = _allCartItems.value.sumOf { cartItem ->
                val card = cardMap[cartItem.cardId]
                card?.packagePrice?.times(cartItem.quantity) ?: 0.0
            }
            _totalPackagePrice.value = totalPackagePrice
        }
    }

    // Method to calculate total price with package of items in the cart
    private fun calculateTotalPriceWithPackage() {
        viewModelScope.launch {
            _totalPriceWithPackage.value = _totalPrice.value + _totalPackagePrice.value
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return CartViewModel(database) as T
            }
        }
    }
}