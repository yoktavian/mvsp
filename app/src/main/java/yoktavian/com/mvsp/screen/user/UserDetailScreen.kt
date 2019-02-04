package yoktavian.com.mvsp.screen.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.user_detail_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import yoktavian.com.mvsp.R
import yoktavian.com.mvsp.base.BaseFragment
import yoktavian.com.mvsp.data.User
import yoktavian.com.mvsp.data.source.UserRepository
import kotlin.coroutines.CoroutineContext

/**
 * Created by YudaOktavian on 03/02/2019
 */
class UserDetailScreen : BaseFragment<UserDetailScreen.State,
        UserDetailScreen.Presenter,
        UserDetailScreen>(::UserDetailScreen) {

    /**
     * Just make the default State inside this class.
     * Everything that is make the state changes will determine
     * the View is updated. Every changes in State, make sure
     * to calling render.
     */
    class State : BaseFragment.State() {
        var isLoading = false
        var isNetworkError = false
        var userData : User? = null
    }

    /**
     * Presenter as default will have 3 params, that is State, View and Repository.
     * State and view is accessible in view, while repository is not. Repo is visible
     * just in Presenter. So if you needs to get data from Repository, make sure you calling
     * the Presenter first inside the View, fetch the result in the State, then calling
     * partial Render to update your View.
     * @param state
     * @param view
     * @param repository
     */
    class Presenter(state: State,
                    view: UserDetailScreen,
                    repository: UserRepository
    ) : BaseFragment.Presenter<State, UserDetailScreen, UserRepository>(state, view, repository) {

        suspend fun fetchUserDetail() {
            state.isLoading = true
            view.renderLoading()
            // get user detail from your API
            // region result
            repository.getUserData { result ->
                state.userData = result
                view.renderUserDetail()
                state.isLoading = false
                view.renderLoading()
            }
            // endregion
        }
    }

    override val state = State()

    override val presenter = Presenter(state, this, UserRepository())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.user_detail_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            presenter.fetchUserDetail()
        }
    }

    fun renderUserDetail() {
        fragmentTitle.text = state.userData?.name
    }

    /**
     * Every part of view have render method.
     * if any changes inside of State affected from Presenter,
     * of course it will determine view changes.
     * So you must calling partial render, based on
     * changes that occur in State.
     */
    override fun renderLoading() {
       super.renderLoading()
        /**
         * Call fragment to make sure this fragment
         * still alive to avoid memory leak.
         */
        fragment {
            // view of loading must be VISIBLE when state isLoading, otherwise GONE
            progressBar.visibility = View.VISIBLE.takeIf { state.isLoading } ?: View.GONE
        }
    }

    override fun renderNetworkError() {
        super.renderNetworkError()
        fragment {
            if (state.isNetworkError) {
                // view of network error must be visible
            } else {
                // view of network error must be gone
            }
        }
    }

    companion object {
        fun newInstance() = UserDetailScreen()
    }
}