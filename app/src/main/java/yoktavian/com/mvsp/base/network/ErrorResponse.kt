package yoktavian.com.mvsp.base.network

/**
 * Created by YudaOktavian on 02-Oct-2019
 */
class ErrorResponse(var error: Error? = null)

class Error(
    val message: String,
    val code: Int
) {
    class LoginConstants {
        companion object {
            const val INVALID_INPUT = 401
            const val USER_NOT_FOUND = 404
        }
    }

    class ProductConstants {
        companion object {
            const val IMAGE_NULL = 400
        }
    }
}