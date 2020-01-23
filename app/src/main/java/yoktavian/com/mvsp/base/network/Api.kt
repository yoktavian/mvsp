package yoktavian.com.mvsp.base.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import yoktavian.com.mvsp.BuildConfig
import java.util.concurrent.TimeUnit

/**
 * Created by YudaOktavian on 01-Oct-2019
 */
class Api {
    companion object {
        private const val BASE_URL = "https://api.sejastip.id/"
        private const val AUTH_HEADER = "Authorization"
        private const val USER_AGENT = "User-Agent"
        private const val CUSTOM_TIMEOUT = 1L

        fun retrofit(): Retrofit {
            val okHttpClient = OkHttpClient.Builder()
            // stetho interceptor only added when build config is debug mode.
            /**
             * If you need network interceptor, you can enable this code.
             * Don't forget to add stetho to gradle dependencies.
            if (BuildConfig.DEBUG) {
                okHttpClient.addNetworkInterceptor(StethoInterceptor())
            }
            */
            val userToken = "Token {get token from local}"
            okHttpClient
                .connectTimeout(CUSTOM_TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(CUSTOM_TIMEOUT, TimeUnit.MINUTES)
                .addInterceptor { chain ->
                val originalChain = chain.request()
                val requestBuilder = originalChain.newBuilder()
                    .header(AUTH_HEADER, userToken)
                    .header(USER_AGENT, getUserAgent())
                // return updated chain.
                chain.proceed(requestBuilder.build())
            }

            return Retrofit.Builder()
                .baseUrl( BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(ApiCustomAdapter.create())
                .build()
        }

        private fun getUserAgent(): String {
            return "your user-agent ${BuildConfig.VERSION_CODE}"
        }

        inline fun <reified T> service(): T = retrofit().create(T::class.java)
    }
}