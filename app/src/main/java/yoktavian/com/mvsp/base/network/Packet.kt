package yoktavian.com.mvsp.base.network

/**
 * Created by YudaOktavian on 30-Sep-2019
 */
interface Packet<T> {
    var errorResponse: ErrorResponse

    fun result(
        onSuccess: (T) -> Unit,
        onError: (ErrorResponse) -> Unit
    )
}