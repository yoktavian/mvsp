package yoktavian.com.mvsp.base.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import yoktavian.com.mvsp.R
import yoktavian.com.mvsp.util.getString
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<S: Any, P: Any> :
    Fragment(), BaseFragmentContract<S, P> {

    // set screen title inside your fragment on init. it's will be
    // default name of your screen, get from app name.
    var screenTitle = R.string.app_name.getString()
    // private val parentJob = Job()
    private var weakReferenceFragment = WeakReference<Fragment>(null)
    // lifecycle owner should be attached fragment.
    private var baseHolder: BaseDataHolder<S, P>? = null
    val presenter: P get() {
        return if (baseHolder == null) {
            val state = initState()
            val presenter = initPresenter(state)
            baseHolder =
                BaseDataHolder(state, presenter)
            presenter
        } else baseHolder!!.presenter!! // should be safe operation because inside of if code block
        // already init the state and presenter.
    }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakReferenceFragment = WeakReference(this)
        (presenter as? Presenter<*, *, *>)?.onCreate()
        // set title text.
        activity {
            if (it is BasicActivity) {
                it.setTitle(screenTitle)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (presenter as? Presenter<*, *, *>)?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        (presenter as? Presenter<*, *, *>)?.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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
    fun requireFragment(fragment: (Fragment) -> Unit) {
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
}

interface BaseFragmentContract<S, P>: Contract.View<S> {
    fun initState(): S
    fun initPresenter(state: S): P
}