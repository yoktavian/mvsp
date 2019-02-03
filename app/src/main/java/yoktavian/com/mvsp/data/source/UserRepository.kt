package yoktavian.com.mvsp.data.source

import kotlinx.coroutines.delay
import yoktavian.com.mvsp.data.User

/**
 * Created by YudaOktavian on 03/02/2019
 */
class UserRepository {
    suspend fun getUserData(result: (User) -> Unit) {
        // just imagine this is a loading time
        // when you call API till you get the
        // response in 5 second.
        delay(5000L)

        result(
            User(name = "Tayo", address = "Jakarta", phone = 111)
        )
    }
}