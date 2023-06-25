package per.scrumgun.test.utils

import per.scrumgun.core.model.Result

fun <T : Any?> successResult(data: T): Result<T> = Result.Success(data)

fun <T : Any?> failResult(): Result<T> = Result.Failure(RuntimeException("Error"))
fun <T : Any?> failResult(exception: Exception): Result<T> = Result.Failure(exception)
