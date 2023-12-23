package com.touhidapps.apicallwithcoroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resumeWithException

object MyApiClient {

    fun callMyApi(apiPath: String, reqMethod: String, success: (String) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            val res = apiRequest(apiPath, reqMethod)
            println(res)
            success(res)

        }

    }

    private suspend fun apiRequest(apiPath: String, reqMethod: String): String {

        return suspendCancellableCoroutine { continuation ->
            try {

                val url = URL(apiPath)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = reqMethod
                val readerStream = InputStreamReader(connection.inputStream) as Reader?
                readerStream?.let {

                    val reader = BufferedReader(it)
                    val myApiData = reader.readText()
                    reader.close()

                    if (continuation.isActive) {
                        continuation.resume(myApiData) {
                            it.printStackTrace()
                        }
                    }

                }

            } catch (e: Exception) {

                e.printStackTrace()
                if (continuation.isActive) {
                    continuation.resumeWithException(e)
                }

            }
        }


    }


}