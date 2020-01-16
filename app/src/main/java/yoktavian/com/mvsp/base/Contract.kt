package yoktavian.com.mvsp.base

/**
 * Created by YudaOktavian on 03/02/2019
 */
interface Contract<S> {
    interface View<S> {
        fun renderAll(state: S)
        fun renderLoading(state: S)
        fun renderNetworkError(state: S)
    }
}