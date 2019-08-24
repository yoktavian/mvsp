package yoktavian.com.mvsp.data.source

import yoktavian.com.mvsp.data.User

/**
 * Created by YudaOktavian on 03/02/2019
 */
class UserRepository {
    fun getUserData(result: (User) -> Unit) {
        result(
            User(name = "Tayo", address = "Jakarta", phone = 111)
        )
    }
}