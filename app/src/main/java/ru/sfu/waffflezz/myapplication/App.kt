package ru.sfu.waffflezz.myapplication

import android.app.Application
import ru.sfu.waffflezz.myapplication.data.MyDatabase

class App : Application() {
    val database by lazy { MyDatabase.createDataBase(this) }
}