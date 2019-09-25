package yoktavian.com.mvsp.data.source

import yoktavian.com.mvsp.data.User
import yoktavian.com.mvsp.data.Wallet

/**
 * Created by YudaOktavian on 03/02/2019
 */
class UserRepository {
    fun getUserData(result: (User) -> Unit) {
        result(
            User(name = "Tayo", address = "Jakarta", phone = 111)
        )
    }

    fun getBalance(result: (Wallet) -> Unit) {
        result(Wallet(balance = 1000))
    }
}