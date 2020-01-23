package yoktavian.com.mvsp.base.network

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.Executor

/**
 * Created by YudaOktavian on 30-Sep-2019
 */
class ApiCustomAdapter private constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = returnType.let {
        return try {
            // get type
            val enclosingType = (it as ParameterizedType)

            // ensure enclosing type
            if (enclosingType.rawType != Packet::class.java)
                null
            else {
                val type = enclosingType.actualTypeArguments[0]
                PacketAdapter<Any>(type, retrofit.callbackExecutor())
            }
        } catch (ex: ClassCastException) {
            null
        }
    }

    companion object {
        @JvmStatic
        fun create() = ApiCustomAdapter()
    }
}

class PacketAdapter<T>(private val responseType: Type, private val callbackExecutor: Executor?) :
    CallAdapter<T, Any> {
    override fun responseType(): Type = responseType
    override fun adapt(call: Call<T>): Any = PacketRequest(call, callbackExecutor)
}

class PacketRequest<T> constructor(private val call: Call<T>, val executor: Executor?) :
    Packet<T> {

    override var errorResponse = ErrorResponse()

    override fun result(onSuccess: (T) -> Unit, onError: (ErrorResponse) -> Unit) {
        call.enqueue(
            object : retrofit2.Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    executor?.let {
                        errorResponse.error = Gson().fromJson(t.cause.toString(), Error::class.java)
                        it.execute { onError.invoke(errorResponse) }
                    }
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            executor?.let {
                                it.execute { onSuccess.invoke(body) }
                            }
                        }
                    } else {
                        executor?.let {
                            errorResponse = Gson().fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                            it.execute { onError.invoke(errorResponse) }
                        }
                    }
                }
            }
        )
    }
}