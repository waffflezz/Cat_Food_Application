package ru.sfu.waffflezz.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.sfu.waffflezz.myapplication.data.entities.CardEntity

@Dao
interface CardDao {
    @Query("SELECT * FROM card_table WHERE id IN (SELECT cardId FROM order_items_table WHERE orderId = :orderId)")
    fun getCardsByOrderId(orderId: Int): Flow<List<CardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(cardEntity: CardEntity)

    @Delete
    suspend fun deleteCard(cardEntity: CardEntity)

    @Update
    suspend fun updateCard(cardEntity: CardEntity)

    @Query("SELECT * FROM card_table")
    fun getAllCards(): Flow<List<CardEntity>>

    @Query("SELECT * FROM card_table WHERE id = :cardId")
    fun getCardById(cardId: Int): Flow<CardEntity?>
}