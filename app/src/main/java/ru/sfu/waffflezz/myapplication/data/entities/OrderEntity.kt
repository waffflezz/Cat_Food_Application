package ru.sfu.waffflezz.myapplication.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "orders_table")
data class OrderEntity (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val phone: String,
    val name: String,
    val comment: String
)

@Entity(
    tableName = "order_items_table",
    foreignKeys = [
        ForeignKey(entity = OrderEntity::class, parentColumns = ["id"], childColumns = ["orderId"]),
        ForeignKey(entity = CardEntity::class, parentColumns = ["id"], childColumns = ["cardId"])
    ],
    indices = [Index("orderId"), Index("cardId")]
)
data class OrderItemEntity (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val orderId: Int, // ID заказа из таблицы OrderEntity
    val cardId: Int, // ID продукта из таблицы CardEntity
    val quantity: Int
)