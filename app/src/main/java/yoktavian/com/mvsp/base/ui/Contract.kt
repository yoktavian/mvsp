package yoktavian.com.mvsp.base.ui

/**
 * Created by YudaOktavian on 03/02/2019
 */
interface Contract {
    interface View<S> {
        fun renderAll(state: S)
        fun renderLoading(state: S)
        fun renderNetworkError(state: S)
    }
}