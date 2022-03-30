package ru.pavelapk.currency_rates.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllCurrencies(currencies: List<CurrencyEntity>)

    @Query("SELECT * FROM currency")
    abstract fun observeAll(): Flow<List<CurrencyEntity>>

}