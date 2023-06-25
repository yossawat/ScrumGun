package per.scrumgun.test.utils

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

fun <T : Any?> successCall(data: T?): Response<T> = Response.success(data)

fun <T : Any?> failCall(): Response<T> = Response.error(500, "mock".toResponseBody(null))
