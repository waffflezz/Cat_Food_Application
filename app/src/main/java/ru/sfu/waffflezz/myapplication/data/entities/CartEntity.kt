package ru.sfu.waffflezz.myapplication.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartEntity (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val cardId: Int, // ID продукта из таблицы CardEntity
    val quantity: Int
)