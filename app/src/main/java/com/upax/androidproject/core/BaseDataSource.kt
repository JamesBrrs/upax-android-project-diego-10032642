package com.upax.androidproject.core

import android.annotation.SuppressLint
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

abstract class BaseDataSource {

    private var headers: HashMap<String, String>? = null

    private fun getOkHttpClient(): OkHttpClient {
        return try {
            val trustAllCerts = arrayOf<TrustManager>(
                @SuppressLint("CustomX509TrustManager")
                object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?> {
                        return arrayOf()
                    }
                }
            )

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            builder.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    val request: Request = chain.request()
                    val newRequest = request.newBuilder()
                    headers.let {
                        headers?.keys?.forEach {
                            newRequest.addHeader(it, headers!![it].toString())
                        }
                        return chain.proceed(newRequest.build())
                    }
                }
            })
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            builder.callTimeout(30, TimeUnit.SECONDS)
            builder.connectTimeout(30, TimeUnit.SECONDS)
            builder.readTimeout(30, TimeUnit.SECONDS)
            builder.writeTimeout(30, TimeUnit.SECONDS)
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = call()
            val code = response.code()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.success(body, code)
                } else
                    error("menaje vacio", code)
            } else
                error("mensaje vacio", code)
        } catch (e: Exception) {
            error(e.message.toString(), 0)
        }
    }

    private fun <T> error(message: String, code: Int): Resource<T> {
        return Resource.error(message, null, code)
    }

    abstract fun getUrl(): String
    abstract fun getClazz(): Class<*>

    fun <T> getRetrofit(): T {

        val retrofit = Retrofit.Builder()
            .baseUrl(getUrl())
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(getClazz()) as T
    }
}