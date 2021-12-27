package com.example.androidstudyjam.network

import com.example.androidstudyjam.BuildConfig
import com.example.androidstudyjam.RecipeApp
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object RetrofitFactory {

    private const val baseUrl = BuildConfig.BASE_URL

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(internetConnectionInterceptor)
                    .addInterceptor(queryInterceptor).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val queryInterceptor: Interceptor
        get() =
            Interceptor { chain ->

                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.API_KEY)
                    .build()
                val requestBuilder = original.newBuilder()
                    .url(url)
                val request = requestBuilder.build()
                chain.proceed(request)

            }

    /**
     * [internetConnectionInterceptor] check availability  of internet
     * if not then show custom error defined below
     * */
    private val internetConnectionInterceptor: Interceptor
        get() = Interceptor { chain ->

            if (!RecipeApp.getNetworkStatus()) {
                throw NoConnectivityException()
            } else if (!isInternetAvailable()) {
                throw NoInternetException()
            } else {
                chain.proceed(chain.request())
            }

        }

    /**
     * check speed of internet is very slow and return below [CustomExceptionClass]
     * [NoConnectivityException], [NoInternetException]
     * */
    private fun isInternetAvailable(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr = InetSocketAddress("8.8.8.8", 53)
            sock.connect(sockaddr, timeoutMs)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }

    }

    /**
     * [CustomExceptionClass] throws when internet connectivity is very slow
     * */
    private class NoConnectivityException : IOException() {
        override val message: String
            get() = "No internet available, please check your WiFi or Data connection"
    }

    /**
     * [CustomExceptionClass] throws when internet is not available
     * */
    private class NoInternetException() : IOException() {
        override val message: String
            get() = "No internet available, please check your WIFi or Data connection"
    }
}