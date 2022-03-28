package ru.pavelapk.currency_rates.api

import retrofit2.HttpException
import java.io.IOException

object Utils {

    suspend fun <T> responseHandler(call: suspend () -> T): Result<T> = try {
        Result.success(call.invoke())
    } catch (e: HttpException) {
        Result.failure(e)
    } catch (e: IOException) {
        Result.failure(e)
    }

}