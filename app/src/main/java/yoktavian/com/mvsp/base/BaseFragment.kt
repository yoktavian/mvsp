package yoktavian.com.mvsp.base

import android.app.Activity
import android.support.v4.app.Fragment
import kotlin.reflect.KFunction0

/**
 * Created by YudaOktavian on 03/02/2019
 */
abstract class BaseFragment<T, A, S: Fragment> (private val fragment: KFunction0<S>):
    Fragment(), BaseFragmentContract<T, A>, MainPresenter {

    open class State
    open class Presenter <S, F, T> (val state: S, val view: F, val repository: T)

    /**
     * It's safe closure lambda function. When screen
     * requires activity/context, closure lambda will make sure
     * you still have the context. if activity destroyed
     * it will safe, because the code inside closure lambda
     * not excecuted.
     */
    fun activity(act: (Activity) -> Unit) {
        activity?.let {
            act(it)
        }
    }
    /**
     * It's safe closure lambda function to get fragment. When screen
     * requires fragment/view, closure lambda will make sure
     * you still have the view. if view destroyed
     * it will safe, because the code inside closure lambda
     * not excecuted.
     */
    fun view(vw: (view: Fragment) -> Unit) {
        fragmentManager?.fragments?.let {
            if (it.contains(fragment.invoke())) {
                view(vw)
            }
        }
    }

    /**
     * As default screen needs two type of rendering,
     * loading and error network. Just ignore when
     * the screen doesn't support this.
     */
    override fun renderLoading() {}
    override fun renderNetworkError() {}
}

interface BaseFragmentContract<T, A> {
    val state: T
    val presenter: A
}