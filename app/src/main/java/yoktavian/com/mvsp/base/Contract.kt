package yoktavian.com.mvsp.base

/**
 * Created by YudaOktavian on 03/02/2019
 */
interface Contract {
    interface View {
        fun renderLoading()
        fun renderNetworkError()
    }
}