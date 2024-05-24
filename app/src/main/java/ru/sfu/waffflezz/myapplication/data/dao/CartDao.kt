package ru.sfu.waffflezz.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.sfu.waffflezz.myapplication.data.entities.CardEntity
import ru.sfu.waffflezz.myapplication.data.entities.CartEntity

@Dao
interface CartDao {
    @Update
    suspend fun updateQuantity(cartEntity: CartEntity)

    @Query("SELECT quantity FROM cart_table WHERE id = :id")
    fun getQuantity(id: Int): Flow<Int>

    @Query("UPDATE cart_table SET quantity = :newQuantity WHERE id = :cartItemId")
    suspend fun updateCartItemQuantity(cartItemId: Int, newQuantity: Int)

    @Query("SELECT card_table.* FROM cart_table INNER JOIN card_table ON cart_table.cardId = card_table.id WHERE cart_table.id = :cartId")
    fun getCardByCartId(cartId: Int): Flow<CardEntity?>

    @Query("SELECT card_table.* FROM cart_table INNER JOIN card_table ON cart_table.cardId = card_table.id")
    fun getAllCartCards(): Flow<List<CardEntity>>

    @Query("SELECT * FROM cart_table")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartEntity)

    @Query("DELETE FROM cart_table WHERE id = :cartItemId")
    suspend fun deleteCartItem(cartItemId: Int)

    @Query("DELETE FROM cart_table")
    suspend fun clearCart()
}