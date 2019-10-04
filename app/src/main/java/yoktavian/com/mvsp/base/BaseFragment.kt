package yoktavian.com.mvsp.base

import android.app.Activity
import android.support.v4.app.Fragment
import android.widget.Toast
import kotlinx.coroutines.*

/**
 * Created by YudaOktavian on 03/02/2019
 */
abstract class BaseFragment<S, P> : Fragment(), BaseFragmentContract<S, P>, MainPresenter {

    private val parentJob = Job()

    open class Presenter <S, V, R> (val state: S, val view: V, val repository: R) {
        /**
         * Using this context to back to dispatcher main from dispatcher IO or Default.
         * You know that if we are in IO or Default then trying to rendering UI, it will
         * caused a crash on the app, you should back to dispatcher Main so that you
         * can update the UI.
         */
        fun UIjob(block: suspend CoroutineScope.() -> Unit): Job {
            return GlobalScope.launch(Dispatchers.Main, block = block)
        }
    }

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
     * use it for IO needs like read write database, fetch data
     * from API etc.
     */
    fun IOjob(block: suspend CoroutineScope.() -> Unit): Job {
        return GlobalScope.launch(Dispatchers.IO + parentJob, block = block)
    }

    /**
     * DFT means default. Use it for heavy computation, like parsing data or
     * something else.
     */
    fun DFTjob(block: suspend CoroutineScope.() -> Unit): Job {
        return GlobalScope.launch(Dispatchers.Default + parentJob, block = block)
    }

    // region handling lifecycle
    override fun onDestroy() {
        if (parentJob.isActive) parentJob.cancel()
        super.onDestroy()
    }

    /**
     * It's safe closure lambda function to get fragment. When screen
     * requires fragment/view, closure lambda will make sure
     * you still have the view. if view destroyed
     * it will safe, because the code inside closure lambda
     * not excecuted.
     */
    fun fragment(fragment: (Fragment) -> Unit) {
        if (isAdded) {
            fragment(this)
        }
    }

    fun showToast(message: String) {
        activity {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }
    // endregion

    /**
     * As default screen needs two type of rendering,
     * loading and error network. Just ignore when
     * the screen doesn't support this.
     */
    override fun renderLoading() {}
    override fun renderNetworkError() {}
}

interface BaseFragmentContract<S, P> {
    val state: S
    val presenter: P
}