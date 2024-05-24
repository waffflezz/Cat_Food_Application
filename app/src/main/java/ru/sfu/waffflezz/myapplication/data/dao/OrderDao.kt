package ru.sfu.waffflezz.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.sfu.waffflezz.myapplication.data.entities.OrderEntity
import ru.sfu.waffflezz.myapplication.data.entities.OrderItemEntity

@Dao
interface OrderDao {
    @Query("UPDATE orders_table SET comment = :comment WHERE id = :orderId")
    suspend fun updateCommentOrder(orderId: Int, comment: String)

    @Query("SELECT * FROM orders_table WHERE id = :orderId")
    fun getOrderById(orderId: Int): Flow<OrderEntity>

    @Query("SELECT * FROM orders_table")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(orderItems: List<OrderItemEntity>)

    @Query("SELECT * FROM order_items_table WHERE orderId = :orderId")
    fun getOrderItems(orderId: Int): Flow<List<OrderItemEntity>>

    @Update
    suspend fun updateOrder(order: OrderEntity)
}