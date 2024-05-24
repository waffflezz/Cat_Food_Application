package ru.sfu.waffflezz.myapplication.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_table")
data class CardEntity (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val type: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val packagePrice: Double
)