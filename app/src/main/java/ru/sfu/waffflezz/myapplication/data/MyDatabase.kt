package ru.sfu.waffflezz.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.sfu.waffflezz.myapplication.data.dao.CardDao
import ru.sfu.waffflezz.myapplication.data.dao.CartDao
import ru.sfu.waffflezz.myapplication.data.dao.OrderDao
import ru.sfu.waffflezz.myapplication.data.entities.CardEntity
import ru.sfu.waffflezz.myapplication.data.entities.CartEntity
import ru.sfu.waffflezz.myapplication.data.entities.OrderEntity
import ru.sfu.waffflezz.myapplication.data.entities.OrderItemEntity

@Database(
    entities = [
        CardEntity::class, CartEntity::class, OrderEntity::class, OrderItemEntity::class
    ],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract val cardDao: CardDao
    abstract val cartDao: CartDao
    abstract val orderDao: OrderDao

    companion object {
        fun createDataBase(context: Context): MyDatabase {
            return Room.databaseBuilder(
                context,
                MyDatabase::class.java,
                "main.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}