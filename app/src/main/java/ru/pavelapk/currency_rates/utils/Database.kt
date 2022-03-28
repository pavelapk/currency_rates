package ru.pavelapk.currency_rates.utils

import android.content.Context
import androidx.room.Room
import ru.pavelapk.currency_rates.data.AppDatabase

object Database {

    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase = synchronized(this) {
        instance ?: build(context.applicationContext).also { instance = it }
    }

    private fun build(context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "app_database")
        .fallbackToDestructiveMigration()
        .build()
}