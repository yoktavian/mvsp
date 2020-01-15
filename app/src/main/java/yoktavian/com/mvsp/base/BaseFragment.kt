package yoktavian.com.mvsp.base

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

/**
 * Created by YudaOktavian on 03/02/2019
 */
abstract class BaseFragment<S, P> :
    Fragment(), BaseFragmentContract<S, P>, MainPresenter {

    //    private val parentJob = Job()
    private var weakReferenceFragment = WeakReference<Fragment>(null)
    // lifecycle owner should be attached fragment.
    /**
     * Still unused for now.
    private val lifecycleOwner: LifecycleOwner?
        get() = weakReferenceFragment.get()
    */

    abstract class Presenter<S, V, R>(
        val state: S,
        val view: V,
        val repository: R
    ) : CoroutineScope {

        private val parentJob = Job()
        override val coroutineContext: CoroutineContext
            get() = parentJob

        /**
         * Using this context to back to dispatcher main from dispatcher IO or Default.
         * You know that if we are in IO or Default then trying to rendering UI, it will
         * caused a crash on the app, you should back to dispatcher Main so that you
         * can update the UI.
         */
        fun UIjob(block: suspend CoroutineScope.() -> Unit): Job {
            return launch(Dispatchers.Main + parentJob, block = block)
        }

        /**
         * DFT means default. Use it for heavy computation, like parsing data or
         * something else.
         */
        fun DFTjob(block: suspend CoroutineScope.() -> Unit): Job {
            return launch(Dispatchers.Default + parentJob, block = block)
        }

        /**
         * IO job. Use it for network operation.
         */
        fun IOjob(block: suspend CoroutineScope.() -> Unit): Job {
            return launch(Dispatchers.Default + parentJob, block = block)
        }

        fun onCreate() {}
        fun onResume() {}
        fun onDestroy() {
            // keep all operation to be safe. if fragment
            // already destroyed, it will cancel all jobs.
            if (parentJob.isActive) parentJob.cancel()
        }
    }

    // region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakReferenceFragment = WeakReference(this)
        (presenter as? Presenter<*, *, *>)?.onCreate()
    }

    override fun onResume() {
        super.onResume()
        (presenter as? Presenter<*, *, *>)?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        (presenter as? Presenter<*, *, *>)?.onDestroy()
    }

    /**
     * It's safe closure lambda function. When screen
     * requires activity/context, closure lambda will make sure
     * you still have the context. if activity destroyed
     * it will safe, because the code inside closure lambda
     * not excecuted.
     */
    fun activity(act: (Activity) -> Unit) {
        if (isFragmentAlive()) activity?.let { act(it) }
    }

    /**
     * It's safe closure lambda function to get fragment. When screen
     * requires fragment/view, closure lambda will make sure
     * you still have the view. if view destroyed
     * it will safe, because the code inside closure lambda
     * not excecuted.
     */
    fun fragment(fragment: (Fragment) -> Unit) {
        if (isFragmentAlive()) fragment(this)
    }

    private fun isFragmentAlive() =
        weakReferenceFragment.get() != null &&
                isAdded &&
                !isRemoving &&
                !isDetached
    // endregion

    fun showToast(message: String) {
        activity {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
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

interface BaseFragmentContract<S, P> {
    val state: S
    val presenter: P
}